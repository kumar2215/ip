package rover.ui;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import rover.main.Rover;

/**
 * Controller for the main GUI.
 */
public final class Gui extends AnchorPane implements Ui {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Rover rover;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image roverImage = new Image(this.getClass().getResourceAsStream("/images/Rover.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Rover instance */
    public void setRover(Rover r) {
        rover = r;
        rover.setUi(this);
    }

    /**
     * Shows the response from the user in the dialog container.
     */
    private void showUserResponse() {
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(userInput.getText(), userImage)
        );
        userInput.clear();
    }

    /**
     * Shows the response from Rover in the dialog container.
     * @param response The response from Rover.
     */
    private void showRoverResponse(String response) {
        dialogContainer.getChildren().addAll(
            DialogBox.getRoverDialog(response, roverImage)
        );
    }

    /**
     * Reads the user's input.
     *
     * @return The user's input.
     */
    @Override
    public String readCommand() {
        return userInput.getText();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Rover's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = readCommand();
        showUserResponse();
        boolean isExit = rover.handleResponse(input);
        if (isExit) {
            rover.endSession();
        }
    }

    /**
     * Displays the welcome message when the program starts.
     */
    @Override
    public void showWelcome() {
        String response = """
            Hello! I'm Rover
            I am your personal task manager.
            What can I do for you?
            """;
        showRoverResponse(response);
    }

    /**
     * Displays the goodbye message when the program ends.
     */
    @Override
    public void sayBye() {
        String response = "Bye. Hope to see you again soon!";
        showRoverResponse(response);
    }

    /**
     * Displays the help message when the user types an invalid command.
     */
    @Override
    public void showHelpMessage() {
        String briefHelp = """
                        I'm sorry, but I don't know what that means.
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
        showRoverResponse(briefHelp);
    }

    /**
     * Displays the message to the user.
     *
     * @param message The message to be displayed.
     */
    @Override
    public void showMessage(String message) {
        showRoverResponse(message);
    }

    /**
     * Displays the message to the user without a line separator.
     *
     * @param message The message to be displayed.
     */
    @Override
    public void showMessageWithoutLineSeparator(String message) {
        showRoverResponse(message);
    }

    /**
     * Displays the error message to the user.
     *
     * @param message The error message to be displayed.
     */
    @Override
    public void displayError(String message) {
        showRoverResponse("Oops! Error: " + message);
    }
}
