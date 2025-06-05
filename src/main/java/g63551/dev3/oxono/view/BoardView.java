package g63551.dev3.oxono.view;

import g63551.dev3.oxono.controller.Controller;
import g63551.dev3.oxono.model.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * The BoardView class is responsible for displaying the game board to the user.
 * It visualizes the game state, including the pieces on the board, and handles
 * user interaction through mouse events. The view updates based on changes in
 * the model and forwards user actions to the controller.
 */
public class BoardView extends GridPane {

    private Game game;
    private Controller controller;

    /**
     * Constructs a BoardView object and initializes the grid for the board.
     *
     * @param game The game object that holds the current game state.
     * @param controller The controller object that manages interactions with the view.
     */
    public BoardView(Game game, Controller controller) {
        this.game = game;
        int size = game.getSize();
        this.controller = controller;
        initialisationGrid(size);
    }

    /**
     * Initializes the grid of the board by creating a cell for each position.
     * Each cell represents a position on the game board.
     *
     * @param size The size of the grid (i.e., the number of rows/columns).
     */
    public void initialisationGrid(int size) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Position pos = new Position(row, col);
                StackPane cell = createCell(game, pos);
                this.add(cell, col, row);
            }
        }
    }

    /**
     * Creates a StackPane for a specific position on the board, including a background
     * and an image representing a piece (if any).
     *
     * @param game The game object used to retrieve the piece at a given position.
     * @param pos The position of the cell on the grid.
     * @return A StackPane representing the cell in the grid.
     */
    private StackPane createCell(Game game, Position pos) {
        StackPane cell = new StackPane();

        Rectangle background = new Rectangle(52, 52);
        background.setFill(Color.TRANSPARENT);

        ImageView imageView = getImageForPosition(game, pos);

        cell.getChildren().addAll(background, imageView);

        // Handles mouse events to check available moves or insert positions
        cell.setOnMouseEntered(event -> {
            controller.checkAvailablePositionTotem(pos);
            controller.checkAvailablePositionToken(pos);
        });

        // Removes glow effect when mouse exits the cell
        cell.setOnMouseExited(event -> {
            applyGlowEffect(pos, false);
        });

        // Handles mouse click events to make a move or insertion
        cell.setOnMouseClicked(event -> {
            controller.click(pos);
        });

        return cell;
    }

    /**
     * Applies or removes a glow effect to a specific position on the board.
     *
     * @param pos The position to apply the glow effect to.
     * @param apply A boolean value that determines whether to apply or remove the glow effect.
     */
    public void applyGlowEffect(Position pos, boolean apply) {
        for (var node : getChildren()) {
            if (GridPane.getRowIndex(node) == pos.getX() && GridPane.getColumnIndex(node) == pos.getY()) {
                if (apply) {
                    DropShadow glowEffect = new DropShadow();
                    glowEffect.setColor(Color.GREEN);
                    glowEffect.setRadius(20);
                    glowEffect.setSpread(0.7);

                    ((StackPane) node).setEffect(glowEffect);
                } else {
                    ((StackPane) node).setEffect(null);
                }
                break;
            }
        }
    }

    /**
     * Retrieves the image to be displayed at a specific position based on the piece at that position.
     *
     * @param game The game object to get the piece at a given position.
     * @param pos The position to get the image for.
     * @return An ImageView containing the image for the given position.
     */
    private ImageView getImageForPosition(Game game, Position pos) {
        Image image = null;
        Piece piece = game.getPieceAtPos(pos.getX(), pos.getY());
        if (piece == null) {
            image = loadImage("/images/emptyCase.png");
        } else if (game.getPositionTotem(Symbol.CIRCLE).equals(pos)) {
            image = loadImage("/images/TotemO.png");
        } else if (game.getPositionTotem(Symbol.CROSS).equals(pos)) {
            image = loadImage("/images/TotemX.png");
        } else if (piece.getSymbol() == Symbol.CIRCLE && (piece.getColor() == g63551.dev3.oxono.model.Color.PINK)) {
            image = loadImage("/images/tokenOpink.png");
        } else if (piece.getSymbol() == Symbol.CIRCLE && (piece.getColor() == g63551.dev3.oxono.model.Color.BLACK)) {
            image = loadImage("/images/tokenOblack.png");
        } else if (piece.getSymbol() == Symbol.CROSS && (piece.getColor() == g63551.dev3.oxono.model.Color.PINK)) {
            image = loadImage("/images/tokenXpink.png");
        } else if (piece.getSymbol() == Symbol.CROSS && (piece.getColor() == g63551.dev3.oxono.model.Color.BLACK)) {
            image = loadImage("/images/tokenXblack.png");
        }

        if (image != null) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            return imageView;
        }
        return null;
    }

    /**
     * Loads an image from the specified file path.
     *
     * @param path The path to the image file.
     * @return The loaded Image object.
     */
    private Image loadImage(String path) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    }

    /**
     * Highlights the selected totem by changing the background color of the position.
     *
     * @param pos The position of the selected totem.
     * @param highlight A boolean value to determine whether to highlight the position.
     */
    public void highlightSelectedTotem(Position pos, boolean highlight) {
        for (var node : getChildren()) {
            if (GridPane.getRowIndex(node) == pos.getX() && GridPane.getColumnIndex(node) == pos.getY()) {
                StackPane cell = (StackPane) node;
                Rectangle background = (Rectangle) cell.getChildren().get(0);
                if (highlight) {
                    background.setFill(Color.RED);
                } else {
                    background.setFill(Color.TRANSPARENT);
                }
                break;
            }
        }
    }
}
