package g63551.dev3.oxono.model;

import java.util.Objects;

/**
 * Represents a game piece with a symbol and color.
 * This is an abstract class that can be extended by specific piece types like Token.
 */
public abstract class Piece {

    protected Color color;
    protected Symbol symbol;

    /**
     * Constructs a Piece with the specified symbol.
     *
     * @param symbol the symbol of the piece (CROSS or CIRCLE)
     */
    public Piece(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the color of the piece.
     *
     * @return the color of the piece
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the symbol of the piece.
     *
     * @return the symbol of the piece
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Compares this piece with another object for equality.
     *
     * @param o the object to compare
     * @return true if the object is a Piece with the same color and symbol, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return color == piece.color && symbol == piece.symbol;
    }

    /**
     * Returns a hash code for this piece based on its color and symbol.
     *
     * @return the hash code for the piece
     */
    @Override
    public int hashCode() {
        return Objects.hash(color, symbol);
    }
}
