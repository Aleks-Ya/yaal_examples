package command;

import java.util.List;

/**
 * Хранит информацию обо всех командах.
 */
public class CommandManager {
    private List<ICommand> commands;

    public CommandManager(List<ICommand> commands) {
        this.commands = commands;
    }

    public List<ICommand> getCommands() {
        return commands;
    }
}
