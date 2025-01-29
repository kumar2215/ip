package rover.task;

import rover.parser.DateTimeParser;

import rover.exceptions.RoverException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Deadline extends Task {

    protected LocalDate byDate;
    protected LocalTime byTime;
    protected String by;
    protected String byFullFormat;

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

    @Override
    public String getTaskString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " /by " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byFullFormat + ")";
    }

}
