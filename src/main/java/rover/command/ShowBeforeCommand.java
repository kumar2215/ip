package rover.command;
import java.time.format.DateTimeParseException;

import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command to show tasks before a specified date.
 */
public final class ShowBeforeCommand extends ShowCommand {

    /**
     * Constructs a ShowBeforeCommand.
     *
     * @param args The arguments to the command.
     */
    public ShowBeforeCommand(String args) {
        super(args.substring(11).trim());
    }

    /**
     * Shows the tasks before the specified date and/or time.
     */
    @Override
    protected void show(TaskList taskList, Ui ui) {
        taskList.showTasks(ui, (task, wasExceptionThrown) -> {
            try {
                if (wasExceptionThrown.get()) {
                    return false;
                }
                return task.isBefore(args);
            } catch (DateTimeParseException e) {
                ui.displayError("The date format should be 'dd/mm/yy' and the time format should be 'hh:mm'.");
                wasExceptionThrown.set(true);
                return false;
            }
        }, "before " + args);
    }
}
