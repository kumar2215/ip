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

        String[] tasks = new String[100];
        int taskCount = 0;

        while (true) {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println(divider);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(divider);
                continue;
            }
            tasks[taskCount] = input;
            taskCount++;
            System.out.println(divider);
            System.out.println("added: " + input);
            System.out.println(divider);
        }

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(divider);
    }
}
