package rover.task;

import rover.exceptions.RoverException;

/**
 * Represents a todo task that can be added to the task list.
 * A todo task has a description and a status that indicates whether it is done.
 */
public final class Todo extends Task {

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
     * Checks if the task is due before the given date and time.
     * Todo tasks do not have a date and time and will always return false.
     *
     * @param dateTime The date and time to compare with.
     */
    @Override
    public boolean isBefore(String dateTime) {
        return false;
    }

    /**
     * Checks if the task is due after the given date and time.
     * Todo tasks do not have a date and time and will always return false.
     *
     * @param dateTime The date and time to compare with.
     */
    @Override
    public boolean isAfter(String dateTime) {
        return false;
    }

    /**
     * Compares this todo task with the specified object for equality.
     *
     * @param obj The object to compare with.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Todo other) {
            return this.description.equals(other.description);
        }
        return false;
    }

    /**
     * Returns the description of the task for saving to the file.
     */
    @Override
    public String getTaskString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns the string representation of the task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
