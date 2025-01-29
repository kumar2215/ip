package rover.command;

import rover.parser.Parser;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command to show tasks before a specified date.
 */
public class ShowBeforeCommand extends ShowCommand {

    /**
     * Constructs a ShowBeforeCommand.
     *
     * @param args The arguments to the command.
     */
    public ShowBeforeCommand(String args) {
        super(args.substring(11).trim());
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        taskList.showTasksBefore(args, ui);
    }
}
