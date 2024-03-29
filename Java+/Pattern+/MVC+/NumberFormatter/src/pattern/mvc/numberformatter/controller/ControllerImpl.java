package pattern.mvc.numberformatter.controller;

import pattern.mvc.numberformatter.model.IModel;

import java.io.Console;

/**
 * Реализация контроллера.
 */
public class ControllerImpl implements IController {
    private static final String EXIT_STRING = "q";
    private final IModel model;

    public ControllerImpl(IModel model) {
        this.model = model;
    }

    @Override
    public void start() {
        Console console = System.console();
        if (console != null) {
            console.printf("Print %s to exit.\n", EXIT_STRING);
            listenConsole(console);
        } else {
            throw new RuntimeException("Console not available! Run me from console.");
        }
    }

    private void listenConsole(Console console) {
        while (true) {
            String line = console.readLine();
            if (!EXIT_STRING.equals(line)) {
                processLine(line);
            } else {
                System.exit(0);
            }
        }
    }

    private void processLine(String line) {
        try {
            model.eventNumberChanged(Double.parseDouble(line));
        } catch (NumberFormatException e) {
            model.eventError(String.format("Incorrect number: %s", line));
        }
    }
}
