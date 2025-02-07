package rover.command;
import java.time.format.DateTimeParseException;

import rover.exceptions.RoverException;
import rover.parser.Parser;
import rover.task.Task;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command that adds a task to the task list.
 */
public final class AddCommand extends Command {

    /**
     * Constructs an add command with the given arguments.
     *
     * @param args The arguments of the command.
     */
    public AddCommand(String args) {
        super(args);
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        try {
            Task newTask = parser.parseTaskDescription(args);
            taskList.addTask(newTask, ui);
        } catch (RoverException e) {

            ui.displayError(e.getMessage());

        } catch (DateTimeParseException e) {

            if (e.getMessage().contains("date")) {
                ui.displayError("The date format should be 'dd/mm/yy'.");
            }
            if (e.getMessage().contains("time")) {
                ui.displayError("The time format should be 'hh:mm'.");
            }

        }
    }
}
