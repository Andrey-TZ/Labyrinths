package backend.academy.solvers;

import backend.academy.Cell;
import backend.academy.Coordinate;
import backend.academy.Maze;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;

public final class BFSSolver implements Solver {
    private Maze solvedMaze;
    private final Deque<Cell> deque = new ArrayDeque<>();
    private final HashMap<Cell, ArrayList<Cell>> chainOfCells = new HashMap<>();
    private final ArrayList<Cell> visitedCells = new ArrayList<>();

    private ArrayList<Cell> getNeighbours(Cell current) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        int x = current.x();
        int y = current.y();

        // Инициализируем интересующие клетки
        Coordinate up = new Coordinate(x, y - 1);
        Coordinate rt = new Coordinate(x + 1, y);
        Coordinate dw = new Coordinate(x, y + 1);
        Coordinate lt = new Coordinate(x - 1, y);

        Coordinate[] neighboursCoordinates = {up, rt, dw, lt};

        for (Coordinate coordinate : neighboursCoordinates) {
            if (coordinate.x() >= 0 && coordinate.x() < solvedMaze.width() && coordinate.y() >= 0
                && coordinate.y() < solvedMaze.height()) {

                Cell cell = solvedMaze.getCell(coordinate);
                if (cell.type() != Cell.Type.VISITED && cell.type() != Cell.Type.DEAD_END
                    && cell.type() != Cell.Type.WALL && cell.type() != Cell.Type.START
                    && !visitedCells.contains(cell)) {

                    neighbours.add(cell);
                    visitedCells.add(cell);

                    ArrayList<Cell> previousCells = new ArrayList<>(chainOfCells.get(current));
                    previousCells.add(current);
                    chainOfCells.put(cell, previousCells);
                }
            }
        }
        return neighbours;
    }

    private Maze displayPath(Maze maze, Cell finishCell) {
        for (Cell cell : chainOfCells.get(finishCell)) {
            maze.makeVisitedWay(cell.x(), cell.y());
        }
        return maze;
    }

    @Override
    public Maze solve(Maze maze) {
        solvedMaze = maze;
        Cell currentCell = maze.getStartCell();
        ArrayList<Cell> neighbours;

        deque.add(currentCell);
        ArrayList<Cell> previousCells = new ArrayList<>();
        chainOfCells.put(currentCell, previousCells);

        do {
            currentCell = deque.pop();

            if (maze.checkFinish(currentCell)) {
                return displayPath(maze, currentCell);
            }

            neighbours = getNeighbours(currentCell);
            if (!neighbours.isEmpty()) {
                deque.addAll(neighbours);
            }
        }
        while (!deque.isEmpty());
        return displayPath(maze, currentCell);
    }
}
