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
public final class TaskList {

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
    public TaskList(String ...taskStrings) throws RoverException, DateTimeParseException {
        assert taskStrings != null : "Task strings should not be null.";
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
    private Task getTask(String ...parts) throws RoverException {
        assert parts != null : "Parts should not be null.";
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
        assert newTask != null : "Task should not be null.";
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
        assert predicate != null : "Predicate should not be null.";
        assert filterDescription != null : "Filter description should not be null.";
        assert !filterDescription.isEmpty() : "Filter description should not be empty.";
        StringBuilder response = new StringBuilder();
        AtomicBoolean wasExceptionThrown = new AtomicBoolean(false);
        List<Task> filteredTasks = tasks.stream().filter(task -> predicate.apply(task, wasExceptionThrown)).toList();
        if (wasExceptionThrown.get()) {
            return;
        }
        if (filteredTasks.isEmpty()) {
            response.append("There are no tasks ").append(filterDescription).append(".");
        } else {
            response.append("Here are the tasks ").append(filterDescription).append(":").append(System.lineSeparator());
            for (int i = 0; i < filteredTasks.size(); i++) {
                response.append((i + 1)).append(". ").append(filteredTasks.get(i));
                if (i != filteredTasks.size() - 1) {
                    response.append(System.lineSeparator());
                }
            }
        }
        ui.showMessage(response.toString());
    }

    /**
     * Adds a new task to the task list.
     *
     * @param newTask The new task to be added.
     * @param ui The user interface to display the added task.
     * @throws RoverException If the task already exists in the list.
     */
    public void addTask(Task newTask, Ui ui) throws RoverException {
        assert newTask != null : "Task should not be null.";
        assert ui != null : "Ui should not be null.";
        if (tasks.contains(newTask)) {
            throw new RoverException("This task already exists in the list.");
        }
        tasks.add(newTask);
        taskCount++;
        String response = "Got it. I've added this task:" + System.lineSeparator()
            + "  " + newTask + System.lineSeparator()
            + "Now you have " + taskCount + " task"
            + (taskCount > 1 ? "s" : "") + " in the list.";
        ui.showMessage(response);
    }

    /**
     * Marks a task in the task list as done.
     *
     * @param index The index of the task to be marked as done.
     * @param ui The user interface to display the tasks found.
     */
    public void markTask(int index, Ui ui) {
        assert index >= 0 : "Index should be non-negative.";
        assert index < taskCount : "Index should be less than the number of tasks.";
        assert ui != null : "Ui should not be null.";
        Task task = tasks.get(index);
        task.setDone();
        String response = "Nice! I've marked this task as done:" + System.lineSeparator() + task;
        ui.showMessage(response);
    }

    /**
     * Marks a task in the task list as undone.
     *
     * @param index The index of the task to be marked as undone.
     * @param ui The user interface to display the tasks found.
     */
    public void unmarkTask(int index, Ui ui) {
        assert index >= 0 : "Index should be non-negative.";
        assert index < taskCount : "Index should be less than the number of tasks.";
        assert ui != null : "Ui should not be null.";
        Task task = tasks.get(index);
        task.setUndone();
        String response = "OK, I've marked this task as not done yet:" + System.lineSeparator() + task;
        ui.showMessage(response);
    }

    /**
     * Deletes a task from the task list.
     *
     * @param index The index of the task to be deleted.
     * @param ui The user interface to display the deleted task.
     */
    public void deleteTask(int index, Ui ui) {
        assert index >= 0 : "Index should be non-negative.";
        assert index < taskCount : "Index should be less than the number of tasks.";
        assert ui != null : "Ui should not be null.";
        Task task = tasks.get(index);
        tasks.remove(index);
        taskCount--;
        String response = "Noted. I've removed this task:" + System.lineSeparator() + task
            + System.lineSeparator() + "Now you have " + taskCount + " task"
            + (taskCount > 1 ? "s" : "") + " in the list.";
        ui.showMessage(response);
    }
}
