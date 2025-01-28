package rover.command;

import rover.task.TaskList;
import rover.parser.Parser;
import rover.ui.Ui;

public class ShowAfterCommand extends ShowCommand {

    public ShowAfterCommand(String args) {
        super(args.substring(10).trim());
    }

    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        taskList.showTasksAfter(args, ui);
    }
}
