package rover.command;

import rover.parser.Parser;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command that does nothing.
 */
public class EmptyCommand extends Command {

    /**
     * Constructs an EmptyCommand object.
     *
     * @param args The arguments to be passed to the command.
     */
    public EmptyCommand(String args) {
        super(args);
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        ui.displayError("Please enter a command.");
    }
}
