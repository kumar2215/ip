package rover.command;

import rover.parser.Parser;
import rover.storage.Storage;
import rover.task.TaskList;
import rover.ui.Ui;

public abstract class Command {

    protected final String args;

    public Command(String args) {
        this.args = args;
    }

    public abstract void execute(TaskList taskList, Parser parser, Ui ui);

    public void execute(TaskList taskList, Storage storage, Ui ui) {}

    public boolean isExit() {
        return false;
    }

}
