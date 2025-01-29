package rover.command;

import rover.parser.Parser;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents an invalid command.
 */
public class InvalidCommand extends Command {

    /**
     * Constructs an InvalidCommand object.
     *
     * @param args The arguments of the command.
     */
    public InvalidCommand(String args) {
        super(args);
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        ui.showHelpMessage();
    }
}
