package rover.ui;

/**
 * Ui interface deals with interactions with the user.
 */
public interface Ui {

    String readCommand();

    void showWelcome();

    void sayBye();

    void showHelpMessage();

    void showMessage(String message);

    void showMessageWithoutLineSeparator(String message);

    void displayError(String message);
}
