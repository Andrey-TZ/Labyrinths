package backend.academy.model;

public record Cell(int x, int y, Type type) {
    public enum Type { WALL, CELL, PASSAGE, FOREST, HILL, START, FINISH, VISITED, DEAD_END }

    public Cell(Coordinate coordinate, Type type) {
        this(coordinate.x(), coordinate.y(), type);
    }

    @Override
    public int hashCode() {
        int total = 31;

        total = 31 * total + x;
        total = 31 * total + y;
        total = 31 * total + type.hashCode();
        return total;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Cell another = (Cell) obj;
        return x == another.x() && y == another.y() && type == another.type();

    }

    public Cell copy() {
        return new Cell(x, y, type);
    }
}
