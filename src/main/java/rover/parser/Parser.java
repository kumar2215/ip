package rover.parser;

import java.time.format.DateTimeParseException;

import rover.command.Command;
import rover.exceptions.RoverException;

import rover.task.Deadline;
import rover.task.Event;
import rover.task.Todo;
import rover.task.Task;
import rover.task.TaskAction;

public class Parser {

    public Parser() {}

    public Command parseCommand(String input) {
        input = input.toLowerCase().trim();
        if (input.isEmpty()) {
            return Command.EMPTY;
        } else if (input.equals("bye")) {
            return Command.EXIT;
        } else if (input.equals("list")) {
            return Command.LIST_TASKS;
        } else if (input.startsWith("mark")) {
            return Command.MARK_TASK;
        } else if (input.startsWith("unmark")) {
            return Command.UNMARK_TASK;
        } else if (input.startsWith("delete")) {
            return Command.DELETE_TASK;
        } else if (input.startsWith("show before")) {
            return Command.SHOW_TASKS_BEFORE;
        } else if (input.startsWith("show after")) {
            return Command.SHOW_TASKS_AFTER;
        } else if (input.startsWith("todo") || input.startsWith("deadline") || input.startsWith("event")) {
            return Command.ADD_TASK;
        } else {
            return Command.INVALID;
        }
    }

    public Task parseTaskDescription(String description) throws RoverException, DateTimeParseException {
        Task newTask;
        description = description.trim();
        if (description.toLowerCase().startsWith("deadline")) {
            newTask = new Deadline(description.substring(8).trim());
        } else if (description.toLowerCase().startsWith("event")) {
            newTask = new Event(description.substring(5).trim());
        } else if (description.toLowerCase().startsWith("todo")) {
            newTask = new Todo(description.substring(4).trim());
        } else {
            throw new RoverException("Not a valid task type.");
        }
        return newTask;
    }

    public int parseTaskNumber(String taskNumber, int numberOfTasks, TaskAction taskAction) throws RoverException {
        String action = taskAction == TaskAction.MARK_DONE
                ? "marked as done"
                : taskAction == TaskAction.MARK_UNDONE
                ? "marked as not done"
                : "deleted";
        if (taskNumber.isEmpty()) {
            throw new RoverException("Please specify the task number to be " + action  + ".");
        }
        int index;
        try {
            index = Integer.parseInt(taskNumber) - 1;
        } catch (NumberFormatException e) {
            throw new RoverException("Please specify a valid task number to be " + action + ".");
        }
        if (index < 0 || index >= numberOfTasks) {
            throw new RoverException("Please specify a valid task number to be " + action + ".\n" +
                    "You only have " + numberOfTasks + " tasks in total.");
        }
        return index;
    }

}
