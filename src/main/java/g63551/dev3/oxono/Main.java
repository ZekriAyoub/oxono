package g63551.dev3.oxono;

import g63551.dev3.oxono.controller.Controller;
import g63551.dev3.oxono.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class is the entry point for the OXONO game application.
 * It extends the JavaFX Application class and launches the game user interface.
 */
public class Main extends Application {

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the primary stage and sets up the main view and controller for the game.
     * This method is automatically called by the JavaFX framework when the application starts.
     *
     * @param primaryStage The primary stage for this application, onto which scenes can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create the main view for the game
        MainView mainView = new MainView(primaryStage);

        // Create the controller for managing the game logic and interaction with the view
        Controller controller = new Controller(mainView);

        // Show the main game window (primary stage)
        primaryStage.show();
    }
}
