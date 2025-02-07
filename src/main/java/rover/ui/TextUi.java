package rover.ui;

import java.util.Scanner;

/**
 * Ui class deals with interactions with the user.
 * It displays messages to the user and reads input from the user.
 */
public final class TextUi implements Ui {

    private static final String divider = "--------------------------------------------";
    private final Scanner sc;

    /**
     * Constructor for Ui class.
     * It initializes the scanner to read input from the user.
     */
    public TextUi() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Reads the next line of input from the user.
     *
     * @return The next line of input from the user.
     */
    @Override
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Displays a line to separate different messages.
     */
    private void showLine() {
        System.out.println(divider);
    }

    /**
     * Displays the welcome message when the program starts.
     */
    @Override
    public void showWelcome() {
        String logo = """
                ___
                |  _`\\
                | (_) )   _    _   _    __   _ __
                | ,  /  /'_`\\ ( ) ( ) /'__`\\( '__)
                | |\\ \\ ( (_) )| \\_/ |(  ___/| |
                (_) (_)`\\___/'`\\___/'`\\____)(_)
                """;

        showLine();
        System.out.println("Hello! I'm Rover");
        System.out.println(logo);
        System.out.println("I am your personal task manager.");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Displays the goodbye message when the program ends.
     */
    @Override
    public void sayBye() {
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Displays the help message when the user types an invalid command.
     */
    @Override
    public void showHelpMessage() {
        showLine();
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
        showLine();
    }

    /**
     * Displays the message to the user.
     *
     * @param message The message to be displayed.
     */
    @Override
    public void showMessage(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    /**
     * Displays the message to the user without the 2nd line separator.
     *
     * @param message The message to be displayed.
     */
    @Override
    public void showMessageWithoutLineSeparator(String message) {
        showLine();
        System.out.println(message);
    }

    /**
     * Displays the error message to the user.
     *
     * @param message The error message to be displayed.
     */
    @Override
    public void displayError(String message) {
        showLine();
        System.out.println("Oops! Error: " + message);
        showLine();
    }
}
