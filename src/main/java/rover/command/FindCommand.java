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
     * {@code @InheritDoc} from Task
     */
    @Override
    protected void show(TaskList taskList, Ui ui) {
        if (args.isEmpty()) {
            ui.showLine();
            ui.displayError("The keyword to find cannot be empty.");
            ui.showLine();
            return;
        }
        taskList.showTasks(ui, (task, ignore) -> task.toString().contains(args),
            "with the keyword '" + args + "'");
    }
}
