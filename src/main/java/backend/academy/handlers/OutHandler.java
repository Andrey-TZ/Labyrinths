package backend.academy.handlers;

import backend.academy.model.Maze;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("RegexpSinglelineJava")
public class OutHandler {
    private final PrintStream output;

    public OutHandler(OutputStream out) {
        output = new PrintStream(out, true, StandardCharsets.UTF_8);
    }

    public void showMessage(String message, boolean toNextString) {
        if (toNextString) {
            output.println(message);
        } else {
            output.print(message);
        }
    }

    public void showMaze(Maze maze, String message) {
        System.out.println(message);
        System.out.println(maze);
    }
}
