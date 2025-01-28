package rover.storage;

import rover.task.Task;
import rover.task.TaskList;
import rover.ui.Ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Storage {

    private final Path filePath;
    private boolean isSaved = false;

    public Storage(String filePath) {
        String[] filePathParts = filePath.split("/");
        String cwd = System.getProperty("user.dir");
        this.filePath = Paths.get(cwd, filePathParts);
    }

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
        } catch (SecurityException e) {
            ui.displayError("Could not access the saved tasks file.");
            return new String[0];
        }
    }

    public void save(TaskList taskList, Ui ui) {
        ui.showMessage("Saving your tasks...");
        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
            for (Task task : taskList.getTasks()) {
                String taskString = task.getTaskString() + "\n";
                Files.writeString(filePath, taskString, java.nio.file.StandardOpenOption.APPEND);
            }
            ui.showMessage("Tasks saved successfully.");
            isSaved = true;
        } catch (IOException e) {
            ui.displayError("Failed to save tasks.");
            isSaved = false;
        } catch (SecurityException e) {
            ui.displayError("Could not create the saved tasks file.");
            isSaved = false;
        }
    }

    public boolean isSavedSuccessfully() {
        return isSaved;
    }
}
