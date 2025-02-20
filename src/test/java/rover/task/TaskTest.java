package rover.task;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import rover.exceptions.RoverException;

public class TaskTest {


    @Test
    public void checkIfTaskIsAbstract() {
        assertThrows(InstantiationException.class, () -> {
            Class<?> clazz = Task.class;
            clazz.getDeclaredConstructor(String.class).newInstance("test");
        });
    }

    @Test
    public void checkForTaskInEquality() {
        try {
            Task task1 = new Todo("read book");
            Task task2 = new Todo(" read book");
            Task task3 = new Todo("read book ");
            assertNotEquals(task1, task2);
            assertNotEquals(task1, task3);
            assertNotEquals(task2, task3);
        } catch (RoverException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkForTaskEquality() {
        try {
            Task task1 = new Todo("read book");
            Task task2 = new Todo("read book");
            Task task3 = new Deadline("read book /by 01/01/2021 1800");
            Task task4 = new Deadline("read book /by 01/02/2021 1800");
            Task task5 = new Event("read book /from 01/01/2021 1800 /to 01/01/2021 1900");
            Task task6 = new Event("read book /from 01/01/2022 1800 /to 01/01/2022 1900");
            assertEquals(task1, task2);
            assertNotEquals(task1, task3);
            assertNotEquals(task1, task5);
            assertNotEquals(task3, task4);
            assertNotEquals(task3, task5);
            assertNotEquals(task5, task6);
        } catch (RoverException e) {
            System.out.println(e.getMessage());
        }
    }

}
