package rover.command;

import rover.parser.Parser;
import rover.storage.Storage;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command to retry saving the task list.
 */
public final class RetrySaveCommand extends Command {

    /**
     * Constructs a RetrySaveCommand.
     *
     * @param args The user input arguments.
     */
    public RetrySaveCommand(String args) {
        super(args);
    }

    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {}

    /**
     * Retries saving the task list.
     *
     * @param taskList The task list.
     * @param storage The storage.
     * @param ui The user interface.
     */
    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) {
        if (args.equals("y") || args.equals("yes")) {
            storage.save(taskList, ui);
        } else {
            ui.showMessage("Exiting without saving...");
        }
    }

    /**
     * Returns true if the user wants to exit the application.
     */
    @Override
    public boolean isExit() {
        return args.equals("n") || args.equals("no");
    }
}
