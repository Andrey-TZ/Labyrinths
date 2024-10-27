package backend.academy.generators;

import backend.academy.Coordinate;
import backend.academy.Maze;

public interface Generator {
    Maze generate(int height, int width, Coordinate start, Coordinate finish);
}
