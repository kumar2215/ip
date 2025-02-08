package rover.task;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import rover.exceptions.RoverException;

public class DeadlineTest {

    @Test
    public void checkIfExceptionThrown_emptyStringInitialisation() {
        assertThrowsExactly(RoverException.class, () -> {
            new Deadline("");
        });
    }

    @Test
    public void checkIfExceptionThrown_noByKeyword() {
        assertThrowsExactly(RoverException.class, () -> {
            new Deadline("read book by 2021-08-24");
        });
        assertThrowsExactly(RoverException.class, () -> {
            new Deadline("read book 2021-08-24");
        });
    }

    @Test
    public void checkIfExceptionThrown_noDate() {
        assertThrowsExactly(RoverException.class, () -> {
            new Deadline("read book /by ");
        });
        assertThrowsExactly(RoverException.class, () -> {
            new Deadline("read book /by");
        });
    }

    @Test
    public void checkIfExceptionThrown_improperDate() {
        assertThrowsExactly(DateTimeParseException.class, () -> {
            new Deadline("read book /by 240921");
        });
    }

    @Test
    public void checkIfExceptionNotThrown_properDate() {
        assertDoesNotThrow(() -> {
            new Deadline("read book /by 2021-08-24");
        });
        assertDoesNotThrow(() -> {
            new Deadline("read book /by 2021-08-24 1800");
        });
    }

    @Test
    public void checkTaskString_beforeMarkingItDone() {
        try {
            Deadline deadline = new Deadline("do homework /by 2021-08-24");
            assertEquals("D | 0 | do homework /by 2021-08-24", deadline.getTaskString());
            assertEquals("[D][ ] do homework (by: Tuesday, 24 August 2021 11:59 pm)", deadline.toString());
        } catch (RoverException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkTaskString_afterMarkingItDone() {
        try {
            Deadline deadline = new Deadline("do homework /by 2021-08-24");
            deadline.setDone();
            assertEquals("D | 1 | do homework /by 2021-08-24", deadline.getTaskString());
            assertEquals("[D][X] do homework (by: Tuesday, 24 August 2021 11:59 pm)", deadline.toString());
        } catch (RoverException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkIfIsBefore() {
        try {
            Deadline deadline = new Deadline("do homework /by 2021-08-24");
            assertFalse(deadline.isBefore("2021-08-24 18:00"));
            assertFalse(deadline.isBefore("20-08-21"));
            assertTrue(deadline.isBefore("26/08/24"));
        } catch (DateTimeParseException | RoverException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkIfIsAfter() {
        try {
            Deadline deadline = new Deadline("do homework /by 2021-08-24");
            assertTrue(deadline.isAfter("2021-08-24 18:00"));
            assertTrue(deadline.isAfter("20-08-21"));
            assertFalse(deadline.isAfter("26/08/24"));;
        } catch (DateTimeParseException | RoverException e) {
            System.out.println(e.getMessage());
        }
    }
}
