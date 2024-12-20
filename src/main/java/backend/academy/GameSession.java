package backend.academy;

import backend.academy.generators.BackTrackingGenerator;
import backend.academy.generators.EllerGenerator;
import backend.academy.generators.Generator;
import backend.academy.handlers.InHandler;
import backend.academy.handlers.OutHandler;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import backend.academy.solvers.BFSSolver;
import backend.academy.solvers.BackTrackingSolver;
import backend.academy.solvers.Solver;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("MultipleStringLiterals")
public final class GameSession {
    public static final Random RANDOM = new Random();
    private final Map<String, Generator> generators = new HashMap<>();
    private final Map<String, Solver> solvers = new HashMap<>();
    private final OutHandler output = new OutHandler(System.out);
    private final InHandler input = new InHandler(System.in, output);
    private static final int SCALE = 2;

    {
        generators.put("эллер", new EllerGenerator());
        generators.put("бэк трэкинг", new BackTrackingGenerator());

        solvers.put("бэк трэкинг", new BackTrackingSolver());
        solvers.put("поиск в ширину", new BFSSolver());
    }

    public void run() {
        Generator generator;
        Solver solver;

        // Ввод параметров
        int height = input.getInt("Введите высоту лабиринта: ");
        int width = input.getInt("Введите ширину лабиринта: ");
        output.showMessage("Введите координаты Стартовой точки в лабиринте", true);
        Coordinate start =
            new Coordinate(input.getCoordinate(0, width) * SCALE - 1, input.getCoordinate(0, height) * SCALE - 1);
        output.showMessage("Введите координаты Финишной точки в лабиринте", true);
        Coordinate finish =
            new Coordinate(input.getCoordinate(0, width) * SCALE - 1, input.getCoordinate(0, height) * SCALE - 1);

        // Определение генератора

        generator = generators.get(input.getKey(generators.keySet(), "Введите название алгоритма генерации: "));

        Maze maze = generator.generate(height * SCALE + 1, width * SCALE + 1, start, finish);
        output.showMaze(maze, "Ваш лабиринт готов");

        // Определение solver'а
        solver = solvers.get(input.getKey(solvers.keySet(), "Введите название алгоритма поиска выхода: "));

        Maze mazeSolved = solver.solve(maze);
        output.showMaze(mazeSolved, "Ваш лабиринт решен");
    }
}
