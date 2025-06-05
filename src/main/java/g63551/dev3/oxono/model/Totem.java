package g63551.dev3.oxono.model;

/**
 * Represents a totem piece in the game, which has a symbol.
 * This class extends the Piece class and provides initialization for the symbol.
 */
public class Totem extends Piece {

    /**
     * Constructs a Totem with the specified symbol.
     *
     * @param symbol the symbol of the totem (CROSS or CIRCLE)
     */
    public Totem(Symbol symbol) {
        super(symbol);
    }
}
