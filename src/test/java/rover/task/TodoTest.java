package rover.task;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

import rover.exceptions.RoverException;

public class TodoTest {
    @Test
    public void checkIfExceptionThrown_emptyStringInitialisation() {
        assertThrowsExactly(RoverException.class, () -> {
            new Todo("");
        });
    }

    @Test
    public void checkTaskString_beforeMarkingItDone() {
        try {
            Todo todo = new Todo("read book");
            assertEquals("T | 0 | read book", todo.getTaskString());
            assertEquals("[T][ ] read book", todo.toString());
        } catch (RoverException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkTaskString_afterMarkingItDone() {
        try {
            Todo todo = new Todo("read book");
            todo.setDone();
            assertEquals("T | 1 | read book", todo.getTaskString());
            assertEquals("[T][X] read book", todo.toString());
        } catch (RoverException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkIfIsBeforeAlwaysReturnsFalse() {
        try {
            Todo todo = new Todo("read book");
            assertFalse(todo.isBefore("2021-08-24T18:00"));
            assertFalse(todo.isBefore("garbage"));;
        } catch (RoverException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkIfIsAfterAlwaysReturnsFalse() {
        try {
            Todo todo = new Todo("read book");
            assertFalse(todo.isAfter("2021-08-24T18:00"));
            assertFalse(todo.isAfter("garbage"));;
        } catch (RoverException e) {
            System.out.println(e.getMessage());
        }
    }
}
