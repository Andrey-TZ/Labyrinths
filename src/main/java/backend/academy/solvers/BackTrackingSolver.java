package backend.academy.solvers;

import backend.academy.GameSession;
import backend.academy.model.Cell;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import java.util.ArrayList;
import java.util.Stack;

//Алгоритм поиска пути бэктрекингом:
//1. Сделайте начальную клетку текущей и отметьте ее как посещенную.
//2. Пока не найден выход
//      1. Если текущая клетка имеет непосещнных «соседей»
//          1. Протолкните текущую клетку в стек
//          2. Выберите случайную клетку из соседних
//          3. Сделайте выбранную клетку текущей и отметьте ее как посещенную.
//      2. Иначе если стек не пуст
//          1. Выдерните клетку из стека
//          2. Сделайте ее текущей
//      3. Иначе выхода нет
public final class BackTrackingSolver implements Solver {

    // Возвращает доступных соседей клетки
    private ArrayList<Cell> getNeighbours(Cell current, Maze maze) {
        int x = current.x();
        int y = current.y();

        // Инициализируем интересующие клетки
        Coordinate up = new Coordinate(x, y - 1);
        Coordinate rt = new Coordinate(x + 1, y);
        Coordinate dw = new Coordinate(x, y + 1);
        Coordinate lt = new Coordinate(x - 1, y);

        Coordinate[] neighbours = {dw, rt, up, lt};
        // Массив, содержащий возвращаемые клетки
        ArrayList<Cell> cells = new ArrayList<>();

        for (Coordinate cell : neighbours) {
            // Проверяем, что клетка находится в лабиринте

            if (cell.x() >= 0 && cell.x() < maze.width() && cell.y() >= 0
                && cell.y() < maze.height()) {
                // Берем клетку с нужными координатами из лабиринта
                Cell mazeCell = maze.getCell(cell.x(), cell.y());
                // Проверяем, что это не стена лабиринта и алгоритм туда еще не заходил
                if (mazeCell.type() != Cell.Type.WALL && mazeCell.type() != Cell.Type.VISITED
                    && mazeCell.type() != Cell.Type.DEAD_END) {
                    cells.add(mazeCell);
                }
            }
        }
        return cells;
    }

    @Override
    public Maze solve(Maze maze) {
        Maze solvedMaze = maze.copy();
        Cell startCell = solvedMaze.getStartCell();
        Cell currentCell = startCell;
        Stack<Cell> stack = new Stack<>();
        ArrayList<Cell> neighbours;
        Cell neighbour;

        while (!solvedMaze.checkFinish(currentCell)) {
            neighbours = getNeighbours(currentCell, solvedMaze);
            if (!neighbours.isEmpty()) {
                stack.push(currentCell);
                neighbour = neighbours.get(GameSession.RANDOM.nextInt(neighbours.size()));
                solvedMaze.makeVisitedWay(currentCell.x(), currentCell.y());
                currentCell = neighbour;
            } else if (!stack.isEmpty()) {
                solvedMaze.makeDeadEnd(currentCell.x(), currentCell.y());
                currentCell = stack.pop();
            } else {
                break;
            }
        }
        solvedMaze.setStart(new Coordinate(startCell.x(), startCell.y()));
        return solvedMaze;
    }
}
