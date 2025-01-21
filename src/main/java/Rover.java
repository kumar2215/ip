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

        while (true) {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                break;
            }
            System.out.println(divider);
            System.out.println("You entered: " + input);
            System.out.println(divider);
        }

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(divider);
    }
}
