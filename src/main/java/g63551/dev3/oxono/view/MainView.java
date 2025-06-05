package g63551.dev3.oxono.view;

import g63551.dev3.oxono.controller.Controller;
import g63551.dev3.oxono.model.Game;
import g63551.dev3.oxono.model.Position;
import g63551.dev3.oxono.model.observer.Observer;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The MainView class is responsible for managing and displaying the main game interface of the OXONO game.
 * It handles the layout and updates the UI based on the game's state.
 */
public class MainView implements Observer {
    private final Stage stage;
    private BoardView boardView;
    private InputView inputView;
    private InfoView infoView;
    private ButtonView buttonView;
    private BorderPane root;
    private Game game;
    private Controller controller;

    /**
     * Constructs a MainView instance for the given stage.
     *
     * @param stage The stage in which the scene will be displayed.
     */
    public MainView(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the initial scene where the user can choose the game settings (e.g., board size and level).
     * Sets up the input view to be displayed initially.
     */
    public void instantiationScene() {
        this.inputView = new InputView(controller);
        this.root = new BorderPane();
        root.setBackground(Background.fill(Color.ALICEBLUE));
        root.setCenter(inputView);
        BorderPane.setAlignment(inputView, Pos.CENTER);

        stage.setScene(new javafx.scene.Scene(root, 800, 750));
        stage.setTitle("OXONO");
    }

    /**
     * Displays the main game view with the game board, game controls, and information panel.
     *
     * @param game The game instance to display.
     */
    public void showMainGameView(Game game) {
        this.game = game;
        game.registerObserver(this);

        this.boardView = new BoardView(game, controller);
        this.infoView = new InfoView(game);
        this.buttonView = new ButtonView(game, controller);

        HBox centeredBoard = new HBox();
        centeredBoard.setAlignment(Pos.CENTER);
        centeredBoard.getChildren().add(boardView);

        root.setTop(centeredBoard);
        root.setCenter(buttonView);
        root.setBottom(infoView);
    }

    /**
     * Displays the winner's information in a pop-up window when the game ends.
     */
    private void displayWinner() {
        String winner = game.displayCurrentPlayer();
        EndGameView winnerView = new EndGameView("The winner is the " + winner + " !");
        winnerView.show();
    }

    /**
     * Displays a draw message in a pop-up window when the game ends in a draw.
     */
    private void displayDraw() {
        EndGameView drawView = new EndGameView("The match ended in a draw !");
        drawView.show();
    }

    /**
     * Updates the view based on changes in the game's state. This includes refreshing the board,
     * updating the information panel, and displaying the winner or draw if the game has ended.
     */
    @Override
    public void update() {
        boardView.initialisationGrid(game.getSize());
        infoView.update();
        buttonView.update();
        if (game.isEnd()) {
            addGrayOverlay();
            displayWinner();
        } else if (game.isDraw()) {
            addGrayOverlay();
            displayDraw();
        }
    }

    /**
     * Adds a gray overlay to the screen to indicate the end of the game.
     */
    public void addGrayOverlay() {
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        overlay.setPrefSize(stage.getScene().getWidth(), stage.getScene().getHeight());

        StackPane stackRoot = new StackPane(root);
        stage.getScene().setRoot(stackRoot);

        StackPane rootStack = (StackPane) stage.getScene().getRoot();
        rootStack.getChildren().add(overlay);
    }

    /**
     * Sets the controller for the MainView to handle user interactions and game logic.
     *
     * @param controller The controller that will manage user actions.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Displays an error message in the information panel.
     */
    public void displayError() {
        infoView.displayError();
    }

    /**
     * Highlights a position on the game board with a totem effect.
     *
     * @param pos The position to highlight.
     * @param b   Whether to highlight or unhighlight the position.
     */
    public void highLight(Position pos, boolean b) {
        boardView.highlightSelectedTotem(pos, b);
    }

    /**
     * Applies a glowing effect to a position on the game board.
     *
     * @param pos   The position to apply the glow effect.
     * @param apply Whether to apply or remove the glow effect.
     */
    public void applyGlowEffect(Position pos, boolean apply) {
        boardView.applyGlowEffect(pos, apply);
    }

    /**
     * Displays a surrender message in a pop-up window when a player forfeits the game.
     */
    private void displaySurrender() {
        EndGameView surrenderView = new EndGameView("You have forfeited !");
        surrenderView.show();
    }

    /**
     * Handles the surrender action, displaying a surrender message and overlaying the game view.
     */
    public void surrender() {
        addGrayOverlay();
        displaySurrender();
    }
}
