package g63551.dev3.oxono.model.command;

import g63551.dev3.oxono.model.*;

/**
 * Command to move a totem on the board from an initial position to a final position.
 * This command can be executed to move the totem and unexecuted to revert the move.
 */
public class MoveTotemCmd implements Command {
    private Board board;         // The board on which the totem is moved
    private Totem totem;         // The totem being moved
    private Position posInitiale; // The initial position of the totem
    private Position posFinale;   // The final position for the totem
    private Symbol initialSymbol; // The symbol of the totem at the start (to revert back if needed)

    /**
     * Constructs a MoveTotemCmd with the specified parameters.
     *
     * @param board the board where the totem is moved
     * @param totem the totem being moved
     * @param posInitiale the initial position of the totem
     * @param posFinale the final position for the totem
     * @param initialSymbol the initial symbol of the totem
     */
    public MoveTotemCmd(Board board, Totem totem, Position posInitiale, Position posFinale, Symbol initialSymbol) {
        this.board = board;
        this.totem = totem;
        this.posInitiale = posInitiale;
        this.posFinale = posFinale;
        this.initialSymbol = initialSymbol;
    }

    /**
     * Executes the command by moving the totem to the final position and updating the last moved symbol.
     */
    @Override
    public void execute() {
        this.board.move(totem, posFinale);
        board.setLastMoved(totem.getSymbol());
    }

    /**
     * Unexecutes the command by moving the totem back to the initial position and reverting the last moved symbol.
     */
    @Override
    public void unexecute() {
        this.board.backMove(totem, posInitiale);
        board.setLastMoved(initialSymbol);
    }
}
