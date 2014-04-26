import command.ICommand;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");

        Map<String, ICommand> commands = context.getBeansOfType(ICommand.class);
        for (ICommand command : commands.values()) {
            command.execute();
        }
        ICommand command = (ICommand) context.getBean("c1");
        command.execute();
    }
}