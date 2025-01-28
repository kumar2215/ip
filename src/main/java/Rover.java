import java.io.IOException;
import java.time.format.DateTimeParseException;

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
            taskList = new TaskList(storage.load());
        } catch (RoverException e) {
            ui.displayError("Could not load saved tasks. Saved tasks could be corrupted.");
            taskList = new TaskList();
        } catch (IOException e) {
            ui.displayError("Could not load saved tasks.");
            taskList = new TaskList();
        } catch (SecurityException e) {
            ui.displayError("Could not access the saved tasks file.");
            taskList = new TaskList();
        }
    }

    private void run() {
        ui.showWelcome();
        boolean isRunning = true;
        while (isRunning) {
            String input = ui.readCommand();
            Command command = parser.parseCommand(input);
            switch (command) {
            case EMPTY:
                ui.displayError("Please enter a command.");
            case EXIT:
                isRunning = false;
                break;
            case LIST_TASKS:
                taskList.showAllTasks(ui);
                break;
            case MARK_TASK:
                try {
                    int index = parser.parseTaskNumber(
                            input.substring(4).trim(),
                            taskList.getNumberOfTasks(),
                            TaskAction.MARK_DONE
                    );
                    taskList.markTask(index, ui);
                    break;
                } catch (RoverException e) {
                    ui.showLine();
                    ui.displayError(e.getMessage());
                    ui.showLine();
                }
                break;
            case UNMARK_TASK:
                try {
                    int index = parser.parseTaskNumber(
                            input.substring(6).trim(),
                            taskList.getNumberOfTasks(),
                            TaskAction.MARK_UNDONE
                    );
                    taskList.unmarkTask(index, ui);
                    break;
                } catch (RoverException e) {
                    ui.showLine();
                    ui.displayError(e.getMessage());
                    ui.showLine();
                }
                break;
            case DELETE_TASK:
                try {
                    int index = parser.parseTaskNumber(
                            input.substring(6).trim(),
                            taskList.getNumberOfTasks(),
                            TaskAction.DELETE
                    );
                    taskList.deleteTask(index, ui);
                    break;
                } catch (RoverException e) {
                    ui.showLine();
                    ui.displayError(e.getMessage());
                    ui.showLine();
                }
                break;
            case SHOW_TASKS_BEFORE:
                taskList.showTasksBefore(input.substring(11).trim(), ui);
                break;
            case SHOW_TASKS_AFTER:
                taskList.showTasksAfter(input.substring(10).trim(), ui);
                break;
            case ADD_TASK:
                try {
                    Task newTask = parser.parseTaskDescription(input);
                    taskList.addTask(newTask, ui);
                } catch (RoverException e) {
                    ui.showLine();
                    ui.displayError(e.getMessage());
                    ui.showLine();
                } catch (DateTimeParseException e) {
                    ui.showLine();
                    if (e.getMessage().contains("date")) {
                        ui.displayError("The date format should be 'dd/mm/yy'.");
                    } if (e.getMessage().contains("time")) {
                        ui.displayError("The time format should be 'hh:mm'.");
                    }
                    ui.showLine();
                }
                break;
            case INVALID:
                ui.showHelpMessage();
            }
        }
        storage.save(taskList, ui);
        ui.sayBye();
    }

    public static void main(String[] args) {
        new Rover("data/Rover.txt").run();
    }
}
