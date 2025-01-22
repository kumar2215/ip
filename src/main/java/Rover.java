import java.util.Scanner;

public class Rover {

    private static final String divider = "--------------------------------------------";
    private static final Task[] tasks = new Task[100];
    private static int taskCount = 0;

    private static void printWelcome() {
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

    private static void printGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(divider);
    }

    private static void listTasks() {
        System.out.println(divider);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
        System.out.println(divider);
    }

    private static void markTaskAsDone(int index) {
        tasks[index].setDone();
        System.out.println(divider);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(tasks[index]);
        System.out.println(divider);
    }

    private static void markTaskAsUndone(int index) {
        tasks[index].setUndone();
        System.out.println(divider);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(tasks[index]);
        System.out.println(divider);
    }

    private static void addTask(String description) {
        Task newTask;
        description = description.trim();
        if (description.toLowerCase().startsWith("deadline")) {
            String[] parts = description.split(" /by ");
            newTask = new Deadline(parts[0].substring(9), parts[1]);
        } else if (description.toLowerCase().startsWith("event")) {
            String[] parts = description.split(" /from ");
            String[] parts2 = parts[1].split(" /to ");
            newTask = new Event(parts[0].substring(6), parts2[0], parts2[1]);
        } else {
            newTask = new Todo(description.substring(5));
        }
        tasks[taskCount] = newTask;
        taskCount++;
        System.out.println(divider);
        System.out.println("Got it. I've added this task:");
        System.out.println(tasks[taskCount - 1]);
        System.out.println("Now you have " + taskCount + " task" + (taskCount > 1 ? "s" : "") +  " in the list.");
        System.out.println(divider);
    }

    public static void main(String[] args) {
        printWelcome();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("list")) {
                listTasks();
            } else if (input.toLowerCase().startsWith("mark")) {
                String[] parts = input.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                markTaskAsDone(index);
            } else if (input.toLowerCase().startsWith("unmark")) {
                String[] parts = input.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                markTaskAsUndone(index);
            } else {
                addTask(input);
            }
        }
        printGoodbye();
    }
}
