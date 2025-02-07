package rover.command;

import rover.exceptions.RoverException;
import rover.parser.Parser;
import rover.task.TaskAction;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command to mark a task as done.
 */
public final class MarkCommand extends Command {

    /**
     * Constructs a MarkCommand object.
     *
     * @param args The arguments to the command.
     */
    public MarkCommand(String args) {
        super(args.substring(4).trim());
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        try {
            int index = parser.parseTaskNumber(args, taskList.getNumberOfTasks(), TaskAction.MARK_DONE);
            taskList.markTask(index, ui);
        } catch (RoverException e) {

            ui.displayError(e.getMessage());

        }
    }
}
