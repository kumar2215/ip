package rover.task;

import java.time.format.DateTimeParseException;
import rover.exceptions.RoverException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) throws RoverException {
        this.description = description;
        if (this.description.isEmpty()) {
            throw new RoverException("The description of a task cannot be empty.");
        }
        this.isDone = false;
    }

    public void setDone() {
        this.isDone = true;
    }

    public void setUndone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task other) {
            return this.description.equals(other.description);
        }
        return false;
    }

    public abstract String getTaskString();

    public abstract boolean isBefore(String dateTime) throws DateTimeParseException;

    public abstract boolean isAfter(String dateTime) throws DateTimeParseException;

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
