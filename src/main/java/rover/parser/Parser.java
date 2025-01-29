package rover.parser;

import java.time.format.DateTimeParseException;

import rover.command.AddCommand;
import rover.command.Command;
import rover.command.DeleteCommand;
import rover.command.EmptyCommand;
import rover.command.ExitCommand;
import rover.command.FindCommand;
import rover.command.InvalidCommand;
import rover.command.ListCommand;
import rover.command.MarkCommand;
import rover.command.RetrySaveCommand;
import rover.command.ShowAfterCommand;
import rover.command.ShowBeforeCommand;
import rover.command.UnmarkCommand;
import rover.exceptions.RoverException;
import rover.task.Deadline;
import rover.task.Event;
import rover.task.Task;
import rover.task.TaskAction;
import rover.task.Todo;

/**
 * Parser class to parse user input
 */
public class Parser {

    /**
     * Constructor for Parser class
     */
    public Parser() {}

    /**
     * Parses the user input and returns the corresponding command
     *
     * @param args User input
     * @return Command object
     */
    public Command parseCommand(String args) {
        args = args.trim();
        String input = args.toLowerCase();
        if (input.isEmpty()) {
            return new EmptyCommand(args);
        } else if (input.equals("bye")) {
            return new ExitCommand(args);
        } else if (input.equals("y") || input.equals("n") || input.equals("yes") || input.equals("no")) {
            return new RetrySaveCommand(args);
        } else if (input.equals("list")) {
            return new ListCommand(args);
        } else if (input.startsWith("find")) {
            return new FindCommand(args);
        } else if (input.startsWith("mark")) {
            return new MarkCommand(args);
        } else if (input.startsWith("unmark")) {
            return new UnmarkCommand(args);
        } else if (input.startsWith("delete")) {
            return new DeleteCommand(args);
        } else if (input.startsWith("show before")) {
            return new ShowBeforeCommand(args);
        } else if (input.startsWith("show after")) {
            return new ShowAfterCommand(args);
        } else if (input.startsWith("todo") || input.startsWith("deadline") || input.startsWith("event")) {
            return new AddCommand(args);
        } else {
            return new InvalidCommand(args);
        }
    }

    /**
     * Parses the task description and returns the corresponding task object
     *
     * @param description Task description
     * @return Task object
     * @throws RoverException If the task description is invalid
     * @throws DateTimeParseException If the date and time format is invalid
     */
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

    /**
     * Parses the task number and returns the corresponding task index
     *
     * @param taskNumber Task number
     * @param numberOfTasks Number of tasks
     * @param taskAction Task action used to determine error message
     * @return Task index
     * @throws RoverException If the task number is invalid
     */
    public int parseTaskNumber(String taskNumber, int numberOfTasks, TaskAction taskAction) throws RoverException {
        String action = taskAction == TaskAction.MARK_DONE
                ? "marked as done"
                : taskAction == TaskAction.MARK_UNDONE
                ? "marked as not done"
                : "deleted";
        if (taskNumber.isEmpty()) {
            throw new RoverException("Please specify the task number to be " + action + ".");
        }
        int index;
        try {
            index = Integer.parseInt(taskNumber) - 1;
        } catch (NumberFormatException e) {
            throw new RoverException("Please specify a valid task number to be " + action + ".");
        }
        if (index < 0 || index >= numberOfTasks) {
            throw new RoverException("Please specify a valid task number to be " + action + ".\n"
                + "You only have " + numberOfTasks + " tasks in total.");
        }
        return index;
    }

}
