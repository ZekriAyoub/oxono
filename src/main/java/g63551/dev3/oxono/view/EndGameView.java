package g63551.dev3.oxono.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The EndGameView class represents the view shown when the game ends.
 * It displays a message to the player
 */
public class EndGameView extends VBox {
    private final String message;

    /**
     * Constructs an EndGameView with a given message.
     *
     * @param message The message to display when the game ends.
     */
    public EndGameView(String message) {
        this.message = message;
        initUI();
    }

    /**
     * Initializes the user interface components for the end game view.
     * It sets up the message label and the close button.
     */
    private void initUI() {
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10;");

        this.getChildren().add(messageLabel);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-padding: 20;");
    }

    /**
     * Displays the end game window.
     * This method creates a new stage, sets the scene, and shows the window.
     */
    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Game Over");
        stage.setScene(new Scene(this));
        stage.setWidth(450);
        stage.setHeight(150);
        stage.setResizable(false);
        stage.show();
    }
}
