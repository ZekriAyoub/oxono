package g63551.dev3.oxono.model;

import java.util.Objects;

/**
 * Represents a position on a 2D grid with x and y coordinates.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Constructs a Position object with specified x and y coordinates.
     *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the position.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the position.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Compares this position with another object for equality.
     *
     * @param o the object to compare
     * @return true if the object is a Position with the same x and y coordinates, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    /**
     * Returns a hash code for this position based on its coordinates.
     *
     * @return the hash code for the position
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns a string representation of this position.
     *
     * @return a string representing the position
     */
    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
