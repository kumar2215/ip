package rover.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import rover.command.AddCommand;
import rover.command.DeleteCommand;
import rover.command.EmptyCommand;
import rover.command.ExitCommand;
import rover.command.FindCommand;
import rover.command.InvalidCommand;
import rover.command.ListCommand;
import rover.command.MarkCommand;
import rover.command.RetrySaveCommand;
import rover.command.SetCommand;
import rover.command.ShowAfterCommand;
import rover.command.ShowBeforeCommand;
import rover.command.UnmarkCommand;
import rover.exceptions.RoverException;
import rover.preferences.PreferenceOption;
import rover.task.Deadline;
import rover.task.Event;
import rover.task.TaskAction;
import rover.task.Todo;

public class ParserTest {

    @Test
    public void checkParseCommand() {
        Parser parser = new Parser();
        assertEquals(new AddCommand("todo read book"), parser.parseCommand("todo read book"));
        assertEquals(new AddCommand("deadline return book /by 2021-08-24 1800"),
            parser.parseCommand("deadline return book /by 2021-08-24 1800"));
        assertEquals(new AddCommand("event project meeting /at 2021-08-24 1800"),
            parser.parseCommand("event project meeting /at 2021-08-24 1800"));
        assertEquals(new DeleteCommand("delete 1"), parser.parseCommand("delete 1"));
        assertEquals(new EmptyCommand(""), parser.parseCommand(""));
        assertEquals(new ExitCommand("bye"), parser.parseCommand("bye"));
        assertEquals(new FindCommand("find book"), parser.parseCommand("find book"));
        assertEquals(new InvalidCommand("whatever"), parser.parseCommand("whatever"));
        assertEquals(new ListCommand("list"), parser.parseCommand("list"));
        assertEquals(new MarkCommand("mark 1"), parser.parseCommand("mark 1"));
        assertEquals(new SetCommand("set name John"), parser.parseCommand("set name John"));
        assertEquals(new ShowAfterCommand("show after 21/08/24"), parser.parseCommand("show after 21/08/24"));
        assertEquals(new ShowBeforeCommand("show before 21/08/24"), parser.parseCommand("show before 21/08/24"));
        assertEquals(new UnmarkCommand("unmark 1"), parser.parseCommand("unmark 1"));
    }

    @Test
    public void checkIsPreviousCommandBye() {
        Parser parser = new Parser();
        assertEquals(new InvalidCommand("yes"), parser.parseCommand("yes"));
        parser.parseCommand("bye");
        assertEquals(new RetrySaveCommand("yes"), parser.parseCommand("yes"));
    }

    @Test
    public void checkParsePreferenceOption() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> {
            assertEquals(PreferenceOption.NAME, parser.parsePreferenceOption("name"));
            assertEquals(PreferenceOption.USER_IMAGE, parser.parsePreferenceOption("userImage"));
            assertEquals(PreferenceOption.ROVER_IMAGE, parser.parsePreferenceOption("roverImage"));
        });
        assertThrowsExactly(RoverException.class, () -> parser.parsePreferenceOption("backgroundImage"));
    }

    @Test
    public void checkParseTaskDescription() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> {
            assertEquals(new Todo("read book"), parser.parseTaskDescription("todo read book"));
            assertEquals(new Deadline("return book /by 24/08/21 1800"),
                parser.parseTaskDescription("deadline return book /by 24/08/21 1800"));
            assertEquals(new Event("project meeting /from 24/08/21 1800 /to 1900"),
                parser.parseTaskDescription("event project meeting /from 24/08/21 1800 /to 1900"));
        });
    }

    @Test
    public void checkParseTaskDescription2() {
        Parser parser = new Parser();
        try {
            parser.parseTaskDescription("deadline return book by 24/08/21 1800");
        } catch (RoverException e) {
            assertEquals("A deadline task must be a task followed with '/by (deadline)'.", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        try {
            parser.parseTaskDescription("deadline return book /by 240821 1800");
        } catch (DateTimeParseException e) {
            assertEquals("Unable to parse date: 240821", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        try {
            parser.parseTaskDescription("event project meeting /from 24/08/21 1800 to 1900");
        } catch (RoverException e) {
            assertEquals("An event task must be a task followed with '/from (start) /to (end)'.",
                e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }
    }

    @Test
    public void checkParseTaskNumber() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> assertEquals(3, parser.parseTaskNumber("4", 4,
            TaskAction.MARK_DONE)));

        try {
            parser.parseTaskNumber("", 2, TaskAction.DELETE);
        } catch (RoverException e) {
            assertEquals("Please specify the task number to be deleted.", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        try {
            parser.parseTaskNumber("read book", 3, TaskAction.MARK_DONE);
        } catch (RoverException e) {
            assertEquals("Please specify a valid task number to be marked as done.", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        try {
            parser.parseTaskNumber("-1", 4, TaskAction.MARK_DONE);
        } catch (RoverException e) {
            assertEquals("Please specify a valid task number to be marked as done.\n"
                + "You only have 4 tasks in total.", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        try {
            parser.parseTaskNumber("5", 4, TaskAction.MARK_UNDONE);
        } catch (RoverException e) {
            assertEquals("Please specify a valid task number to be marked as not done.\n"
                + "You only have 4 tasks in total.", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }
    }
}
