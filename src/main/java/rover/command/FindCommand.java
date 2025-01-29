package rover.command;

import rover.parser.Parser;
import rover.task.TaskList;
import rover.ui.Ui;
public class FindCommand extends Command {

    public FindCommand(String args) {
        super(args.substring(4).trim());
    }

    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        taskList.showTasksByKeyword(args, ui);
    }
}
