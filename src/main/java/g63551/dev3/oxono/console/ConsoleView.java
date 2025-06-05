package g63551.dev3.oxono.console;

import g63551.dev3.oxono.model.Color;
import g63551.dev3.oxono.model.Game;
import g63551.dev3.oxono.model.Piece;

/**
 * ConsoleView is responsible for displaying the current state of the game on the console.
 * It displays the game board as a grid with symbols representing the pieces.
 */
public class ConsoleView {

    /**
     * Displays the current game state in the console.
     * The board is shown with the pieces represented by "X" or "O", in different colors.
     *
     * @param game The Game object representing the current state of the game.
     */
    public void display(Game game) {
        int rows = game.getSize();
        int cols = game.getSize();

        printTopBottomBorder(cols);

        for (int i = 0; i < rows; i++) {
            System.out.print("  |");
            for (int j = 0; j < cols; j++) {
                printCell(game.getPieceAtPos(i, j));
            }
            System.out.println();
            printTopBottomBorder(cols);
        }
        System.out.println();
    }

    /**
     * Prints the top and bottom borders of the game grid.
     *
     * @param cols The number of columns in the game grid.
     */
    private void printTopBottomBorder(int cols) {
        System.out.print("  +");
        for (int j = 0; j < cols; j++) {
            System.out.print("---+");
        }
        System.out.println();
    }

    /**
     * Prints the content of a cell, which could be empty or contain a piece.
     *
     * @param piece The Piece object at a given position, or null if the cell is empty.
     */
    private void printCell(Piece piece) {
        if (piece == null) {
            System.out.print("   |");
        } else {
            String symbolToPrint = getSymbol(piece);
            String colorCode = getColorCode(piece);
            System.out.print(colorCode + " " + symbolToPrint + " " + "\u001B[0m|");
        }
    }

    /**
     * Gets the symbol of a piece (either "X" or "O").
     *
     * @param piece The Piece object.
     * @return The symbol of the piece.
     */
    private String getSymbol(Piece piece) {
        switch (piece.getSymbol()) {
            case CROSS:
                return "X";
            case CIRCLE:
                return "O";
            default:
                return " ";
        }
    }

    /**
     * Gets the color code for a piece based on its color.
     *
     * @param piece The Piece object.
     * @return The color code for the piece.
     */
    private String getColorCode(Piece piece) {
        if (piece.getColor() == Color.PINK) {
            return "\u001B[35m"; // Magenta for PINK
        } else if (piece.getColor() == Color.BLACK) {
            return "\u001B[33m"; // Yellow for BLACK
        } else {
            return "\u001B[34m"; // Blue for default
        }
    }
}
