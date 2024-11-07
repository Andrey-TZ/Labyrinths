package backend.academy.solvers;

import backend.academy.model.Cell;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;

public final class BFSSolver implements Solver {
    private Maze solvedMaze;
    private HashMap<Cell, Cell> chainOfCells;
    private ArrayList<Cell> visitedCells;

    // Возвращает доступных соседей клетки
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

    // Отображает путь в лабиринте
    private void displayPath(Cell finishCell) {
        Cell current = chainOfCells.get(finishCell);
        while (chainOfCells.containsKey(current)) {
            solvedMaze.makeVisitedWay(current.x(), current.y());
            current = chainOfCells.get(current);
        }
    }

    @Override
    public Maze solve(Maze maze) {
        Deque<Cell> deque = new ArrayDeque<>();
        chainOfCells = new HashMap<>();
        visitedCells = new ArrayList<>();

        solvedMaze = maze.copy();
        Cell currentCell = solvedMaze.getStartCell();
        ArrayList<Cell> neighbours;

        deque.add(currentCell);

        do {
            currentCell = deque.pop();

            if (solvedMaze.checkFinish(currentCell)) {
                displayPath(currentCell);
                return solvedMaze;
            }

            neighbours = getNeighbours(currentCell);
            if (!neighbours.isEmpty()) {
                deque.addAll(neighbours);
            }
        }
        while (!deque.isEmpty());
        displayPath(currentCell);
        return solvedMaze;
    }
}
