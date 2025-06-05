package g63551.dev3.oxono.view;

import g63551.dev3.oxono.controller.Controller;
import g63551.dev3.oxono.model.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

/**
 * The ButtonView class is responsible for displaying the game control buttons
 * (Undo, Redo, Surrender, Quit) in the user interface. It handles the button
 * layout, creation, and associating each button with its corresponding action
 * via the controller. The view is updated based on the game's state to enable
 * or disable buttons appropriately.
 */
public class ButtonView extends HBox {
    private Button undo;
    private Button redo;
    private Button surrender;
    private Button quit;
    private Controller controller;
    private Game game;

    /**
     * Constructs a ButtonView object and initializes the buttons.
     * The buttons are added to the layout with appropriate styles and actions.
     *
     * @param game The game object used to update the button states.
     * @param controller The controller object that handles the button actions.
     */
    public ButtonView(Game game, Controller controller) {
        this.controller = controller;
        this.game = game;

        undo = createButtonWithImage("/images/undo.png");
        redo = createButtonWithImage("/images/redo.png");
        surrender = createStandardButton("Abandonner");
        quit = createStandardButton("Quitter");

        this.setSpacing(15);
        this.setPadding(new Insets(30));
        this.getChildren().addAll(undo, redo, surrender, quit);
        this.setAlignment(Pos.CENTER);

        undo.setDisable(true);
        redo.setDisable(true);

        setButtonActions();
    }

    /**
     * Creates a button with an image to be used for Undo and Redo actions.
     *
     * @param imagePath The path to the image file used for the button graphic.
     * @return The created button with the image.
     */
    private Button createButtonWithImage(String imagePath) {
        Button button = new Button();
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: #800080;");
        return button;
    }

    /**
     * Creates a standard button with a text label, used for the Surrender and Quit buttons.
     *
     * @param text The text to display on the button.
     * @return The created button with the text label.
     */
    private Button createStandardButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #800080; -fx-text-fill: white; -fx-font-size: 17px; -fx-min-width: 150px; -fx-min-height: 40px;");
        return button;
    }

    /**
     * Sets the actions to be triggered when each button is clicked.
     * Each button is linked to a specific method in the controller to perform the required action.
     */
    private void setButtonActions() {
        quit.setOnAction(e -> controller.initializeButtonQuitHandlers());
        undo.setOnAction(e -> controller.initializeButtonUndoHandlers());
        redo.setOnAction(e -> controller.initializeButtonRedoHandlers());
        surrender.setOnAction(e -> controller.surrenderButton());
    }

    /**
     * Updates the state of the buttons based on the current game state.
     * This method enables or disables the Undo and Redo buttons depending on
     * whether the game can perform these actions.
     */
    public void update() {
        undo.setDisable(!game.canUndo());
        redo.setDisable(!game.canRedo());
    }
}
