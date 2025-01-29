package rover.task;

import rover.exceptions.RoverException;

/**
 * Represents a todo task that can be added to the task list.
 * A todo task has a description and a status that indicates whether it is done.
 */
public class Todo extends Task {

    /**
     * Constructor for a todo task.
     *
     * @param description The description of the todo task.
     * @throws RoverException If the description is empty.
     */
    public Todo(String description) throws RoverException {
        super(description);
    }

    /**
     * {@code @InheritDoc} from Task
     * Todo tasks do not have a date and time and will always return false.
     *
     * @param dateTime The date and time to compare with.
     */
    @Override
    public boolean isBefore(String dateTime) {
        return false;
    }

    /**
     * {@code @InheritDoc} from Task
     * Todo tasks do not have a date and time and will always return false.
     *
     * @param dateTime The date and time to compare with.
     */
    @Override
    public boolean isAfter(String dateTime) {
        return false;
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public String getTaskString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
