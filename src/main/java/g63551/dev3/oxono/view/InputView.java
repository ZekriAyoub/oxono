package g63551.dev3.oxono.view;

import g63551.dev3.oxono.controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * The InputView class represents the initial screen of the game, allowing the user
 * to choose the game board size and the level of the automatic player.
 */
public class InputView extends VBox {
    private final Label welcome;
    private final Label sizeTitle;
    private final Label levelTitle;
    private final Button buttonGo;
    private final ComboBox<Integer> dropdownMenuSize;
    private final ComboBox<Integer> levelMenu;
    private final Controller controller;

    /**
     * Constructs an InputView with the given controller.
     *
     * @param controller The controller instance that will handle input actions.
     */
    public InputView(Controller controller) {
        this.controller = controller;

        welcome = new Label("Welcome to OXONO");
        sizeTitle = new Label("Game board size: ");
        levelTitle = new Label("Automatic player's level: ");
        buttonGo = new Button("Go");

        dropdownMenuSize = new ComboBox<>();
        dropdownMenuSize.getItems().addAll(4, 6, 8);
        dropdownMenuSize.setValue(6);

        levelMenu = new ComboBox<>();
        levelMenu.getItems().addAll(1);
        levelMenu.setValue(1);

        setupLayout();
        setupActions();
    }

    /**
     * Sets up the layout of the input view, including the positioning and styling of
     * the various UI components such as labels, combo boxes, and buttons.
     */
    private void setupLayout() {
        HBox buttonContainer = new HBox(buttonGo);
        buttonContainer.setAlignment(Pos.CENTER);

        this.getChildren().addAll(welcome, sizeTitle, dropdownMenuSize, levelTitle, levelMenu, buttonContainer);
        this.setSpacing(22);
        this.setPadding(new Insets(30));

        welcome.setFont(Font.font("Arial", 25));
        welcome.setStyle("-fx-text-fill: purple;");
        sizeTitle.setFont(Font.font("Arial", 20));
        levelTitle.setFont(Font.font("Arial", 20));
        buttonGo.setStyle("-fx-background-color: #800080; -fx-text-fill: white; -fx-font-size: 19px; -fx-min-width: 50px; -fx-min-height: 30px;");
        dropdownMenuSize.setStyle("-fx-background-color: #87CEEB;");
        levelMenu.setStyle("-fx-background-color: #87CEEB;");
    }

    /**
     * Sets up the action handlers for the buttons and other interactive elements.
     * Specifically, it sets up the action for the "Go" button, which initializes the game
     * with the selected board size and automatic player level.
     */
    private void setupActions() {
        buttonGo.setOnAction(e -> {
            Integer selectedSize = dropdownMenuSize.getValue();
            Integer selectedLevel = levelMenu.getValue();
            controller.initializeInputHandlers(selectedSize, selectedLevel);
        });
    }
}
