package backend.academy;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import static java.lang.Math.abs;

public class StandartGenerator implements Generator {
    private Maze maze;
    public static final Random random = new Random();

    private ArrayList<Cell> getNeighbours(int height, int width, Cell current, Maze maze) {
        int x = current.x();
        int y = current.y();

        // Инициализируем интересующие клетки
        Coordinate up = new Coordinate(x, y - 2);
        Coordinate rt = new Coordinate(x + 2, y);
        Coordinate dw = new Coordinate(x, y + 2);
        Coordinate lt = new Coordinate(x - 2, y);

        Coordinate[] neighbours = {dw, rt, up, lt};
        // Массив, содержащий возвращаемые клетки
        ArrayList<Cell> cells = new ArrayList<>();

        for (Coordinate cell : neighbours) {
            // Проверяем, что клетка находится в лабиринте

            if (cell.x() >= 0 && cell.x() < width && cell.y() >= 0 &&
                cell.y() < height) {
                // Берем клетку с нужными координатами из лабиринта
                Cell mazeCell = maze.getCell(cell.x(), cell.y());
                // Проверяем, что это не стена лабиринта и алгоритм туда еще не заходил
                if (mazeCell.type() != Cell.Type.WALL && mazeCell.type() != Cell.Type.VISITED) {
                    cells.add(mazeCell);

                }
            }
        }
        return cells;
    }

    private ArrayList<Cell> getUnvisited(int height, int width) {
        ArrayList<Cell> unvisited = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze.getCell(j, i).type() != Cell.Type.WALL && maze.getCell(j, i).type() != Cell.Type.VISITED) {
                    unvisited.add(maze.getCell(j, i));
                }
            }
        }
        return unvisited;
    }

    private Maze removeWall(Cell first, Cell second, Maze maze) {
        int xDiff = second.x() - first.x();
        int yDiff = second.y() - first.y();

        int addX = (xDiff != 0) ? (xDiff / abs(xDiff)) : 0;
        int addY = (yDiff != 0) ? (yDiff / abs(yDiff)) : 0;

        maze.makeVisited(first.x() + addX, first.y() + addY);

        return maze;

    }

    @Override
    public Maze generate(int height, int width, Coordinate start, Coordinate finish) {
        maze = new Maze(height, width);

        Cell currentCell = maze.getCell(start.x(), start.y());
        ArrayList<Cell> neighbours, unvisited;
        Cell neighbour;
        Stack<Cell> stack = new Stack<>();
        int unvisitedNum = height * width;

        do {
            neighbours = getNeighbours(height, width, currentCell, maze);
            maze.makeVisited(currentCell.x(), currentCell.y());
            if (!neighbours.isEmpty()) {
                neighbour = neighbours.get(random.nextInt(neighbours.size()));
                stack.push(currentCell);
                maze = removeWall(currentCell, neighbour, maze);
                currentCell = neighbour;
            } else if (!stack.isEmpty()) {
                currentCell = stack.pop();
            } else {
//                    System.out.println(unvisitedNum);
//                    unvisited = getUnvisited(height, width);
//                    unvisitedNum = unvisited.size();
//                    currentCell = unvisited.get(random.nextInt(unvisited.size()));
//                    maze.makeVisited(currentCell.x(), currentCell.y());
                break;
            }
        }

        while (unvisitedNum > 0);

        maze.setStart(start);
        maze.setFinish(finish);

        return maze;
    }

}
