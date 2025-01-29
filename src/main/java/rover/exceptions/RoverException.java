package rover.exceptions;

/**
 * RoverException is a custom exception class that extends the Exception class.
 * It is used to throw exceptions when the rover chatBot is not able to function properly.
 */
public class RoverException extends Exception {
    public RoverException(String message) {
        super(message);
    }

    /**
     * Overridden toString method to include the class name in the exception message.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "RoverException: " + this.getMessage();
    }
}
