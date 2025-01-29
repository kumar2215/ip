package rover.command;

import rover.parser.Parser;
import rover.task.TaskList;
import rover.ui.Ui;

public class ExitCommand extends Command {

    public ExitCommand(String args) {
        super(args);
    }

    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {}

    @Override
    public boolean isExit() {
        return true;
    }
}
