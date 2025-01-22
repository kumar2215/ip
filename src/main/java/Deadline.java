public class Deadline extends Task {

    protected String by;

    public Deadline(String description) throws RoverException {
        super(description);
        String[] parts = description.split(" /by ");
        if (parts.length == 1) {
            throw new RoverException("A deadline task must be a task followed with '/by (deadline)'.");
        }
        this.by = parts[1];
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

}
