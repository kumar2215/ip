package rover.ui;

import java.util.Scanner;

public class Ui {

    private static final String divider = "--------------------------------------------";
    private final Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public String readCommand() {
        return sc.nextLine();
    }

    public void showLine() {
        System.out.println(divider);
    }

    public void showWelcome() {
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
        System.out.println("I am your personal task manager.");
        System.out.println("What can I do for you?");
        System.out.println(divider);
    }

    public void sayBye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(divider);
    }

    public void showHelpMessage() {
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
                            Show tasks before a certain date and/or time by typing 'show before (date) (time)'.
                            Show tasks after a certain date and/or time by typing 'show after (date) (time)'.
                            Exit the program by typing 'bye'.
                        """;
        System.out.print(briefHelp);
        System.out.println(divider);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String message) {
        System.out.println("Oops! Error: " + message);
    }

}
