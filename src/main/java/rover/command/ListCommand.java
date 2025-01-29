package rover.command;

import rover.parser.Parser;
import rover.task.TaskList;
import rover.ui.Ui;
public class ListCommand extends ShowCommand {

    public ListCommand(String args) {
        super(args);
    }

    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        taskList.showAllTasks(ui);
    }

}
