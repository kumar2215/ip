import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

public class Event extends Task {

    protected LocalDate startDate;
    protected LocalTime startTime;
    protected LocalDate endDate;
    protected LocalTime endTime;
    protected String start;
    protected String end;
    protected String fromToFullFormat;

    public Event(String description) throws RoverException, DateTimeParseException {
        super(description);
        String[] parts = description.split(" /from ");
        if (parts.length == 1) {
            throw new RoverException("An event task must be a task followed with '/from (start) /to (end)'.");
        }
        this.description = parts[0];
        String[] parts2 = parts[1].split(" /to ");
        if (parts2.length == 1) {
            throw new RoverException("An event task must be a task followed with '/from (start) /to (end)'.");
        }
        this.start = parts2[0];
        try {
            this.startDate = DateTimeParser.parseDateTime(start).toLocalDate();
            this.startTime = DateTimeParser.parseDateTime(start).toLocalTime();
        } catch (DateTimeParseException e) {
            this.startDate = LocalDate.now();
            this.startTime = DateTimeParser.parseTime(start);
        }
        this.end = parts2[1];
        try {
            this.endDate = DateTimeParser.parseDateTime(end).toLocalDate();
            this.endTime = DateTimeParser.parseDateTime(end).toLocalTime();
        } catch (DateTimeParseException e) {
            this.endDate = startDate;
            this.endTime = DateTimeParser.parseTime(end);
        }
        this.fromToFullFormat = "from "
                + startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) + " "
                + startTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                + " to " + endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) + " "
                + endTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
    }

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

    @Override
    public String getTaskString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " /from " + start + " /to " + end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (" + fromToFullFormat + ")";
    }
}
