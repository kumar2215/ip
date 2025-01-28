package rover.task;

import rover.exceptions.RoverException;

public class Todo extends Task {

    public Todo (String description) throws RoverException {
        super(description);
    }

    @Override
    public boolean isBefore(String dateTime) {
        return false;
    }

    @Override
    public boolean isAfter(String dateTime) {
        return false;
    }

    @Override
    public String getTaskString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
