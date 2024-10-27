package backend.academy;

import lombok.Getter;

public final class Maze {
    @Getter private final int height;
    @Getter private final int width;
    @Getter private final Cell[][] grid;
    private Coordinate start;
    private Coordinate finish;

    public enum MazeType { EMPTY, WALLS }

    public Maze(int height, int width, MazeType type) {
        this.height = height;
        this.width = width;
        grid = new Cell[height][width];

        switch (type) {
            case WALLS -> {
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
            default -> { // По умолчанию стены только по краям лабиринта
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

}
