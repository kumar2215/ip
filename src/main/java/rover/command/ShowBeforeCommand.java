package rover.command;

import rover.task.TaskList;
import rover.parser.Parser;
import rover.ui.Ui;

public class ShowBeforeCommand extends ShowCommand {

    public ShowBeforeCommand(String args) {
        super(args.substring(11).trim());
    }

    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        taskList.showTasksBefore(args, ui);
    }
}
