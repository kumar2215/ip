public class Event extends Task {

    protected String start;
    protected String end;

    public Event(String description) throws RoverException {
        super(description);
        String[] parts = description.split(" /from ");
        if (parts.length == 1) {
            throw new RoverException("An event task must be a task followed with '/from (start) /to (end)'.");
        }
        String[] parts2 = parts[1].split(" /to ");
        if (parts2.length == 1) {
            throw new RoverException("An event task must be a task followed with '/from (start) /to (end)'.");
        }
        this.start = parts2[0];
        this.end = parts2[1];
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
