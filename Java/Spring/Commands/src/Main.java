import command.CommandManager;
import command.ICommand;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");

        CommandManager manager = (CommandManager) context.getBean("commandManager");

        for (ICommand command : manager.getCommands()) {
            command.execute();
        }
    }
}