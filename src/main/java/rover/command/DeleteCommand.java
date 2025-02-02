package rover.command;

import rover.exceptions.RoverException;
import rover.parser.Parser;
import rover.task.TaskAction;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 */
public final class DeleteCommand extends Command {

    /**
     * Constructs a DeleteCommand object.
     *
     * @param args The arguments to the command.
     */
    public DeleteCommand(String args) {
        super(args.substring(6).trim());
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        try {
            int index = parser.parseTaskNumber(args, taskList.getNumberOfTasks(), TaskAction.DELETE);
            taskList.deleteTask(index, ui);
        } catch (RoverException e) {
            ui.showLine();
            ui.displayError(e.getMessage());
            ui.showLine();
        }
    }
}
