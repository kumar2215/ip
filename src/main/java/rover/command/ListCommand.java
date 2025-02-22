package rover.command;

import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command to list all tasks in the task list.
 */
public final class ListCommand extends ShowCommand {

    /**
     * Constructs a ListCommand object.
     *
     * @param args The arguments to be passed to the command.
     */
    public ListCommand(String args) {
        super(args);
    }

    /**
     * Executes the command to list all tasks in the task list.
     */
    @Override
    protected void show(TaskList taskList, Ui ui) {
        taskList.showTasks(ui, (task, ignore) -> true, "in your list");
    }

}
