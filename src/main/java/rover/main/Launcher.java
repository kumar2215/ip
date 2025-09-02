package rover.main;
import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        assert args != null : "Arguments array should not be null";
        Application.launch(Main.class, args);
    }
}
