package rover.storage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import rover.task.Task;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Handles the storage of the tasks in the file system.
 */
public final class Storage {

    private final Path filePath;
    private boolean isSaved = false;

    /**
     * Returns a new Storage object with the specified file path.
     *
     * @param filePath The path to the file where the tasks are stored.
     */
    public Storage(String filePath) {
        String[] filePathParts = filePath.split("/");
        String cwd = System.getProperty("user.dir");
        this.filePath = Paths.get(cwd, filePathParts);
    }

    /**
     * Loads the tasks from the file system.
     *
     * @param ui The Ui object to display messages.
     * @return An array of strings representing the tasks.
     */
    public String[] load(Ui ui) {
        try {
            boolean fileExists = Files.exists(filePath);
            if (!fileExists) {
                return new String[0];
            } else {
                Stream<String> lines = Files.lines(filePath).filter(line -> !line.isBlank());
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
     * Saves the tasks to the file system and updates the isSaved field.
     *
     * @param taskList The TaskList object containing the tasks to be saved.
     * @param ui The Ui object to display messages.
     */
    public void save(TaskList taskList, Ui ui) {
        String response = "Saving your tasks...";
        String errorMessage = "";
        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
            String tasksString = taskList.getTasks().stream()
                .map(Task::getTaskString)
                .collect(Collectors.joining("\n"));
            Files.writeString(filePath, tasksString, StandardOpenOption.WRITE);
            isSaved = true;
        } catch (IOException e) {
            errorMessage = "Failed to save tasks.";
            isSaved = false;
        } finally {
            if (isSaved) {
                response += System.lineSeparator() + "Tasks saved successfully!";
                ui.showMessageWithoutLineSeparator(response);
            } else {
                response += System.lineSeparator() + errorMessage;
                ui.displayError(response);
            }
        }
    }

    /**
     * Returns whether the tasks were saved successfully.
     *
     * @return True if the tasks were saved successfully, false otherwise.
     */
    public boolean isSavedSuccessfully() {
        return isSaved;
    }
}
