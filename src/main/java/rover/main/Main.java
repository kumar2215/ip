package rover.main;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rover.ui.Gui;

/**
 * A GUI for Rover using FXML.
 */
public class Main extends Application {

    private final Rover rover = new Rover();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Rover - Your Personal Task Manager");
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<Gui>getController().setRover(rover);
            stage.show();
            rover.startSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
