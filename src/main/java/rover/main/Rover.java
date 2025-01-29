package rover.main;
import java.time.format.DateTimeParseException;

import rover.command.Command;
import rover.exceptions.RoverException;
import rover.parser.Parser;
import rover.storage.Storage;
import rover.task.TaskList;
import rover.ui.Ui;

public class Rover {

    private final Storage storage;
    private TaskList taskList;
    private final Parser parser;
    private final Ui ui;

    Rover(String filePath) {
        parser = new Parser();
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            taskList = new TaskList(storage.load(ui));
        } catch (RoverException | DateTimeParseException e) {
            ui.displayError("Could not load saved tasks properly. Saved tasks could be corrupted.");
            taskList = new TaskList();
        }
    }

    private void run() {
        ui.showWelcome();
        boolean isRunning = true;
        while (isRunning) {
            String input = ui.readCommand();
            Command command = parser.parseCommand(input);
            command.execute(taskList, parser, ui);
            isRunning = !command.isExit();
        }
        storage.save(taskList, ui);
        while (!storage.isSavedSuccessfully()) {
            ui.displayError("Could not save tasks. Try again? (Y/N)");
            String input = ui.readCommand();
            Command command = parser.parseCommand(input);
            command.execute(taskList, storage, ui);
            if (command.isExit()) {
                break;
            }
        }
        ui.sayBye();
    }

    public static void main(String[] args) {
        new Rover("data/Rover.txt").run();
    }
}
