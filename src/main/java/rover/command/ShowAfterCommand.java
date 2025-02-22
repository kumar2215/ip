package rover.command;
import java.time.format.DateTimeParseException;

import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command to show tasks after a specified date.
 */
public final class ShowAfterCommand extends ShowCommand {

    /**
     * Constructs a ShowAfterCommand.
     *
     * @param args The date to show tasks after.
     */
    public ShowAfterCommand(String args) {
        super(args.substring(10).trim());
    }

    /**
     * Shows the tasks after the specified date and/or time.
     */
    @Override
    protected void show(TaskList taskList, Ui ui) {
        taskList.showTasks(ui, (task, wasExceptionThrown) -> {
            try {
                if (wasExceptionThrown.get()) {
                    return false;
                }
                return task.isAfter(args);
            } catch (DateTimeParseException e) {
                ui.displayError("The date format should be 'dd/mm/yy' and the time format should be 'hh:mm'.");
                wasExceptionThrown.set(true);
                return false;
            }
        }, "after " + args);
    }
}
