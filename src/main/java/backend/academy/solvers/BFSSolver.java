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
    private final HashMap<Cell,Cell> chainOfCells = new HashMap<>();
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

                    chainOfCells.put(cell, current);
                }
            }
        }
        return neighbours;
    }

    private Maze displayPath(Maze maze, Cell finishCell) {
        Cell current = chainOfCells.get(finishCell);
        while(chainOfCells.containsKey(current)) {
            maze.makeVisitedWay(current.x(), current.y());
            current = chainOfCells.get(current);
        }
        return maze;
    }

    @Override
    public Maze solve(Maze maze) {
        solvedMaze = maze;
        Cell currentCell = maze.getStartCell();
        ArrayList<Cell> neighbours;

        deque.add(currentCell);

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
