package backend.academy.model;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

public final class Maze {
    @Getter private final int height;
    @Getter private final int width;
    @Getter private final Cell[][] grid;
    @Getter private Coordinate start;
    @Getter private Coordinate finish;

    private Maze(int height, int width, Cell[][] grid, Coordinate start, Coordinate finish) {
        this.height = height;
        this.width = width;
        this.grid = grid;
        this.start = start;
        this.finish = finish;
    }

    public Maze(int height, int width, boolean isEmpty) {
        this.height = height;
        this.width = width;
        grid = new Cell[height][width];

        if (isEmpty) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (i % 2 == 0) {
                        grid[i][j] = new Cell(j, i, Cell.Type.WALL);
                    } else if (j == 0 || j == width - 1) {
                        grid[i][j] = new Cell(j, i, Cell.Type.WALL);
                    } else {
                        grid[i][j] = new Cell(j, i, Cell.Type.CELL);
                    }
                }
            }
        } else {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if ((i % 2 != 0 && j % 2 != 0)
                        && (i < height - 1 && j < width - 1)) {
                        grid[i][j] = new Cell(j, i, Cell.Type.CELL);
                    } else {
                        grid[i][j] = new Cell(j, i, Cell.Type.WALL);
                    }
                }
            }
        }
    }

    public void setStart(Coordinate start) {
        grid[start.y()][start.x()] = new Cell(start.x(), start.y(), Cell.Type.START);
        this.start = start;
    }

    public void setFinish(Coordinate finish) {
        grid[finish.y()][finish.x()] = new Cell(finish.x(), finish.y(), Cell.Type.FINISH);
        this.finish = finish;
    }

    public Cell getCell(int x, int y) {
        return grid[y][x];
    }

    public Cell getStartCell() {
        return getCell(start);
    }

    public Cell getCell(Coordinate coordinate) {
        return grid[coordinate.y()][coordinate.x()];
    }

    public void makeVisited(int x, int y) {
        grid[y][x] = new Cell(x, y, Cell.Type.PASSAGE);
    }

    public void makeVisitedWay(int x, int y) {
        grid[y][x] = new Cell(x, y, Cell.Type.VISITED);
    }

    public void makeWall(int x, int y) {
        grid[y][x] = new Cell(x, y, Cell.Type.WALL);
    }

    public boolean checkFinish(Cell cell) {
        return finish.x() == cell.x() && finish.y() == cell.y();
    }

    public void makeDeadEnd(int x, int y) {
        grid[y][x] = new Cell(x, y, Cell.Type.DEAD_END);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (grid[i][j].type()) {
                    case WALL -> builder.append("#");
                    case PASSAGE, DEAD_END -> builder.append(" ");
                    case CELL -> builder.append("C");
                    case START -> builder.append("S");
                    case FINISH -> builder.append("F");
                    case VISITED -> builder.append("W");
                    default -> builder.append("D");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width, Arrays.deepHashCode(grid), start, finish);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Maze another = (Maze) obj;
        return height == another.height() && width == another.width() && start.equals(another.start())
            && finish.equals(another.finish()) && Arrays.deepEquals(grid, another.grid());
    }

    public Maze copy() {
        Cell[][] newGrid = new Cell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newGrid[y][x] = grid[y][x].copy();
            }
        }
        return new Maze(height, width, newGrid, start, finish);
    }

}
