package backend.academy;

public record Cell(int x, int y, Type type) {
    public enum Type { WALL, CELL, PASSAGE, FOREST, HILL, START, FINISH, VISITED, DEAD_END }
}
