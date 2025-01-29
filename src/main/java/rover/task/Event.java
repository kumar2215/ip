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
 * Represents an event task.
 * An event task is a task that has a start date and/or time and an end date and/or time.
 */
public class Event extends Task {

    protected LocalDate startDate;
    protected LocalTime startTime;
    protected LocalDate endDate;
    protected LocalTime endTime;
    protected String start;
    protected String end;
    protected String fromToFullFormat;

    /**
     * Constructs an event task with the given description.
     * The description must be in the format "task /from (start) /to (end)".
     * The start and end can be a date, time, or date and time.
     * If the start or end is a date, the time will be set to 00:00.
     * If the start or end is a time, the date will be set to the current date.
     * If the start or end is a date and time, the date and time will be set accordingly.
     * The start date and time must be before the end date and time.
     *
     * @param description The description of the event task.
     * @throws RoverException If the description is not in the correct format or the start date and time is after
     *      the end date and time.
     */
    public Event(String description) throws RoverException, DateTimeParseException {
        super(description);
        String[] parts = description.split(" /from ");
        if (parts.length != 2) {
            throw new RoverException("An event task must be a task followed with '/from (start) /to (end)'.");
        }
        this.description = parts[0];
        String[] parts2 = parts[1].split(" /to ");
        if (parts2.length != 2) {
            throw new RoverException("An event task must be a task followed with '/from (start) /to (end)'.");
        }
        this.start = parts2[0];
        try {
            this.startDate = DateTimeParser.parseDateTime(start).toLocalDate();
            this.startTime = DateTimeParser.parseDateTime(start).toLocalTime();
        } catch (DateTimeParseException e) {
            try {
                this.startDate = DateTimeParser.parseDate(start);
                this.startTime = LocalTime.of(0, 0);
            } catch (DateTimeParseException e2) {
                this.startDate = LocalDate.now();
                this.startTime = DateTimeParser.parseTime(start);
            }
        }
        this.end = parts2[1];
        try {
            this.endDate = DateTimeParser.parseDateTime(end).toLocalDate();
            this.endTime = DateTimeParser.parseDateTime(end).toLocalTime();
        } catch (DateTimeParseException e) {
            try {
                this.endDate = startDate;
                this.endTime = DateTimeParser.parseTime(end);
            } catch (DateTimeParseException e2) {
                this.endDate = DateTimeParser.parseDate(end);
                this.endTime = LocalTime.of(23, 59);
            }
        }
        if (startDate.isAfter(endDate) || (startDate.isEqual(endDate) && startTime.isAfter(endTime))) {
            throw new RoverException("The start date and time must be before the end date and time.");
        }
        this.fromToFullFormat = "from "
            + startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) + " "
            + startTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
            + " to " + endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) + " "
            + endTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
    }

    /**
     * {@code @InheritDoc} from Task
     * Event tasks are due before the start date and time.
     */
    @Override
    public boolean isBefore(String dateTime) {
        String[] parts = dateTime.split(" ");
        if (parts.length == 1) {
            try {
                LocalDate otherDate = DateTimeParser.parseDate(dateTime);
                return startDate.isBefore(otherDate);
            } catch (DateTimeParseException e) {
                LocalDateTime otherDateTime = DateTimeParser.parseDateTime(LocalDate.now() + " " + dateTime);
                return startDate.atTime(startTime).isBefore(otherDateTime);
            }
        } else {
            LocalDateTime otherDateTime = DateTimeParser.parseDateTime(dateTime);
            return startDate.atTime(startTime).isBefore(otherDateTime);
        }
    }

    /**
     * {@code @InheritDoc} from Task
     * Event tasks are due before the end date and time.
     */
    @Override
    public boolean isAfter(String dateTime) {
        String[] parts = dateTime.split(" ");
        if (parts.length == 1) {
            try {
                LocalDate otherDate = DateTimeParser.parseDate(dateTime);
                return startDate.isAfter(otherDate);
            } catch (DateTimeParseException e) {
                LocalDateTime otherDateTime = DateTimeParser.parseDateTime(LocalDate.now() + " " + dateTime);
                return startDate.atTime(startTime).isAfter(otherDateTime);
            }
        } else {
            LocalDateTime otherDateTime = DateTimeParser.parseDateTime(dateTime);
            return startDate.atTime(startTime).isAfter(otherDateTime);
        }
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public String getTaskString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " /from " + start + " /to " + end;
    }

    /**
     * {@code @InheritDoc} from Task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (" + fromToFullFormat + ")";
    }
}
