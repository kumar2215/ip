package rover.command;

import rover.parser.Parser;
import rover.ui.Ui;
import rover.task.TaskList;

public abstract class Command {

    protected final String args;

    public Command(String args) {
        this.args = args;
    }

    public abstract void execute(TaskList taskList, Parser parser, Ui ui);

    public boolean isExit() {
        return false;
    }

}
