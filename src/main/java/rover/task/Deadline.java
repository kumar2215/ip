package rover.task;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import rover.exceptions.RoverException;
import rover.parser.DateTimeParser;

/**
 * Represents a deadline task that can be added to the task list.
 * A deadline task has a description, a status that indicates whether it is done, and a deadline.
 */
public final class Deadline extends Task {

    private LocalDate byDate;
    private LocalTime byTime;
    private String by;
    private String byFullFormat;

    /**
     * Constructs a deadline task with the given description.
     * The description must be in the format "task /by (deadline)".
     * The deadline can be a date, time, or date and time.
     * If the deadline is a date, the time will be set to 00:00.
     * If the deadline is a time, the date will be set to the current date.
     * If the deadline is a date and time, the date and time will be set accordingly.
     *
     * @param description The description of the deadline task.
     * @throws RoverException If the description is not in the correct format.
     */
    public Deadline(String description) throws RoverException, DateTimeParseException {
        super(description);
        setByAndDescription(description);
        setByDateAndTime();
        setByFullFormat();
    }

    private void setByAndDescription(String description) throws RoverException {
        String[] parts = description.split(" /by ");
        this.description = parts[0];
        if (parts.length != 2) {
            throw new RoverException("A deadline task must be a task followed with '/by (deadline)'.");
        }
        this.by = parts[1];
    }

    private void setByDateAndTime() {
        String[] dateAndTime = by.split(" ");
        if (dateAndTime.length == 1) {
            // Deadline is a date only
            // The case where only time is given is omitted as it doesn't make sense
            // to create a deadline task on the same day with only a time
            this.byDate = DateTimeParser.parseDate(dateAndTime[0]);
            this.byTime = LocalTime.MAX; // Set to the end of the day
        } else {
            // Deadline is a date and time
            this.byDate = DateTimeParser.parseDate(dateAndTime[0]);
            this.byTime = DateTimeParser.parseTime(dateAndTime[1]);
        }
    }

    private void setByFullFormat() {
        this.byFullFormat = byDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy"));
        this.byFullFormat += " " + byTime.format(DateTimeFormatter.ofPattern("h:mm a")).toLowerCase();
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public boolean isBefore(String dateTime) throws DateTimeParseException {
        String[] parts = dateTime.split(" ");
        if (parts.length == 1) {
            try { // Interpret as a date only
                LocalDate otherDate = DateTimeParser.parseDate(dateTime);
                return byDate.isBefore(otherDate);
            } catch (DateTimeParseException e) {
                // Interpret as a time only
                LocalDateTime otherTime = DateTimeParser.parseDateTime(LocalDate.now() + " " + dateTime);
                return byDate.atTime(byTime).isBefore(otherTime);
            }
        } else { // Interpret as a date and time
            LocalDateTime otherDateTime = DateTimeParser.parseDateTime(dateTime);
            return byDate.atTime(byTime).isBefore(otherDateTime);
        }
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public boolean isAfter(String dateTime) {
        String[] parts = dateTime.split(" ");
        if (parts.length == 1) {
            try { // Interpret as a date only
                LocalDate otherDate = DateTimeParser.parseDate(dateTime);
                return byDate.isAfter(otherDate);
            } catch (DateTimeParseException e) {
                // Interpret as a time only
                LocalDateTime otherTime = DateTimeParser.parseDateTime(LocalDate.now() + " " + dateTime);
                return byDate.atTime(byTime).isAfter(otherTime);
            }
        } else { // Interpret as a date and time
            LocalDateTime otherDateTime = DateTimeParser.parseDateTime(dateTime);
            return byDate.atTime(byTime).isAfter(otherDateTime);
        }
    }

    /**
     * Compares this deadline task with the specified object for equality.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Deadline other) {
            return this.description.equals(other.description) && this.byDate.equals(other.byDate)
                    && this.byTime.equals(other.byTime);
        }
        return false;
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public String getTaskString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " /by " + by;
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byFullFormat + ")";
    }

}
