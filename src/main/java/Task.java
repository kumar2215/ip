public class Task {
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

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
