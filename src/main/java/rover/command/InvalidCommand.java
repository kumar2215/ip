package rover.command;

import rover.task.TaskList;
import rover.parser.Parser;
import rover.ui.Ui;

public class InvalidCommand extends Command {

    public InvalidCommand(String args) {
        super(args);
    }

    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        ui.showHelpMessage();
    }
}
