package rover.main;
import java.time.format.DateTimeParseException;

import rover.command.Command;
import rover.exceptions.RoverException;
import rover.parser.Parser;
import rover.storage.Storage;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Rover is a personal task manager that helps users keep track of their tasks.
 **/
public class Rover {

    private final Storage storage;
    private TaskList taskList;
    private final Parser parser;
    private final Ui ui;

    /**
     * Creates a new Rover instance by loading tasks from the specified file path.
     *
     * @param filePath The file path to save and load tasks from.
     */
    private Rover(String filePath) {
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

    /**
     * Runs the Rover program.
     */
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

    /**
     * Main entry point of the Rover program.
     * Creates a new Rover instance and runs the program.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Rover("data/Rover.txt").run();
    }
}
