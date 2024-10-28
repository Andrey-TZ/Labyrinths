package backend.academy.handlers;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class InHandler {
    private final Scanner scanner;
    private final OutHandler output;
    private static final String NULL_STRING_EXCEPTION = "Строка отсутствует";

    public InHandler(InputStream in, OutHandler output) {
        scanner = new Scanner(in, StandardCharsets.UTF_8);
        this.output = output;
    }

    public int getInt(String message) {
        int integer;
        output.showMessage(message, false);
        while (scanner.hasNextLine()) {

            try {
                integer = Integer.parseInt(scanner.nextLine());
                if (integer <= 0) {
                    output.showMessage("Число должно быть больше 0", true);
                    continue;
                }
                return integer;
            } catch (NumberFormatException e) {
                output.showMessage("Нужно ввести число!", true);
            }
        }
        throw new RuntimeException(NULL_STRING_EXCEPTION);
    }

    public String getString(String message) {
        output.showMessage(message, false);
        if (scanner.hasNextLine()) {
            return scanner.nextLine().toLowerCase();
        }
        throw new RuntimeException(NULL_STRING_EXCEPTION);
    }

    public int getCoordinate(int low, int high) {
        int coordinate = getInt(String.format("Введите число от %d до %d: ", low + 1, high));
        while (coordinate < low || coordinate > high) {
            output.showMessage(String.format("Число должно быть в отрезке от %d до %d: ", low + 1, high), true);
            coordinate = getInt("Введите координату: ");
        }
        return coordinate;
    }
}
