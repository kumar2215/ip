package rover.command;

import rover.exceptions.RoverException;
import rover.task.TaskAction;
import rover.task.TaskList;
import rover.parser.Parser;
import rover.ui.Ui;

public class DeleteCommand extends Command {

    public DeleteCommand(String args) {
        super(args.substring(6).trim());
    }

    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        try {
            int index = parser.parseTaskNumber(args, taskList.getNumberOfTasks(), TaskAction.DELETE);
            taskList.deleteTask(index, ui);
        } catch (RoverException e) {
            ui.showLine();
            ui.displayError(e.getMessage());
            ui.showLine();
        }
    }
}
