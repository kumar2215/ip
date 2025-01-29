package rover.command;

import rover.parser.Parser;
import rover.task.TaskList;
import rover.ui.Ui;

public class EmptyCommand extends Command {

    public EmptyCommand(String args) {
        super(args);
    }

    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        ui.displayError("Please enter a command.");
    }
}
