package rover.task;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

import rover.exceptions.RoverException;
import rover.parser.DateTimeParser;

/**
 * Represents a deadline task that can be added to the task list.
 * A deadline task has a description, a status that indicates whether it is done, and a deadline.
 */
public final class Deadline extends Task {

    private final LocalDate byDate;
    private final LocalTime byTime;
    private final String by;
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
        String[] parts = description.split(" /by ");
        this.description = parts[0];
        if (parts.length != 2) {
            throw new RoverException("A deadline task must be a task followed with '/by (deadline)'.");
        }
        String[] dateAndTime = parts[1].split(" ");
        if (dateAndTime.length == 1) {
            this.byDate = DateTimeParser.parseDate(dateAndTime[0]);
            this.byTime = null;
        } else {
            this.byDate = DateTimeParser.parseDate(dateAndTime[0]);
            this.byTime = DateTimeParser.parseTime(dateAndTime[1]);
        }
        this.by = parts[1];
        this.byFullFormat = byDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        if (byTime != null) {
            this.byFullFormat += " " + byTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
        }
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public boolean isBefore(String dateTime) throws DateTimeParseException {
        String[] parts = dateTime.split(" ");
        if (parts.length == 1) {
            try {
                LocalDate otherDate = DateTimeParser.parseDate(dateTime);
                return byDate.isBefore(otherDate);
            } catch (DateTimeParseException e) {
                LocalDateTime otherTime = DateTimeParser.parseDateTime(LocalDate.now() + " " + dateTime);
                return byDate.atTime(byTime == null ? LocalTime.MAX : byTime).isBefore(otherTime);
            }
        } else {
            LocalDateTime otherDateTime = DateTimeParser.parseDateTime(dateTime);
            return byDate.atTime(byTime == null ? LocalTime.MAX : byTime).isBefore(otherDateTime);
        }
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public boolean isAfter(String dateTime) {
        String[] parts = dateTime.split(" ");
        if (parts.length == 1) {
            try {
                LocalDate otherDate = DateTimeParser.parseDate(dateTime);
                return byDate.isAfter(otherDate);
            } catch (DateTimeParseException e) {
                LocalDateTime otherTime = DateTimeParser.parseDateTime(LocalDate.now() + " " + dateTime);
                return byDate.atTime(byTime == null ? LocalTime.MAX : byTime).isAfter(otherTime);
            }
        } else {
            LocalDateTime otherDateTime = DateTimeParser.parseDateTime(dateTime);
            return byDate.atTime(byTime == null ? LocalTime.MAX : byTime).isAfter(otherDateTime);
        }
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
