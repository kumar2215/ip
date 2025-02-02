package rover.task;

import java.time.format.DateTimeParseException;

import rover.exceptions.RoverException;

/**
 * Represents a task that can be added to the task list.
 * A task has a description and a status that indicates whether it is done.
 */
public abstract sealed class Task permits Todo, Deadline, Event {
    protected String description;
    protected boolean isDone;

    /**
     * Constructor for a task.
     *
     * @param description The description of the task.
     * @throws RoverException If the description is empty.
     */
    public Task(String description) throws RoverException {
        this.description = description;
        if (this.description.isEmpty()) {
            throw new RoverException("The description of a task cannot be empty.");
        }
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as undone.
     */
    public void setUndone() {
        this.isDone = false;
    }

    private String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Overrides the equals method to compare tasks based on their description.
     *
     * @param obj The object to compare with.
     * @return True if the tasks have the same description, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task other) {
            return this.description.equals(other.description);
        }
        return false;
    }

    /**
     * Returns the description of the task for saving to the file.
     *
     * @return The description of the task.
     */
    public abstract String getTaskString();

    /**
     * Checks if the task is due before the given date and time.
     *
     * @param dateTime The date and time to compare with.
     * @return True if the task is due before the given date and time, false otherwise.
     * @throws DateTimeParseException If the date and time is in the wrong format.
     */
    public abstract boolean isBefore(String dateTime) throws DateTimeParseException;

    /**
     * Checks if the task is due after the given date and time.
     *
     * @param dateTime The date and time to compare with.
     * @return True if the task is due after the given date and time, false otherwise.
     * @throws DateTimeParseException If the date and time is in the wrong format.
     */
    public abstract boolean isAfter(String dateTime) throws DateTimeParseException;

    /**
     * Returns the string representation of the task.
     *
     * @return The string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
