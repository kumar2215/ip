package rover.command;

import rover.parser.Parser;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command to list all tasks in the task list.
 */
public class ListCommand extends ShowCommand {

    /**
     * Constructs a ListCommand object.
     *
     * @param args The arguments to be passed to the command.
     */
    public ListCommand(String args) {
        super(args);
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        taskList.showAllTasks(ui);
    }

}
