import java.util.Scanner;

public class Rover {

    private static final String divider = "--------------------------------------------";
    private static final Task[] tasks = new Task[100];
    private static int taskCount = 0;

    private static void printWelcome() {
        String logo = "██████╗  ██████╗ ██╗   ██╗███████╗██████╗ \n" +
                "██╔══██╗██╔═══██╗██║   ██║██╔════╝██╔══██╗\n" +
                "██████╔╝██║   ██║██║   ██║█████╗  ██████╔╝\n" +
                "██╔══██╗██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗\n" +
                "██║  ██║╚██████╔╝ ╚████╔╝ ███████╗██║  ██║\n" +
                "╚═╝  ╚═╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝\n";

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
        tasks[taskCount] = new Task(description);
        taskCount++;
        System.out.println(divider);
        System.out.println("added: " + description);
        System.out.println(divider);
    }

    public static void main(String[] args) {
        printWelcome();
        while (true) {
            Scanner sc = new Scanner(System.in);
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
