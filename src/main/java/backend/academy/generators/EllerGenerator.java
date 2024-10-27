package backend.academy.generators;

import backend.academy.Coordinate;
import backend.academy.Maze;
import static backend.academy.generators.BackTrackingGenerator.RANDOM;

public class EllerGenerator implements Generator {
    int[][] mazeInt;
    int height;
    int width;
    Maze mazeCell;

    @SuppressWarnings("CyclomaticComplexity")
    @Override
    public Maze generate(int height, int width, Coordinate start, Coordinate finish) {
        this.height = height;
        this.width = width;
        mazeCell = new Maze(height, width, Maze.MazeType.EMPTY);
        mazeInt = new int[height][width];

        int setCounter = 1;
        for (int y = 0; y < height; y++) {
            if (y % 2 == 1) {
                mazeInt[y][0] = -1;
                mazeInt[y][width - 1] = -1;
            } else {
                for (int x = 0; x < width; x++) {
                    mazeInt[y][x] = -1;
                }
            }
        }

        // Идем по строкам
        for (int y = 1; y < height - 1; y += 2) {
            // Устанавливаем исходные значения в строке
            for (int x = 1; x < width; x += 2) {
                if (mazeInt[y - 1][x] == -1) { // Если сверху стенка, создаем новую группу
                    mazeInt[y][x] = setCounter++;
                } else { // Иначе продолжаем группу сверху
                    mazeInt[y][x] = mazeInt[y - 1][x];
                }
                mazeCell.makeVisited(x, y);
            }

            // Объединение по горизонтали
            for (int x = 1; x < (width - 2) - 1; x += 2) {
                // Будем ли ставить стенку
                if ((RANDOM.nextBoolean() || mazeInt[y][x] == mazeInt[y][x + 2]) && y != height - 2) {
                    mazeInt[y][x + 1] = -1;
                    mazeCell.makeWall(x + 1, y);
                } else {  // Иначе объединяем в одну группу
                    int oldSet = mazeInt[y][x + 2];
                    int newSet = mazeInt[y][x];
                    mazeInt[y][x + 1] = newSet;
                    mazeCell.makeVisited(x + 1, y);
                    // Переводим все элементы строки старой группы в новую
                    for (int i = 1; i < width; i++) {
                        if (mazeInt[y][i] == oldSet) {
                            mazeInt[y][i] = newSet;
                        }
                    }
                }
            }

            // Создаем вертикальные связи
            for (int x = 1; x < width; x += 2) {
                // Если стена, ставим снизу стену
                if (mazeInt[y][x] == -1) {
                    mazeInt[y + 1][x] = -1;
                    mazeCell.makeWall(x, y + 1);
                } else if (y == height - 2) {  // Если предпоследняя строка, то снизу ставим стенки
                    mazeInt[y + 1][x] = -1;
                } else if (RANDOM.nextBoolean()) {  // Будем ли ставить горизонтальную стенку
                    // Проверяем, что у этого множества еще есть места для связи с нижней строкой
                    int counter = 0;
                    for (int i = 1; i < width; i += 2) {
                        if (mazeInt[y][i] == mazeInt[y][x] && mazeInt[y + 1][i] >= 0) {
                            counter++;
                            // Ставим стенку, если больше одной связи
                            if (counter > 1) {
                                mazeInt[y + 1][x] = -1;
                                mazeCell.makeWall(x, y + 1);
                                break;
                            }
                        }
                    }
                    // Если это единственное место для связи, то продолжаем группу вниз
                    if (counter <= 1) {
                        mazeInt[y + 1][x] = mazeInt[y][x];
                        mazeCell.makeVisited(x, y + 1);
                    }

                } else {  // Иначе продолжаем группу вниз
                    mazeInt[y + 1][x] = mazeInt[y][x];
                    mazeCell.makeVisited(x, y + 1);
                }
            }

        }
        mazeCell.setStart(start);
        mazeCell.setFinish(finish);
        return mazeCell;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                switch (mazeInt[y][x]) {
                    case -1 -> stringBuilder.append("#");
                    case 0 -> stringBuilder.append("@");
                    default -> stringBuilder.append(" ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
