package command;

import java.util.Random;

/**
 * Печатает в консоль случайное число.
 */
public class PrintRandomNumberCommand implements ICommand {
    private final Random random = new Random();

    @Override
    public void execute() {
        System.out.println(random.nextInt());
    }
}
