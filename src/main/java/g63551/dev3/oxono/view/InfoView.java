package g63551.dev3.oxono.view;

import g63551.dev3.oxono.model.Color;
import g63551.dev3.oxono.model.Game;
import g63551.dev3.oxono.model.Symbol;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * The InfoView class represents the view that displays various game information.
 * It shows details such as the current player, remaining tokens, empty cells,
 * and error messages.
 */
public class InfoView extends VBox {

    private final Label errorMessage;
    private final Label emptyCase;
    private final Label currentPlayer;
    private final Label tokenXblack;
    private final Label tokenOblack;
    private final Label tokenXpink;
    private final Label tokenOpink;
    private final Game game;

    /**
     * Constructs an InfoView with the given game instance.
     *
     * @param game The game instance used to retrieve and display game information.
     */
    public InfoView(Game game) {
        this.game = game;

        errorMessage = new Label();
        emptyCase = new Label();
        currentPlayer = new Label();
        tokenXblack = new Label();
        tokenOblack = new Label();
        tokenXpink = new Label();
        tokenOpink = new Label();

        this.setSpacing(10);
        this.setPadding(new Insets(25));

        this.getChildren().addAll(errorMessage, currentPlayer, emptyCase, tokenXblack, tokenOblack, tokenXpink, tokenOpink);

        styleLabels(errorMessage, currentPlayer, emptyCase, tokenXblack, tokenOblack, tokenXpink, tokenOpink);

        update();
    }

    /**
     * Styles the provided labels to use a consistent font style.
     *
     * @param labels The labels to be styled.
     */
    private void styleLabels(Label... labels) {
        for (Label label : labels) {
            label.setFont(Font.font("Arial", 17));
        }
    }

    /**
     * Displays an error message indicating an invalid position.
     */
    public void displayError() {
        errorMessage.setText("Invalid position!");
    }

    /**
     * Updates the information displayed in the view based on the current game state.
     * This includes information about the current player, the number of empty cells,
     * and the remaining tokens for both colors and symbols.
     */
    public void update() {
        errorMessage.setText("");
        currentPlayer.setText("It's" + game.displayCurrentPlayer() + "'s turn");
        emptyCase.setText("Empty cells: " + game.countEmpty());
        tokenXblack.setText("Remaining X tokens (BLACK) : " + game.getNbTokens(Color.BLACK, Symbol.CROSS));
        tokenOblack.setText("Remaining O tokens (BLACK) : " + game.getNbTokens(Color.BLACK, Symbol.CIRCLE));
        tokenXpink.setText("Remaining X tokens (PINK) : " + game.getNbTokens(Color.PINK, Symbol.CROSS));
        tokenOpink.setText("Remaining O tokens (PINK) : " + game.getNbTokens(Color.PINK, Symbol.CIRCLE));
    }
}
