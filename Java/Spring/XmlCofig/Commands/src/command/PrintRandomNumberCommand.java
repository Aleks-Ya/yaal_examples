package command;

import java.util.Random;

/**
 * Печатает в консоль случайное число.
 */
public class PrintRandomNumberCommand implements ICommand {
    private final Random random = new Random();
    private final int min;
    private final int max;

    public PrintRandomNumberCommand(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void execute() {
        int next;
        do {
            next = random.nextInt();
        } while (min > next || max < next);
        System.out.println(next);
    }
}