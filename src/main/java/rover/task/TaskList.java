package rover.task;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import rover.exceptions.RoverException;
import rover.ui.Ui;

public class TaskList {

    private final ArrayList<Task> tasks;
    private int taskCount = 0;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

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

    public void showAllTasks(Ui ui) {
        ui.showLine();
        if (taskCount == 0) {
            ui.showMessage("There are no tasks in your list.");
            ui.showLine();
            return;
        }
        ui.showMessage("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            ui.showMessage((i + 1) + ". " + tasks.get(i));
        }
        ui.showLine();
    }

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

    public void markTask(int index, Ui ui) {
        Task task = tasks.get(index);
        task.setDone();
        ui.showLine();
        ui.showMessage("Nice! I've marked this task as done:");
        ui.showMessage(task.toString());
        ui.showLine();
    }

    public void unmarkTask(int index, Ui ui) {
        Task task = tasks.get(index);
        task.setUndone();
        ui.showLine();
        ui.showMessage("OK, I've marked this task as not done yet:");
        ui.showMessage(task.toString());
        ui.showLine();
    }

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

    public void showTasksBefore(String dateTime, Ui ui) {
        ui.showLine();
        ArrayList<Task> tasksBefore = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            Task task = tasks.get(i);
            try {
                if (task.isBefore(dateTime)) {
                    tasksBefore.add(task);
                }
            } catch (DateTimeParseException e) {
                ui.displayError("The date format should be 'dd/mm/yy' and the time format should be 'hh:mm'.");
                return;
            }
        }
        if (tasksBefore.isEmpty()) {
            ui.showMessage("There are no tasks before " + dateTime + ".");
        } else {
            ui.showMessage("Here are the tasks before " + dateTime + ":");
            for (int i = 0; i < tasksBefore.size(); i++) {
                ui.showMessage((i + 1) + ". " + tasksBefore.get(i));
            }
        }
        ui.showLine();
    }

    public void showTasksAfter(String dateTime, Ui ui) {
        ui.showLine();
        ArrayList<Task> tasksAfter = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            Task task = tasks.get(i);
            try {
                if (task.isAfter(dateTime)) {
                    tasksAfter.add(task);
                }
            } catch (DateTimeParseException e) {
                ui.displayError("The date format should be 'dd/mm/yy' and the time format should be 'hh:mm'.");
                return;
            }
        }
        if (tasksAfter.isEmpty()) {
            ui.showMessage("There are no tasks after " + dateTime + ".");
        } else {
            ui.showMessage("Here are the tasks after " + dateTime + ":");
            for (int i = 0; i < tasksAfter.size(); i++) {
                ui.showMessage((i + 1) + ". " + tasksAfter.get(i));
            }
        }
        ui.showLine();
    }

}
