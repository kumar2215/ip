package rover.command;

import rover.parser.Parser;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Abstract class for show commands.
 */
public abstract class ShowCommand extends Command {

    public ShowCommand(String args) {
        super(args);
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        show(taskList, ui);
    }

    /**
     * Shows the task list.
     *
     * @param taskList The task list to show.
     * @param ui The user interface to display the task list.
     */
    protected abstract void show(TaskList taskList, Ui ui);
}
