package g63551.dev3.oxono.model;

/**
 * Represents a token piece in the game, which has a symbol and color.
 * This class extends the Piece class and provides specific initialization for the color.
 */
public class Token extends Piece {

    /**
     * Constructs a Token with the specified symbol and color.
     *
     * @param symbol the symbol of the token (CROSS or CIRCLE)
     * @param color the color of the token (BLACK or PINK)
     */
    public Token(Symbol symbol, Color color) {
        super(symbol);
        this.color = color;
    }
}
