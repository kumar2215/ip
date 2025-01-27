public class Todo extends Task {

    public Todo (String description) throws RoverException {
        super(description);
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
