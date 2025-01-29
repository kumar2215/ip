package rover.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A utility class for parsing date and time strings.
 */
public class DateTimeParser {

    public static final String[] DATE_FORMATS = {
        "yyyy/MM/dd",
        "yyyy-MM-dd",
        "dd-MM-yyyy",
        "dd/MM/yyyy",
        "dd/MM/yy",
        "dd-MM-yy"
    };

    public static final String[] TIME_FORMATS = {
        "HH:mm",
        "HHmm",
        "h:mma",
        "h.mma",
        "hha",
        "ha"
    };

    /**
     * Parses a date and time string using the supported formats.
     *
     * @param dateTimeString the date and time string to parse
     * @return LocalDateTime object representing the parsed date and time
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        for (String dateFormat : DATE_FORMATS) {
            for (String timeFormat : TIME_FORMATS) {
                String format = dateFormat + " " + timeFormat;
                try {
                    return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(format));
                } catch (DateTimeParseException e) {
                    // Continue to the next formatter
                }
            }
        }
        throw new DateTimeParseException("Unable to parse date and time: " + dateTimeString, dateTimeString, 0);
    }

    /**
     * Parses a date string using the supported formats.
     *
     * @param dateString the date string to parse
     * @return LocalDate object representing the parsed date
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
        for (String format : DATE_FORMATS) {
            try {
                return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(format));
            } catch (DateTimeParseException e) {
                // Continue to the next formatter
            }
        }
        throw new DateTimeParseException("Unable to parse date: " + dateString, dateString, 0);
    }

    /**
     * Parses a time string using the supported formats.
     *
     * @param timeString the time string to parse
     * @return LocalTime object representing the parsed time
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalTime parseTime(String timeString) throws DateTimeParseException {
        for (String format : TIME_FORMATS) {
            try {
                return LocalTime.parse(timeString, DateTimeFormatter.ofPattern(format));
            } catch (DateTimeParseException e) {
                // Continue to the next formatter
            }
        }
        throw new DateTimeParseException("Unable to parse time: " + timeString, timeString, 0);
    }
}
