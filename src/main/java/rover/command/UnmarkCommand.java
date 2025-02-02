package rover.command;

import rover.exceptions.RoverException;
import rover.parser.Parser;
import rover.task.TaskAction;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command to unmark a task as done.
 */
public final class UnmarkCommand extends Command {

    /**
     * Constructs a new UnmarkCommand object.
     *
     * @param args The arguments to the command.
     */
    public UnmarkCommand(String args) {
        super(args.substring(6).trim());
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        try {
            int index = parser.parseTaskNumber(args, taskList.getNumberOfTasks(), TaskAction.MARK_UNDONE);
            taskList.unmarkTask(index, ui);
        } catch (RoverException e) {
            ui.showLine();
            ui.displayError(e.getMessage());
            ui.showLine();
        }
    }
}
