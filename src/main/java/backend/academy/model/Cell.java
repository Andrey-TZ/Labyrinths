package backend.academy.model;

import java.util.Objects;

public record Cell(int x, int y, Type type) {
    public enum Type { WALL, CELL, PASSAGE, START, FINISH, VISITED, DEAD_END }

    public Cell(Coordinate coordinate, Type type) {
        this(coordinate.x(), coordinate.y(), type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, type);
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
