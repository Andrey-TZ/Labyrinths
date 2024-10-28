package backend.academy.generators;

import backend.academy.Coordinate;
import backend.academy.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EllerGeneratorTest {
    private final int width = 100;
    private final int height = 100;
    private final Coordinate start = new Coordinate(1, 1);
    private final Coordinate finish = new Coordinate(width - 2, height - 2);
    private final EllerGenerator generator = new EllerGenerator();

    @Test
    void generate_notNull() {
        Maze maze = generator.generate(height, width, start, finish);

        Assertions.assertNotNull(maze);
    }

    @Test
    void generate_notEquals() {
        Maze maze1 = generator.generate(height, width, start, finish);
        Maze maze2 = generator.generate(height, width, start, finish);

        Assertions.assertNotEquals(maze1, maze2);
    }

}
