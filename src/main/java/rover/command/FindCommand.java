package rover.command;

import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command to find tasks by keyword.
 */
public class FindCommand extends ShowCommand {

    /**
     * Constructor for a FindCommand.
     *
     * @param args The arguments for the command.
     */
    public FindCommand(String args) {
        super(args.substring(4).trim());
    }

    /**
     * Shows the tasks that contain the keyword.
     */
    @Override
    protected void show(TaskList taskList, Ui ui) {
        if (args.isEmpty()) {
            ui.displayError("The keyword to find cannot be empty.");
            return;
        }
        taskList.showTasks(ui, (task, ignore) -> task.toString().contains(args),
            "with the keyword '" + args + "'");
    }
}
