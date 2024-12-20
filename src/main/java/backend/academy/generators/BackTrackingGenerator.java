package backend.academy.generators;

import backend.academy.GameSession;
import backend.academy.model.Cell;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import static java.lang.Math.abs;

public final class BackTrackingGenerator implements Generator {

    @Override
    public Maze generate(int height, int width, Coordinate start, Coordinate finish) {
        Maze maze = new Maze(height, width, false);

        Cell currentCell = maze.getCell(start.x(), start.y());
        List<Cell> neighbours;
        Cell neighbour;
        Stack<Cell> stack = new Stack<>();

        do {
            neighbours = getNeighbours(currentCell, maze);
            maze.makeVisited(currentCell.x(), currentCell.y());
            if (!neighbours.isEmpty()) {
                neighbour = neighbours.get(GameSession.RANDOM.nextInt(neighbours.size()));
                stack.push(currentCell);
                removeWall(currentCell, neighbour, maze);
                currentCell = neighbour;
            } else if (!stack.isEmpty()) {
                currentCell = stack.pop();
            } else {
                break;
            }
        }
        while (!neighbours.isEmpty() || !stack.isEmpty());

        maze.setStart(start);
        maze.setFinish(finish);

        return maze;
    }

    private List<Cell> getNeighbours(Cell current, Maze maze) {
        int x = current.x();
        int y = current.y();

        // Инициализируем интересующие клетки
        Coordinate up = new Coordinate(x, y - 2);
        Coordinate rt = new Coordinate(x + 2, y);
        Coordinate dw = new Coordinate(x, y + 2);
        Coordinate lt = new Coordinate(x - 2, y);

        Coordinate[] neighbours = {dw, rt, up, lt};
        // Массив, содержащий возвращаемые клетки
        List<Cell> cells = new ArrayList<>();

        for (Coordinate cell : neighbours) {
            // Проверяем, что клетка находится в лабиринте

            if (cell.x() >= 0 && cell.x() < maze.width() && cell.y() >= 0
                && cell.y() < maze.height()) {
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

    private List<Cell> getUnvisited(Maze maze, int height, int width) {
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

}
