package rover.main;
import java.time.format.DateTimeParseException;

import rover.command.Command;
import rover.exceptions.RoverException;
import rover.parser.Parser;
import rover.storage.Storage;
import rover.task.TaskList;
import rover.ui.TextUi;
import rover.ui.Ui;

/**
 * Rover is a personal task manager that helps users keep track of their tasks.
 **/
public final class Rover {

    private final Storage storage;
    private TaskList taskList;
    private final Parser parser;
    private Ui ui;

    /**
     * Creates a new Rover instance with the default file path.
     */
    public Rover() {
        this("data/Rover.txt");
    }

    /**
     * Creates a new Rover instance by loading tasks from the specified file path.
     *
     * @param filePath The file path to save and load tasks from.
     */
    private Rover(String filePath) {
        parser = new Parser();
        ui = new TextUi();
        storage = new Storage(filePath);
    }

    /**
     * Sets the Ui instance for Rover.
     */
    public void setUi(Ui ui) {
        this.ui = ui;
    }

    /**
     * Starts the Rover session by displaying the welcome message.
     */
    public void startSession() {
        ui.showWelcome();
        try {
            taskList = new TaskList(storage.load(ui));
        } catch (RoverException | DateTimeParseException e) {
            ui.displayError("Could not load saved tasks properly. Saved tasks could be corrupted.");
            taskList = new TaskList();
        }
    }

    /**
     * Handles the response from Rover based on the user input.
     */
    public boolean handleResponse(String input) {
        Command command = parser.parseCommand(input);
        assert command != null : "Command should not be null.";
        command.execute(taskList, parser, ui);
        return command.isExit();
    }

    /**
     * Ends the Rover session by saving the tasks and displaying the goodbye message.
     */
    public void endSession() {
        storage.save(taskList, ui);
        while (!storage.isSavedSuccessfully()) {
            ui.displayError("Could not save tasks. Try again? (Y/N)");
            String input = ui.readCommand();
            Command command = parser.parseCommand(input);
            assert command != null : "Command should not be null.";
            command.execute(taskList, storage, ui);
            if (command.isExit()) {
                break;
            }
        }
        ui.sayBye();
    }

    /**
     * Runs the Rover program.
     */
    private void run() {
        startSession();
        boolean isExit = false;
        while (!isExit) {
            isExit = handleResponse(ui.readCommand());
        }
        endSession();
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
