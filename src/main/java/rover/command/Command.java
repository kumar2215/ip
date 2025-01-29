package rover.command;

import rover.parser.Parser;
import rover.storage.Storage;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command that can be executed by the user.
 */
public abstract class Command {

    protected final String args;

    /**
     * Constructs a command with the given arguments.
     *
     * @param args The arguments of the command.
     */
    public Command(String args) {
        this.args = args;
    }

    /**
     * Executes the command.
     *
     * @param taskList The task list to execute the command on.
     * @param parser The parser to parse the command.
     * @param ui The user interface to interact with the user.
     */
    public abstract void execute(TaskList taskList, Parser parser, Ui ui);

    /**
     * Executes the command.
     *
     * @param taskList The task list to execute the command on.
     * @param storage The storage to save the task list.
     * @param ui The user interface to interact with the user.
     */
    public void execute(TaskList taskList, Storage storage, Ui ui) {}

    /**
     * Returns true if the command is an exit command and is false by default.
     */
    public boolean isExit() {
        return false;
    }

}
