package command;

/**
 * Печатает в консоль слово "Money".
 */
public class PrintCommand implements ICommand {
    private String text;

    public PrintCommand(String text) {
        this.text = text;
    }

    @Override
    public void execute() {
        System.out.println(text + this.hashCode());
    }
}
