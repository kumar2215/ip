package rover.task;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import rover.exceptions.RoverException;

public class EventTest {

    @Test
    public void checkIfExceptionThrown_emptyStringInitialisation() {
        assertThrowsExactly(RoverException.class, () -> new Event(""));
    }

    @Test
    public void checkIfExceptionThrown_noFromKeyword() {
        assertThrowsExactly(RoverException.class, () -> new Event("read book 2021-08-24 /to 2021-08-25"));
        assertThrowsExactly(RoverException.class, () -> new Event("read book /to 2021-08-25"));
    }

    @Test
    public void checkIfExceptionThrown_noToKeyword() {
        assertThrowsExactly(RoverException.class, () -> new Event("read book /from 2021-08-24 1800"));
        assertThrowsExactly(RoverException.class, () -> new Event("read book /from 2021-08-24 1800 /to "));
    }

    @Test
    public void checkIfExceptionThrown_noDate() {
        assertThrowsExactly(RoverException.class, () -> new Event("read book /from /to 2021-08-25 1800"));
        assertThrowsExactly(RoverException.class, () -> new Event("read book /from 2021-08-24 1800 /to "));
    }

    @Test
    public void checkIfExceptionThrown_improperDate() {
        assertThrowsExactly(DateTimeParseException.class, () -> new Event("read book /from 240921 /to 250921"));
        assertThrowsExactly(DateTimeParseException.class, () ->
            new Event("read book /from 2030-08-24 1800 /to 250921"));
        assertThrowsExactly(RoverException.class, () ->
            new Event("read book /from 24/08/30 1900 /to 2030-08-24 1800"));
    }

    @Test
    public void checkIfExceptionNotThrown_properDate() {
        assertDoesNotThrow(() -> {
            new Event("School Camp /from 2030-08-24 /to 2030-08-27");
        });
        assertDoesNotThrow(() -> {
            new Event("School Camp /from 2030-08-24 1800 /to 2030-08-25 1800");
        });
    }

    @Test
    public void checkTaskString_beforeMarkingItDone() {
        try {
            Event event = new Event("School Camp /from 2021-08-24 1800 /to 2021-08-27 1800");
            assertEquals("E | 0 | School Camp /from 2021-08-24 1800 /to 2021-08-27 1800", event.getTaskString());
            assertEquals("[E][ ] School Camp (from Tuesday, 24 August, 2021 6:00 pm to Friday, "
                + "27 August, 2021 6:00 pm)", event.toString());

            Event event2 = new Event("School Camp /from 2021-08-24 /to 2021-08-27");
            assertEquals("E | 0 | School Camp /from 2021-08-24 /to 2021-08-27", event2.getTaskString());
            assertEquals("[E][ ] School Camp (from Tuesday, 24 August, 2021 12:00 am to Friday, "
                + "27 August, 2021 11:59 pm)", event2.toString());
        } catch (RoverException | DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkTaskString_afterMarkingItDone() {
        try {
            Event event = new Event("School Camp /from 2021-08-24 1800 /to 2021-08-27 1800");
            event.setDone();
            assertEquals("E | 1 | School Camp /from 2021-08-24 1800 /to 2021-08-27 1800", event.getTaskString());
            assertEquals("[E][X] School Camp (from Tuesday, 24 August, 2021 6:00 pm to Friday, "
                + "27 August, 2021 6:00 pm)", event.toString());

            Event event2 = new Event("School Camp /from 2021-08-24 /to 2021-08-27");
            event2.setDone();
            assertEquals("E | 1 | School Camp /from 2021-08-24 /to 2021-08-27", event2.getTaskString());
            assertEquals("[E][X] School Camp (from Tuesday, 24 August, 2021 12:00 am to Friday, "
                + "27 August, 2021 11:59 pm)", event2.toString());
        } catch (RoverException | DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkIsBefore() {
        try {
            Event event = new Event("School Camp /from 2021-08-24 1800 /to 2021-08-27 1800");
            assertFalse(event.isBefore("2021-08-24 1759"));
            assertFalse(event.isBefore("2021-08-24"));
            assertFalse(event.isBefore("24-08-21 1800"));
            assertTrue(event.isBefore("24/08/2021 1801"));
            assertTrue(event.isBefore("27/08/21 1759"));
            assertTrue(event.isBefore("27-08-2021 1800"));
            assertTrue(event.isBefore("2021-08-27 1801"));
            assertTrue(event.isBefore("2021-08-28"));
        } catch (RoverException | DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkIsAfter() {
        try {
            Event event = new Event("School Camp /from 2021-08-24 1800 /to 2021-08-27 1800");
            assertTrue(event.isAfter("2021-08-24 1759"));
            assertFalse(event.isAfter("2021-08-24"));
            assertTrue(event.isAfter("24-08-21 1759"));
            assertFalse(event.isAfter("24/08/2021 1800"));
            assertFalse(event.isAfter("27/08/21 1759"));
            assertFalse(event.isAfter("27-08-2021 1800"));
            assertFalse(event.isAfter("2021-08-27 1759"));
            assertFalse(event.isAfter("2021-08-28"));
        } catch (RoverException | DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
