package backend.academy;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Coordinate startCell = new Coordinate(1 * 2 - 1, 1 * 2 - 1);
        Coordinate finishCell = new Coordinate(30 * 2 - 1, 30 * 2 - 1);
        Generator generator = new StandartGenerator();
        Maze maze = generator.generate(30 * 2 + 1, 30 * 2 + 1, startCell, finishCell);
        Solver solver1 = new Solver1();
        Maze mazeSolved = solver1.solve(maze);
        System.out.println(mazeSolved);
    }
}
