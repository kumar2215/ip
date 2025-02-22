package rover.main;
import java.time.format.DateTimeParseException;

import rover.command.Command;
import rover.exceptions.RoverException;
import rover.parser.Parser;
import rover.preferences.UserPreferences;
import rover.storage.Storage;
import rover.task.TaskList;
import rover.ui.TextUi;
import rover.ui.Ui;

/**
 * Rover is a personal task manager that helps users keep track of their tasks.
 **/
public final class Rover {

    private static final String DEFAULT_TASKS_FILE_PATH = "data/Tasks.txt";
    private static final String DEFAULT_PREFERENCES_FILE_PATH = "data/Preferences.json";

    private UserPreferences userPreferences;
    private final Storage storage;
    private TaskList taskList;
    private final Parser parser;
    private Ui ui;

    /**
     * Creates a new Rover instance with the default file path.
     */
    public Rover() {
        this(DEFAULT_TASKS_FILE_PATH, DEFAULT_PREFERENCES_FILE_PATH);
    }

    /**
     * Creates a new Rover instance by loading tasks from the specified file path.
     *
     * @param tasksFilePath The file path to save and load tasks from.
     * @param preferencesFilePath The file path to save and load preferences from.
     */
    private Rover(String tasksFilePath, String preferencesFilePath) {
        parser = new Parser();
        ui = new TextUi();
        storage = new Storage(tasksFilePath, preferencesFilePath);
    }

    /**
     * Sets the Ui instance for Rover.
     */
    public void setUi(Ui ui) {
        this.ui = ui;
        this.userPreferences = new UserPreferences(storage.loadPreferences(ui));
        this.ui.setUserPreferences(userPreferences);
    }

    /**
     * Starts the Rover session by displaying the welcome message.
     */
    public void startSession() {
        ui.showWelcome();
        try {
            taskList = new TaskList(storage.loadTasks(ui));
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
        command.execute(taskList, parser, ui);
        return command.isExit();
    }

    /**
     * Ends the Rover session by saving the tasks and displaying the goodbye message.
     */
    public void endSession() {
        storage.saveAll(taskList, userPreferences, ui);
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
     * Runs the Rover program.
     */
    private void run() {
        setUi(ui);
        startSession();
        boolean isExit = false;
        while (!isExit) {
            isExit = handleResponse(ui.readCommand());
        }
        endSession();
    }

    /**
     * Creates a new Rover instance and runs the program.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Rover(DEFAULT_TASKS_FILE_PATH, DEFAULT_PREFERENCES_FILE_PATH).run();
    }
}
