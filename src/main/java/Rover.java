import java.util.Scanner;
import java.util.ArrayList;

public class Rover {

    private static final String divider = "--------------------------------------------";
    private final ArrayList<Task> tasks = new ArrayList<>();
    private int taskCount = 0;

    private void printWelcome() {
        String logo = """
                ___
                |  _`\\
                | (_) )   _    _   _    __   _ __
                | ,  /  /'_`\\ ( ) ( ) /'__`\\( '__)
                | |\\ \\ ( (_) )| \\_/ |(  ___/| |
                (_) (_)`\\___/'`\\___/'`\\____)(_)
                """;

        System.out.println(divider);
        System.out.println("Hello! I'm Rover");
        System.out.println(logo);
        System.out.println("What can I do for you?");
        System.out.println(divider);
    }

    private void printGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(divider);
    }

    private void listTasks() {
        System.out.println(divider);
        if (taskCount == 0) {
            System.out.println("There are no tasks in your list.");
            System.out.println(divider);
            return;
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println(divider);
    }

    private int getTaskNumber(String taskNumber, TaskAction taskAction) throws RoverException {
        String action = taskAction == TaskAction.MARK_DONE
                ? "marked as done"
                : taskAction == TaskAction.MARK_UNDONE
                ? "marked as not done"
                : "deleted";
        if (taskNumber.isEmpty()) {
            throw new RoverException("Please specify the task number to be " + action  + ".");
        }
        int index;
        try {
            index = Integer.parseInt(taskNumber) - 1;
        } catch (NumberFormatException e) {
            throw new RoverException("Please specify a valid task number to be " + action + ".");
        }
        if (index < 0 || index >= taskCount) {
            throw new RoverException("Please specify a valid task number to be " + action + ".\n" +
                    "You only have " + taskCount + " tasks in total.");
        }
        return index;
    }

    private void markTaskAsDone(String taskNumber) {
        int index;
        try {
            index = getTaskNumber(taskNumber, TaskAction.MARK_DONE);
        } catch (RoverException e) {
            System.out.println(divider);
            System.out.println(e.getMessage());
            System.out.println(divider);
            return;
        }
        tasks.get(index).setDone();
        System.out.println(divider);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(tasks.get(index));
        System.out.println(divider);
    }

    private void markTaskAsUndone(String taskNumber) {
        int index;
        try {
            index = getTaskNumber(taskNumber, TaskAction.MARK_UNDONE);
        } catch (RoverException e) {
            System.out.println(divider);
            System.out.println(e.getMessage());
            System.out.println(divider);
            return;
        }
        tasks.get(index).setUndone();
        System.out.println(divider);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(tasks.get(index));
        System.out.println(divider);
    }

    private void deleteTask(String taskNumber) {
        int index;
        try {
            index = getTaskNumber(taskNumber, TaskAction.DELETE);
        } catch (RoverException e) {
            System.out.println(divider);
            System.out.println(e.getMessage());
            System.out.println(divider);
            return;
        }
        Task task = tasks.remove(index);
        taskCount--;
        System.out.println(divider);
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " task" + (taskCount > 1 ? "s" : "") +  " in the list.");
        System.out.println(divider);
    }

    private Task getTaskFromDescription(String description) throws RoverException {
        Task newTask;
        description = description.trim();
        if (description.toLowerCase().startsWith("deadline")) {
            newTask = new Deadline(description.substring(8).trim());
        } else if (description.toLowerCase().startsWith("event")) {
            newTask = new Event(description.substring(5).trim());
        } else if (description.toLowerCase().startsWith("todo")) {
            newTask = new Todo(description.substring(4).trim());
        } else {
            throw new RoverException("Not a valid task type.");
        }
        return newTask;
    }

    private void addTask(String description) {
        Task newTask;
        try {
            newTask = getTaskFromDescription(description);
        } catch (RoverException e) {
            if (e.getMessage().equals("Not a valid task type.")) {
                System.out.println(divider);
                System.out.println("I'm sorry, but I don't know what that means.");
                String briefHelp = """
                        The following commands are supported:
                            You can add a task by typing:
                            - todo (description)
                            - deadline (description) /by (deadline)
                            - event (description) /from (start) /to (end)
                            List the existing tasks by typing 'list'.
                            Mark a task as done by typing 'mark (task number)'.
                            Mark a task as not done by typing 'unmark (task number)'.
                            Delete a task by typing 'delete (task number)'.
                            Exit the program by typing 'bye'.
                        """;
                System.out.print(briefHelp);
                System.out.println(divider);
                return;
            } else {
                System.out.println(divider);
                System.out.println(e.getMessage());
                System.out.println(divider);
                return;
            }
        }
        if (tasks.contains(newTask)) {
            System.out.println(divider);
            System.out.println("This task already exists in your list.");
            System.out.println(divider);
            return;
        }
        tasks.add(newTask);
        taskCount++;
        System.out.println(divider);
        System.out.println("Got it. I've added this task:");
        System.out.println(tasks.get(taskCount - 1));
        System.out.println("Now you have " + taskCount + " task" + (taskCount > 1 ? "s" : "") +  " in the list.");
        System.out.println(divider);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Rover rover = new Rover();
        rover.printWelcome();
        while (true) {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("list")) {
                rover.listTasks();
            } else if (input.toLowerCase().startsWith("mark")) {
                rover.markTaskAsDone(input.substring(4).trim());
            } else if (input.toLowerCase().startsWith("unmark")) {
                rover.markTaskAsUndone(input.substring(6).trim());
            } else if (input.toLowerCase().startsWith("delete")) {
                rover.deleteTask(input.substring(6).trim());
            } else {
                rover.addTask(input);
            }
        }
        rover.printGoodbye();
    }
}
