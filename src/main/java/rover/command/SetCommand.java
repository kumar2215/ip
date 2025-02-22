package rover.command;

import java.nio.file.Paths;

import rover.exceptions.RoverException;
import rover.parser.Parser;
import rover.preferences.PreferenceOption;
import rover.task.TaskList;
import rover.ui.Gui;
import rover.ui.TextUi;
import rover.ui.Ui;

/**
 * Represents a command to set the user preferences.
 */
public class SetCommand extends Command {

    /**
     * Creates a new SetCommand with the given arguments.
     *
     * @param args The arguments for the command.
     */
    public SetCommand(String args) {
        super(args.substring(3).trim());
    }

    /**
     * Executes the set command to set the given user preferences.
     *
     * @param taskList The task list to be operated on.
     * @param parser The parser to parse the user input.
     * @param ui The user interface to interact with the user.
     */
    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        String[] preferences = args.split(" ");
        if (preferences.length == 2) {
            preferences[0] = preferences[0].trim();
            preferences[1] = preferences[1].trim();

            PreferenceOption preferenceOption;
            try {
                preferenceOption = parser.parsePreferenceOption(preferences[0]);
            } catch (RoverException e) {
                ui.displayError("Invalid preference! Preference options can only be: name, userImage or roverImage "
                    + "and the value should be a valid file path if setting image.");
                return;
            }

            String optionName = String.join(" ", preferenceOption.name().split("_")).toLowerCase();
            optionName = optionName.substring(0, 1).toUpperCase() + optionName.substring(1);
            if (ui instanceof TextUi && optionName.contains("image")) {
                ui.displayError(String.format("%s cannot be set in text mode!", optionName));
            }

            boolean isSuccessful = setOption(ui, preferenceOption, preferences[1]);
            if (isSuccessful) {
                ui.showMessage(String.format("%s successfully set to %s", optionName, preferences[1]));
            }
        } else {
            ui.displayError("Invalid input! Please enter the command in the format: set [preference] [value]");
        }
    }

    private boolean setOption(Ui ui, PreferenceOption preferenceOption, String value) {
        return switch (preferenceOption) {
        case NAME -> ui.setUsername(value);
        case USER_IMAGE -> ((Gui) ui).setUserImage(Paths.get(value));
        case ROVER_IMAGE -> ((Gui) ui).setRoverImage(Paths.get(value));
        };
    }
}
