import java.util.Scanner;

public class Rover {
    public static void main(String[] args) {

        String divider = "--------------------------------------------";

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

        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println(divider);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(divider);
                continue;
            } else if (input.toLowerCase().startsWith("mark")) {
                String[] parts = input.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                tasks[index].setDone();
                System.out.println(divider);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(tasks[index]);
                System.out.println(divider);
                continue;
            } else if (input.toLowerCase().startsWith("unmark")) {
                String[] parts = input.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                tasks[index].setUndone();
                System.out.println(divider);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(tasks[index]);
                System.out.println(divider);
                continue;
            }
            tasks[taskCount] = new Task(input);
            taskCount++;
            System.out.println(divider);
            System.out.println("added: " + input);
            System.out.println(divider);
        }

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(divider);
    }
}
