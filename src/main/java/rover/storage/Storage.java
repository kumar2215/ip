package rover.storage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.node.ObjectNode;

import rover.preferences.UserPreferences;
import rover.task.Task;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Handles the storage of the tasks in the file system.
 */
public final class Storage {

    private static final String NEW_LINE = System.lineSeparator();
    private final Path tasksFilePath;
    private final Path preferencesFilePath;
    private boolean isTasksSaved = false;
    private boolean isPreferencesSaved = false;
    private final JsonFileManager jsonFileManager = new JsonFileManager();

    /**
     * Returns a new Storage object with the specified file path.
     *
     * @param tasksFilePath The file path to save and load tasks from.
     * @param preferencesFilePath The file path to save and load preferences from.
     */
    public Storage(String tasksFilePath, String preferencesFilePath) {
        String cwd = System.getProperty("user.dir");
        this.tasksFilePath = Paths.get(cwd, tasksFilePath.split("/"));
        this.preferencesFilePath = Paths.get(cwd, preferencesFilePath.split("/"));
    }

    /**
     * Loads the tasks from the file system.
     *
     * @param ui The Ui object to display messages.
     * @return An array of strings representing the tasks.
     */
    public String[] loadTasks(Ui ui) {
        try {
            boolean fileExists = Files.exists(tasksFilePath);
            if (!fileExists) {
                return new String[0];
            } else {
                Stream<String> lines = Files.lines(tasksFilePath).filter(line -> !line.isBlank());
                String[] tasks = lines.toArray(String[]::new);
                lines.close();
                return tasks;
            }
        } catch (IOException e) {
            ui.displayError("Failed to load tasks.");
            return new String[0];
        }
    }

    /**
     * Loads the preferences from the file system.
     *
     * @param ui The Ui object to display messages.
     * @return The JSON node representing the preferences.
     */
    public ObjectNode loadPreferences(Ui ui) {
        try {
            boolean fileExists = Files.exists(preferencesFilePath);
            if (!fileExists) {
                return (ObjectNode) (new UserPreferences()).getJsonNode();
            }
            return (ObjectNode) jsonFileManager.load(preferencesFilePath.toString());
        } catch (IOException e) {
            ui.displayError("Failed to load preferences.");
            return (ObjectNode) (new UserPreferences()).getJsonNode();
        }
    }

    /**
     * Saves tasks and preferences to the file system.
     *
     * @param taskList The TaskList object containing the tasks to be saved.
     * @param userPreferences The UserPreferences object containing the preferences to be saved.
     * @param ui The Ui object to display messages.
     */
    public void saveAll(TaskList taskList, UserPreferences userPreferences, Ui ui) {
        assert taskList != null : "TaskList should not be null.";
        assert userPreferences != null : "UserPreferences should not be null.";
        assert ui != null : "Ui should not be null.";
        String response = "Saving your tasks...";
        saveTasks(taskList);
        response += NEW_LINE + (isTasksSaved ? "Tasks saved successfully!" : "Failed to save tasks.");

        response += NEW_LINE + "Saving your preferences...";
        savePreferences(userPreferences);
        response += NEW_LINE + (isPreferencesSaved ? "Preferences saved successfully!" : "Failed to save preferences.");

        if (isTasksSaved && isPreferencesSaved) {
            ui.showMessageWithoutLineSeparator(response);
        } else {
            ui.displayError(response);
        }
    }

    /**
     * Saves the tasks to the file system and updates the isSaved field.
     *
     * @param taskList The TaskList object containing the tasks to be saved.
     */
    private void saveTasks(TaskList taskList) {
        assert taskList != null : "TaskList should not be null.";
        try {
            Files.createDirectories(tasksFilePath.getParent());
            Files.deleteIfExists(tasksFilePath);
            Files.createFile(tasksFilePath);
            String tasksString = taskList.getTasks().stream()
                .map(Task::getTaskString)
                .collect(Collectors.joining("\n"));
            Files.writeString(tasksFilePath, tasksString, StandardOpenOption.WRITE);
            isTasksSaved = true;
        } catch (IOException e) {
            isTasksSaved = false;
        }
    }

    /**
     * Saves the preferences to the file system and updates the isSaved field.
     *
     * @param userPreferences The UserPreferences object containing the preferences to be saved.
     */
    private void savePreferences(UserPreferences userPreferences) {
        assert userPreferences != null : "UserPreferences should not be null.";
        try {
            jsonFileManager.write(userPreferences.getJsonNode(), preferencesFilePath.toString());
            isPreferencesSaved = true;
        } catch (IOException e) {
            isPreferencesSaved = false;
        }
    }

    /**
     * Returns whether the tasks were saved successfully.
     *
     * @return True if the tasks were saved successfully, false otherwise.
     */
    public boolean isSavedSuccessfully() {
        return isTasksSaved && isPreferencesSaved;
    }
}
