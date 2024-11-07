package backend.academy.generators;

import backend.academy.GameSession;
import backend.academy.model.Cell;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import java.util.ArrayList;
import java.util.Stack;
import static java.lang.Math.abs;

public final class BackTrackingGenerator implements Generator {

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

            if (cell.x() >= 0 && cell.x() < width && cell.y() >= 0
                && cell.y() < height) {
                // Берем клетку с нужными координатами из лабиринта
                Cell mazeCell = maze.getCell(cell.x(), cell.y());
                // Проверяем, что это не стена лабиринта и алгоритм туда еще не заходил
                if (mazeCell.type() != Cell.Type.WALL && mazeCell.type() != Cell.Type.PASSAGE) {
                    cells.add(mazeCell);

                }
            }
        }
        return cells;
    }

    private ArrayList<Cell> getUnvisited(Maze maze, int height, int width) {
        ArrayList<Cell> unvisited = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze.getCell(j, i).type() != Cell.Type.WALL && maze.getCell(j, i).type() != Cell.Type.PASSAGE) {
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
        Maze maze = new Maze(height, width, Maze.MazeType.WALLS);

        Cell currentCell = maze.getCell(start.x(), start.y());
        ArrayList<Cell> neighbours;
        Cell neighbour;
        Stack<Cell> stack = new Stack<>();
        int unvisitedNum = height * width;

        do {
            neighbours = getNeighbours(height, width, currentCell, maze);
            maze.makeVisited(currentCell.x(), currentCell.y());
            if (!neighbours.isEmpty()) {
                neighbour = neighbours.get(GameSession.RANDOM.nextInt(neighbours.size()));
                stack.push(currentCell);
                maze = removeWall(currentCell, neighbour, maze);
                currentCell = neighbour;
            } else if (!stack.isEmpty()) {
                currentCell = stack.pop();
            } else {
//                    System.out.println(unvisitedNum);
//                    unvisited = getUnvisited(maze, height, width);
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
