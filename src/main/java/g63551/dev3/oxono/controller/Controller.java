package g63551.dev3.oxono.controller;

import g63551.dev3.oxono.model.*;
import g63551.dev3.oxono.view.MainView;
import javafx.application.Platform;

import java.util.List;

/**
 * The Controller class handles user input and manages interactions between the
 * view and the model in the game.
 * It manages the game state transitions, user actions (such as moves and insertions),
 * and communicates with the view to update the game interface.
 */
public class Controller {
    private final MainView mainView;
    private Game game;
    private Totem currentTotem;
    private Position selectedTotemPosition;

    /**
     * Constructs a Controller object.
     * Initializes the controller and sets the view.
     *
     * @param mainView The main view object for the game.
     */
    public Controller(MainView mainView) {
        this.currentTotem = null;
        this.mainView = mainView;
        mainView.setController(this);
        mainView.instantiationScene();
    }

    /**
     * Initializes input handlers for the game based on the selected size and level.
     *
     * @param selectedSize The size of the board (e.g., 8x8).
     * @param selectedLevel The level of difficulty for the game.
     */
    public void initializeInputHandlers(int selectedSize, int selectedLevel) {
        this.game = new Game(selectedSize, selectedLevel);
        mainView.showMainGameView(game);
    }

    /**
     * Handles a user click on a given position in the game board.
     * Depending on the game state, it either processes a move or an insertion.
     *
     * @param pos The position where the user clicked.
     */
    public void click(Position pos) {
        if (game.getGameState() == GameState.MOVE) {
            handleMoveState(pos);
        } else {
            handleInsertState(pos);
        }
    }

    /**
     * Handles the move state logic. It selects a totem if clicked, or moves it to the
     * selected position if valid.
     *
     * @param pos The position where the user clicked.
     */
    private void handleMoveState(Position pos) {
        if (currentTotem == null && game.getPieceAtPos(pos.getX(), pos.getY()) instanceof Totem) {
            selectTotem(pos);
        } else {
            moveTotem(pos);
        }
    }

    /**
     * Selects a totem at the given position and highlights it.
     *
     * @param pos The position of the totem to be selected.
     */
    private void selectTotem(Position pos) {
        currentTotem = (Totem) game.getPieceAtPos(pos.getX(), pos.getY());
        selectedTotemPosition = pos;
        mainView.highLight(pos, true);
    }

    /**
     * Moves the selected totem to a new position if the move is valid.
     *
     * @param pos The target position for the move.
     */
    private void moveTotem(Position pos) {
        try {
            if (game.isValidMove(currentTotem, pos)) {
                game.move(pos.getX(), pos.getY(), currentTotem.getSymbol());
                mainView.highLight(selectedTotemPosition, false);
                resetTotemSelection();
            } else {
                mainView.displayError();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Resets the selected totem and its position.
     */
    private void resetTotemSelection() {
        selectedTotemPosition = null;
        currentTotem = null;
    }

    /**
     * Handles the insertion state logic when the user inserts a token into the board.
     *
     * @param pos The position where the user tries to insert a token.
     */
    private void handleInsertState(Position pos) {
        try {
            game.insert(pos.getX(), pos.getY());
            if (!game.isEnd()) {
                game.autoPlay();
            }
        } catch (IllegalArgumentException e) {
            mainView.displayError();
            System.out.println("Invalid insertion : " + e.getMessage());
        }
    }

    /**
     * Handles the surrender button action.
     * Ends the game and displays a surrender message.
     */
    public void surrenderButton() {
        mainView.surrender();
    }

    /**
     * Handles the undo button action. Undoes the last action in the game.
     */
    public void initializeButtonUndoHandlers() {
        if (game.getGameState() == GameState.MOVE) {
            currentTotem = null;
        }
        game.undo();
        System.out.println("Undo successfully completed.");
        buttonLogic();
    }

    /**
     * Handles the redo button action. Redoes the last undone action in the game.
     */
    public void initializeButtonRedoHandlers() {
        game.redo();
        System.out.println("Redo successfully completed.");
        buttonLogic();
    }

    /**
     * Contains the logic for handling highlighting of the totem or token.
     */
    private void buttonLogic() {
        Position previousPos = selectedTotemPosition;
        if (previousPos != null) {
            mainView.highLight(previousPos, false);
        }
    }

    /**
     * Handles the quit button action. Exits the application.
     */
    public void initializeButtonQuitHandlers() {
        Platform.exit();
    }

    /**
     * Checks and highlights available positions for moving the totem.
     *
     * @param pos The position to check for a valid totem move.
     */
    public void checkAvailablePositionTotem(Position pos) {
        if (currentTotem != null) {
            List<Position> possibleMoves = game.getPossibleTotemMoves(currentTotem.getSymbol());
            if (possibleMoves.contains(pos)) {
                mainView.applyGlowEffect(pos, true);
            }
        }
    }

    /**
     * Checks and highlights available positions for inserting a token.
     *
     * @param pos The position to check for a valid token insertion.
     */
    public void checkAvailablePositionToken(Position pos) {
        if (game.getGameState() == GameState.INSERT) {
            List<Position> possibleInsert = game.getEmptyPositions(game.getToInsert(), game.getCurrentColor());
            if (possibleInsert.contains(pos)) {
                mainView.applyGlowEffect(pos, true);
            }
        }
    }
}
