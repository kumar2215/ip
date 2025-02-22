package rover.ui;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.databind.JsonNode;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import rover.main.Rover;
import rover.preferences.UserPreferences;

/**
 * Controller for the main GUI.
 */
public final class Gui extends AnchorPane implements Ui {

    private static final Image DEFAULT_USER_IMAGE = new Image(Gui.class.getResourceAsStream("/images/User.png"));
    private static final Image DEFAULT_ROVER_IMAGE = new Image(Gui.class.getResourceAsStream("/images/Rover.png"));

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Rover rover;
    private Image userImage;
    private Image roverImage;
    private String username;
    private UserPreferences userPreferences;

    /**
     * Initializes the GUI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Gets the UserPreferences instance.
     *
     * @return The UserPreferences instance.
     */
    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    /**
     * Injects the UserPreferences instance.
     *
     * @param userPreferences The UserPreferences instance.
     */
    public void setUserPreferences(UserPreferences userPreferences) {
        assert userPreferences != null : "User preferences should not be null.";
        this.userPreferences = userPreferences;

        JsonNode name = userPreferences.getJsonNode().get("name");
        this.username = name.asText().isEmpty() ? "" : " " + name.asText();
        setUserImage(Path.of(userPreferences.getJsonNode().get("userImage").asText()));
        setRoverImage(Path.of(userPreferences.getJsonNode().get("roverImage").asText()));
    }

    /**
     * Injects the Rover instance
     */
    public void setRover(Rover r) {
        rover = r;
        rover.setUi(this);
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username of the user.
     */
    @Override
    public boolean setUsername(String username) {
        assert username != null : "Username should not be null.";
        assert !username.isBlank() : "Username should not be blank.";
        this.username = " " + username;
        return userPreferences.setName(username);
    }

    /**
     * Sets the image of the user.
     *
     * @param userImagePath The path to the image of the user.
     */
    public boolean setUserImage(Path userImagePath) {
        if (userImagePath.toString().equals("default")) {
            userImage = DEFAULT_USER_IMAGE;
            return false;
        } else if (!Files.exists(userImagePath)) {
            displayError(String.format("File %s not found at the specified path. "
                + "Using default user image.", userImagePath));
            userImage = DEFAULT_USER_IMAGE;
            return false;
        }
        userImage = new Image(userImagePath.toUri().toString());
        return userPreferences.setUserImage(userImagePath.toString());
    }

    /**
     * Sets the image of the rover.
     *
     * @param roverImagePath The path to the image of the rover.
     */
    public boolean setRoverImage(Path roverImagePath) {
        if (roverImagePath.toString().equals("default")) {
            roverImage = DEFAULT_ROVER_IMAGE;
            return false;
        } else if (!Files.exists(roverImagePath)) {
            displayError(String.format("File %s not found at the specified path. "
                + "Using default rover image.", roverImagePath));
            roverImage = DEFAULT_ROVER_IMAGE;
            return false;
        }
        roverImage = new Image(roverImagePath.toUri().toString());
        return userPreferences.setRoverImage(roverImagePath.toString());
    }

    /**
     * Shows the response from the user in the dialog container.
     */
    private void showUserResponse() {
        assert dialogContainer != null : "Dialog container should not be null.";
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
        assert dialogContainer != null : "Dialog container should not be null.";
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
        assert userInput != null : "User input should not be null.";
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
        assert rover != null : "Rover should not be null.";
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
        String response = String.format("""
            Hello%s! I'm Rover
            I am your personal task manager.
            What can I do for you?
            """, username);
        showRoverResponse(response);
    }

    /**
     * Displays the goodbye message when the program ends.
     * Part of the code was taken from a StackOverflow answer given by Jason C.
     * source: <a href="https://stackoverflow.com/questions/21974415/how-to-close-this-javafx
     * -application-after-showing-a-message-in-a-text-area-elem">...</a>
     * Thanks to Jason C. for the original code.
     * Thanks to @zuoshihua for pointing out the issue with the original code.
     * Thanks to @ChinZJ for the fix to use new Timer(true) instead.
     */
    @Override
    public void sayBye() {
        String response = String.format("Bye%s. Hope to see you again soon!", username);
        showRoverResponse(response);
        new Timer(true).schedule(new TimerTask() {
            public void run() {
                Platform.exit();
            }
        }, 2000);
    }

    /**
     * Displays the help message when the user types an invalid command.
     */
    @Override
    public void showHelpMessage() {
        String briefHelp = String.format("""
                        I'm sorry%s, but I don't know what that means.
                        The following commands are supported:
                            You can add a task by typing:
                            - todo (description)
                            - deadline (description) /by (deadline)
                            - event (description) /from (start) /to (end)
                            You can set your preferences by typing 'set (preference) (value)'.
                            To set your name, type 'set name (your name)'.
                            To set your user image, type 'set userImage (path to image)'.
                            To set Rover's image, type 'set roverImage (path to image)'.
                            List the existing tasks by typing 'list'.
                            Mark a task as done by typing 'mark (task number)'.
                            Mark a task as not done by typing 'unmark (task number)'.
                            Delete a task by typing 'delete (task number)'.
                            Show tasks before a certain date and/or time by typing 'show before (date) (time)'.
                            Show tasks after a certain date and/or time by typing 'show after (date) (time)'.
                            Exit the program by typing 'bye'.
                        """, username);
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
        String response = "Oops! Error: " + message;
        dialogContainer.getChildren().addAll(
            DialogBox.getErrorDialog(response, roverImage)
        );
    }
}
