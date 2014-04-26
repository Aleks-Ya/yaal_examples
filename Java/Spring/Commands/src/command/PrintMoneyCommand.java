package command;

/**
 * Печатает в консоль слово "Money".
 */
public class PrintMoneyCommand implements ICommand {
    @Override
    public void execute() {
        System.out.println("Money");
    }
}
