package rover.task;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import rover.exceptions.RoverException;
import rover.parser.DateTimeParser;

/**
 * Represents an event task.
 * An event task is a task that has a start date and/or time and an end date and/or time.
 */
public final class Event extends Task {

    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private String start;
    private String end;
    private String fromToFullFormat;

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
        setStartAndEnd();
        setStartDateAndTime();
        setEndDateAndTime();
        checkIfEndIsAfterStart();
        setFromToFullFormat();
    }

    private void setFromToFullFormat() {
        fromToFullFormat = "from "
            + startDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy")) + " "
            + startTime.format(DateTimeFormatter.ofPattern("h:mm a")).toLowerCase() + " to "
            + endDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy")) + " "
            + endTime.format(DateTimeFormatter.ofPattern("h:mm a")).toLowerCase();
    }

    private void checkIfEndIsAfterStart() throws RoverException {
        if (startDate.isAfter(endDate) || (startDate.isEqual(endDate) && startTime.isAfter(endTime))) {
            throw new RoverException("The start date and time must be before the end date and time.");
        }
    }

    private void setStartAndEnd() throws RoverException {
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
        this.end = parts2[1];
    }

    private void setStartDateAndTime() throws DateTimeParseException {
        try { // Try to parse the start as a date and time
            startDate = DateTimeParser.parseDateTime(start).toLocalDate();
            startTime = DateTimeParser.parseDateTime(start).toLocalTime();
        } catch (DateTimeParseException e) {
            try { // Try to parse the start as a date only
                this.startDate = DateTimeParser.parseDate(start);
                this.startTime = LocalTime.of(0, 0);
            } catch (DateTimeParseException e2) {
                // Try to parse the start as a time only
                this.startDate = LocalDate.now();
                this.startTime = DateTimeParser.parseTime(start);
            }
        }
    }

    private void setEndDateAndTime() throws DateTimeParseException {
        try { // Try to parse the end as a date and time
            endDate = DateTimeParser.parseDateTime(end).toLocalDate();
            endTime = DateTimeParser.parseDateTime(end).toLocalTime();
        } catch (DateTimeParseException e) {
            try { // Try to parse the end as a time only
                endDate = startDate;
                endTime = DateTimeParser.parseTime(end);
            } catch (DateTimeParseException e2) {
                // Try to parse the end as a date only
                endDate = DateTimeParser.parseDate(end);
                endTime = LocalTime.of(23, 59);
            }
        }
    }

    /**
     * {@code @InheritDoc} from Task
     * Event tasks are due before the start date and time.
     */
    @Override
    public boolean isBefore(String dateTime) {
        String[] parts = dateTime.split(" ");
        if (parts.length == 1) {
            try { // Interpret as a date only
                LocalDate otherDate = DateTimeParser.parseDate(dateTime);
                return startDate.isBefore(otherDate);
            } catch (DateTimeParseException e) {
                // Interpret as a time only
                LocalDateTime otherDateTime = DateTimeParser.parseDateTime(LocalDate.now() + " " + dateTime);
                return startDate.atTime(startTime).isBefore(otherDateTime);
            }
        } else { // Interpret as a date and time
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
            try { // Interpret as a date only
                LocalDate otherDate = DateTimeParser.parseDate(dateTime);
                return startDate.isAfter(otherDate);
            } catch (DateTimeParseException e) {
                // Interpret as a time only
                LocalDateTime otherDateTime = DateTimeParser.parseDateTime(LocalDate.now() + " " + dateTime);
                return startDate.atTime(startTime).isAfter(otherDateTime);
            }
        } else { // Interpret as a date and time
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
