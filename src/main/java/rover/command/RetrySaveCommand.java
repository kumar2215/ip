package rover.command;

import rover.task.TaskList;
import rover.storage.Storage;
import rover.parser.Parser;
import rover.ui.Ui;

public class RetrySaveCommand extends Command {

    public RetrySaveCommand(String args) {
        super(args);
    }

    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {}

    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) {
        if (args.equals("y") || args.equals("yes")) {
            storage.save(taskList, ui);
        } else {
            ui.showMessage("Exiting without saving...");
        }
    }

    @Override
    public boolean isExit() {
        return args.equals("n") || args.equals("no");
    }
}
