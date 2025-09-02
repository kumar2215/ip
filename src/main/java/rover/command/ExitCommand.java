package rover.command;

import rover.parser.Parser;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command to exit the program.
 */
public final class ExitCommand extends Command {

    /**
     * Constructs an ExitCommand object.
     *
     * @param args The arguments passed to the command.
     */
    public ExitCommand(String args) {
        super(args);
    }

    /**
     * Executes nothing as the program is exiting.
     */
    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {}

    /**
     * Returns true as the command is an exit command.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
