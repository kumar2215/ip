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
     * {@code @InheritDoc} from Task
     */
    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {}

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
