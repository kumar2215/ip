package rover.task;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import rover.exceptions.RoverException;
import rover.ui.TextUi;
import rover.ui.Ui;

public class TaskListTest {

    @Test
    public void checkEmptyTaskList() {
        assertEquals(0, new TaskList().getNumberOfTasks());
        try {
            assertEquals(0, new TaskList(null).getNumberOfTasks());
        } catch (RoverException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void checkIfExceptionThrown_corruptedTaskString() {
        try {
            new TaskList(null, "T | 2 | read book");
        } catch (RoverException e) {
            assertEquals("Possible corruption in saved tasks.", e.getMessage());
        }
        try {
            new TaskList(null, "X | 0 | read book");
        } catch (RoverException e) {
            assertEquals("Possible corruption in saved tasks.", e.getMessage());
        }
        try {
            new TaskList(null, "T | 0 |read book");
        } catch (RoverException e) {
            assertEquals("Possible corruption in saved tasks.", e.getMessage());
        }
    }

    @Test
    public void checkIfExceptionThrown_corruptedTaskString2() {
        try {
            new TaskList(null, "D | 0 | read book 10/12/21");
        } catch (RoverException e) {
            assertEquals("A deadline task must be a task followed with '/by (deadline)'.", e.getMessage());
        }
        try {
            new TaskList(null, "D | 1 | read book /by 10/12/21 /by 09/08/21");
        } catch (RoverException e) {
            assertEquals("A deadline task must be a task followed with '/by (deadline)'.", e.getMessage());
        }
        try {
            new TaskList(null, "D | 0 | read book /by 10/12/");
        } catch (RoverException e) {
            fail("This exception should not be thrown");
        } catch (DateTimeParseException e) {
            assertEquals("Unable to parse date: 10/12/", e.getMessage());
        }
    }

    @Test
    public void checkIfExceptionThrown_corruptedTaskString3() {
        Ui ui = new TextUi();
        try {
            new TaskList(ui, "E | 0 | read book 10/12/21 09/08/21");
        } catch (RoverException e) {
            assertEquals("An event task must be a task followed with '/from (start) /to (end)'.", e.getMessage());
        }
        try {
            new TaskList(ui, "E | 1 | read book /from 10/12/21 /from 09/08/21");
        } catch (RoverException e) {
            assertEquals("An event task must be a task followed with '/from (start) /to (end)'.", e.getMessage());
        }
        try {
            new TaskList(ui, "E | 0 | read book /from 10/12/21 09/08/21");
        } catch (RoverException e) {
            assertEquals("An event task must be a task followed with '/from (start) /to (end)'.", e.getMessage());
        }
        try {
            new TaskList(ui, "E | 0 | read book /from 10/12/21 /to 09/08/");
        } catch (RoverException e) {
            fail("This exception should not be thrown");
        } catch (DateTimeParseException e) {
            assertEquals("Unable to parse date: 09/08/", e.getMessage());
        }
        try {
            new TaskList(ui, "E | 0 | read book /from 10/12/21 /to 09/08/21");
        } catch (RoverException e) {
            assertEquals("The start date and time must be before the end date and time.", e.getMessage());
        }
    }

    @Test
    public void checkTaskList_forProperStrings() {
        String[] taskStrings = {
            "T | 0 | read book",
            "D | 1 | return book /by 2021-08-24 1800",
            "E | 0 | project meeting /from 2021-08-25 1400 /to 2021-08-25 1600"
        };
        try {
            Ui ui = new TextUi();
            TaskList taskList = new TaskList(ui, taskStrings);
            assertEquals(3, taskList.getNumberOfTasks());
            assertEquals("T | 0 | read book", taskList.getTasks().get(0).getTaskString());
            assertEquals("D | 1 | return book /by 2021-08-24 1800", taskList.getTasks().get(1).getTaskString());
            assertEquals(
                "E | 0 | project meeting /from 2021-08-25 1400 /to 2021-08-25 1600",
                taskList.getTasks().get(2).getTaskString()
            );
        } catch (RoverException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void checkListingOfTasks() {
        TaskList taskList = new TaskList();
        Ui ui = new TextUi();
        try {
            taskList.addTask(new Todo("read book"), ui);
            taskList.addTask(new Deadline("return book /by 2021-08-24 1800", ui), ui);
            taskList.addTask(new Event("project meeting /from 2021-08-25 1400 /to 2021-08-25 1600", ui), ui);
        } catch (RoverException | DateTimeParseException e) {
            fail("Exception should not be thrown");
        }

        // Capture the output of the UI
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        taskList.showTasks(ui, (task, ignore) -> true, "in your list");

        String expectedOutput = """
            --------------------------------------------
            Here are the tasks in your list:
            1. [T][ ] read book
            2. [D][ ] return book (by: Tuesday, 24 August, 2021 6:00 pm)
            3. [E][ ] project meeting (from Wednesday, 25 August, 2021 2:00 pm to Wednesday, 25 August, 2021 4:00 pm)
            --------------------------------------------
            """.replace("\n", System.lineSeparator());
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void checkAddingOfTasks() {
        TaskList taskList = new TaskList();
        Ui ui = new TextUi();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            taskList.addTask(new Todo("read book"), ui);
        } catch (RoverException | DateTimeParseException e) {
            fail("Exception should not be thrown");
        }

        String expectedOutput = """
           --------------------------------------------
           Got it. I've added this task:
             [T][ ] read book
           Now you have 1 task in the list.
           --------------------------------------------
            """.replace("\n", System.lineSeparator());
        assertEquals(expectedOutput, outContent.toString());
        outContent.reset();

        try {
            taskList.addTask(new Deadline("return book /by 2030-08-24 1800", ui), ui);
        } catch (RoverException | DateTimeParseException e) {
            fail("Exception should not be thrown");
        }

        String expectedOutput2 = """
            --------------------------------------------
            Got it. I've added this task:
              [D][ ] return book (by: Saturday, 24 August, 2030 6:00 pm)
            Now you have 2 tasks in the list.
            --------------------------------------------
            """.replace("\n", System.lineSeparator());
        assertEquals(expectedOutput2, outContent.toString());
        outContent.reset();

        try {
            taskList.addTask(new Event("project meeting /from 2030-08-25 1400 /to 2030-08-25 1600"), ui);
        } catch (RoverException | DateTimeParseException e) {
            fail("Exception should not be thrown");
        }

        String expectedOutput3 = """
            --------------------------------------------
            Got it. I've added this task:
              [E][ ] project meeting (from Sunday, 25 August, 2030 2:00 pm to Sunday, 25 August, 2030 4:00 pm)
            Now you have 3 tasks in the list.
            --------------------------------------------
            """.replace("\n", System.lineSeparator());
        assertEquals(expectedOutput3, outContent.toString());
        outContent.reset();

        try {
            taskList.addTask(new Todo("read book"), ui);
        } catch (RoverException | DateTimeParseException e) {
            assertEquals("This task already exists in the list.", e.getMessage());
        }
    }
}
