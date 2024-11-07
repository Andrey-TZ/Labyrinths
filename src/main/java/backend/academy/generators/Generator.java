package backend.academy.generators;

import backend.academy.model.Coordinate;
import backend.academy.model.Maze;

public interface Generator {
    Maze generate(int height, int width, Coordinate start, Coordinate finish);
}
