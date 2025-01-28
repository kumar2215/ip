package rover.exceptions;

public class RoverException extends Exception {
    public RoverException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "RoverException: " + this.getMessage();
    }
}
