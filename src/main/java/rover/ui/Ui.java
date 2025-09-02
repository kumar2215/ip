package rover.ui;

import rover.preferences.UserPreferences;

/**
 * Ui interface deals with interactions with the user.
 */
public interface Ui {

    UserPreferences getUserPreferences();

    void setUserPreferences(UserPreferences userPreferences);

    String getUsername();

    boolean setUsername(String username);

    String readCommand();

    void showWelcome();

    void sayBye();

    void showHelpMessage();

    void showMessage(String message);

    void showMessageWithoutLineSeparator(String message);

    void displayError(String message);
}
