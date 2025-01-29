package rover.task;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;

import rover.exceptions.RoverException;
import rover.ui.Ui;

/**
 * Represents a list of tasks that can be added to, marked, unmarked, deleted, and displayed.
 */
public class TaskList {

    private final ArrayList<Task> tasks;
    private int taskCount = 0;

    /**
     * Returns an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Returns a task list with the tasks from the given array of task strings.
     *
     * @param taskStrings The array of task strings to be converted to tasks.
     * @throws RoverException If there is a possible corruption in the saved tasks.
     * @throws DateTimeParseException If the date and time format is incorrect.
     */
    public TaskList(String[] taskStrings) throws RoverException, DateTimeParseException {
        this.tasks = new ArrayList<>();
        for (String taskString : taskStrings) {
            String[] parts = taskString.split(" \\| ");
            if (parts.length != 3) {
                throw new RoverException("Possible corruption in saved tasks.");
            }
            Task newTask = getTask(parts);
            tasks.add(newTask);
            taskCount++;
        }
    }

    /**
     * Returns a task based on the given parts of the task string.
     *
     * @param parts The parts of the task string.
     * @return The task based on the parts.
     * @throws RoverException If there is a possible corruption in the saved tasks.
     */
    private Task getTask(String[] parts) throws RoverException {
        Task newTask;
        switch (parts[0]) {
        case "T" -> newTask = new Todo(parts[2]);
        case "D" -> newTask = new Deadline(parts[2]);
        case "E" -> newTask = new Event(parts[2]);
        default -> throw new RoverException("Possible corruption in saved tasks.");
        }
        if (parts[1].equals("1")) {
            newTask.setDone();
        } else if (!parts[1].equals("0")) {
            throw new RoverException("Possible corruption in saved tasks.");
        }
        return newTask;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public int getNumberOfTasks() {
        return this.taskCount;
    }

    /**
     * Displays all the tasks that match the given predicate.
     *
     * @param ui The user interface to display the tasks.
     * @param predicate The predicate to filter the tasks.
     * @param filterDescription The description of the filter.
     */
    public void showTasks(Ui ui, BiFunction<Task, AtomicBoolean, Boolean> predicate, String filterDescription) {
        ui.showLine();
        AtomicBoolean wasExceptionThrown = new AtomicBoolean(false);
        List<Task> filteredTasks = tasks.stream().filter(task -> predicate.apply(task, wasExceptionThrown)).toList();
        if (wasExceptionThrown.get()) {
            ui.showLine();
            return;
        }
        if (filteredTasks.isEmpty()) {
            ui.showMessage("There are no tasks " + filterDescription + ".");
        } else {
            ui.showMessage("Here are the tasks " + filterDescription + ":");
            for (int i = 0; i < filteredTasks.size(); i++) {
                ui.showMessage((i + 1) + ". " + filteredTasks.get(i));
            }
        }
        ui.showLine();
    }

    /**
     * Adds a new task to the task list.
     *
     * @param newTask The new task to be added.
     * @param ui The user interface to display the added task.
     * @throws RoverException If the task already exists in the list.
     */
    public void addTask(Task newTask, Ui ui) throws RoverException {
        if (tasks.contains(newTask)) {
            throw new RoverException("This task already exists in the list.");
        }
        tasks.add(newTask);
        taskCount++;
        ui.showLine();
        ui.showMessage("Got it. I've added this task:");
        ui.showMessage("  " + tasks.get(taskCount - 1).toString());
        ui.showMessage("Now you have " + taskCount + " task" + (taskCount > 1 ? "s" : "") + " in the list.");
        ui.showLine();
    }

    /**
     * Marks a task in the task list as done.
     *
     * @param index The index of the task to be marked as done.
     * @param ui The user interface to display the tasks found.
     */
    public void markTask(int index, Ui ui) {
        Task task = tasks.get(index);
        task.setDone();
        ui.showLine();
        ui.showMessage("Nice! I've marked this task as done:");
        ui.showMessage(task.toString());
        ui.showLine();
    }

    /**
     * Marks a task in the task list as undone.
     *
     * @param index The index of the task to be marked as undone.
     * @param ui The user interface to display the tasks found.
     */
    public void unmarkTask(int index, Ui ui) {
        Task task = tasks.get(index);
        task.setUndone();
        ui.showLine();
        ui.showMessage("OK, I've marked this task as not done yet:");
        ui.showMessage(task.toString());
        ui.showLine();
    }

    /**
     * Deletes a task from the task list.
     *
     * @param index The index of the task to be deleted.
     * @param ui The user interface to display the deleted task.
     */
    public void deleteTask(int index, Ui ui) {
        Task task = tasks.get(index);
        tasks.remove(index);
        taskCount--;
        ui.showLine();
        ui.showMessage("Noted. I've removed this task:");
        ui.showMessage(task.toString());
        ui.showMessage("Now you have " + taskCount + " task" + (taskCount > 1 ? "s" : "") + " in the list.");
        ui.showLine();
    }

}
