package backend.academy;

public record Cell(int x, int y, Type type) {
    public enum Type {WALL, CELL, VISITED, PASSAGE, FOREST, HILL, START, FINISH, VISITED_WAY, DEAD_END}
}
