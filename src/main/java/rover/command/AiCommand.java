package rover.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import rover.ai.AiHelper;
import rover.parser.Parser;
import rover.task.TaskList;
import rover.ui.Ui;

/**
 * Represents a command that queries the AI for a response.
 */
public class AiCommand extends Command {

    /**
     * Constructs an add command with the given arguments.
     *
     * @param args The arguments of the command.
     */
    public AiCommand(String args) {
        super(args.substring(3).trim());
    }

    @Override
    public void execute(TaskList taskList, Parser parser, Ui ui) {
        AiHelper aiHelper = new AiHelper();
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));

        String systemPrompt = "You are an assistant for a CLI task management app. Based on the user's request, "
            + "generate a valid command that the app understands."
            + "Only respond with the command, nothing else.\n\n"
            + "Available commands:\n"
            + "The following commands are supported:\n"
            + "- todo (description)\n"
            + "- deadline (description) /by (deadline)\n"
            + "- event (description) /from (start) /to (end)\n"
            + "Deadlines and events start and end should be in the format 'dd/mm/yy HH:MM' or 'dd/mm/yy' or 'HH:MM', "
            + " and definitely not words like 'tomorrow' or 'today'.\n"
            + "The current date and time is " + currentDateTime + ".\n"
            + "You can set your preferences by typing 'set (preference) (value)'.\n"
            + "To set your name, type 'set name (your name)'.\n"
            + "To set your user image, type 'set userImage (path to image)'.\n"
            + "To set Rover's image, type 'set roverImage (path to image)'.\n"
            + "List the existing tasks by typing 'list'.\n"
            + "Mark a task as done by typing 'mark (task number)'.\n"
            + "Mark a task as not done by typing 'unmark (task number)'.\n"
            + "Delete a task by typing 'delete (task number)'.\n"
            + "Find tasks with a certain keyword by typing 'find (keyword)'.\n"
            + "Show tasks before a certain date and/or time by typing 'show before (date) (time)'.\n"
            + "Show tasks after a certain date and/or time by typing 'show after (date) (time)'.\n"
            + "Exit the program by typing 'bye'.\n";

        String stringCommand = aiHelper.getAiResponse(systemPrompt, args).aiMessage().text();
        Command command = parser.parseCommand(stringCommand);

        if (command instanceof InvalidCommand) {
            ui.displayError("Sorry, I couldn't understand the command.");
        } else {
            command.execute(taskList, parser, ui);
            ui.showMessage("Command executed: " + stringCommand);
        }
    }
}
