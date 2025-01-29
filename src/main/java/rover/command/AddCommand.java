package rover.command;
import java.time.format.DateTimeParseException;

import rover.exceptions.RoverException;
import rover.parser.Parser;
import rover.task.Task;
import rover.task.TaskList;
import rover.ui.Ui;

public class AddCommand extends Command {

    public AddCommand(String args) {
        super(args);
    }

    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        try {
            Task newTask = parser.parseTaskDescription(args);
            taskList.addTask(newTask, ui);
        } catch (RoverException e) {
            ui.showLine();
            ui.displayError(e.getMessage());
            ui.showLine();
        } catch (DateTimeParseException e) {
            ui.showLine();
            if (e.getMessage().contains("date")) {
                ui.displayError("The date format should be 'dd/mm/yy'.");
            }
            if (e.getMessage().contains("time")) {
                ui.displayError("The time format should be 'hh:mm'.");
            }
            ui.showLine();
        }
    }
}
