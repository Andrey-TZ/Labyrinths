package backend.academy.solvers;

import backend.academy.generators.BackTrackingGenerator;
import backend.academy.generators.EllerGenerator;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BackTrackingSolverTest {
    private final int width = 100;
    private final int height = 100;
    private final Coordinate start = new Coordinate(1, 1);
    private final Coordinate finish = new Coordinate(width - 2, height - 2);
    private static EllerGenerator ellerGenerator;
    private static BackTrackingGenerator backTrackingGenerator;
    private static Solver solver;

    @BeforeAll static void setUp(){
        solver = new BackTrackingSolver();
        ellerGenerator = new EllerGenerator();
        backTrackingGenerator = new BackTrackingGenerator();
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 50, 100})
    Maze generateEllerMazes(int length){
        return ellerGenerator.generate(length, length, start, new Coordinate(length, length));
    }
    @ParameterizedTest
    @ValueSource(ints={10, 50, 100})
    void solveEller(int length){
        Maze maze = ellerGenerator.generate(length, length, start, new Coordinate(length-1, length-1));
        Maze newMaze = maze.copy();
        Maze solvedMaze = solver.solve(newMaze);

        Assertions.assertNotEquals(maze, solvedMaze);
    }

    @ParameterizedTest
    @ValueSource(ints={10, 50, 100})
    void solveBackTracking(int length){
        Maze maze = backTrackingGenerator.generate(length, length, start, new Coordinate(length-1, length-1));
        Maze newMaze = maze.copy();
        Maze solvedMaze = solver.solve(newMaze);

        Assertions.assertNotEquals(maze, solvedMaze);
    }
}
