package g63551.dev3.oxono.model.command;

import g63551.dev3.oxono.model.*;

/**
 * Command to insert a token into the board at a specific position.
 * This command can be executed to insert the token and unexecuted to remove it.
 */
public class InsertTokenCmd implements Command {
    private Board board;       // The board on which the token is inserted
    private Token token;       // The token to be inserted
    private Position posFinale; // The final position for the token
    private Player player;     // The player who is performing the action

    /**
     * Constructs an InsertTokenCmd with the specified parameters.
     *
     * @param board the board where the token is inserted
     * @param token the token to insert
     * @param posFinale the final position where the token will be placed
     * @param player the player who performs the action
     */
    public InsertTokenCmd(Board board, Token token, Position posFinale, Player player) {
        this.board = board;
        this.token = token;
        this.posFinale = posFinale;
        this.player = player;
    }

    /**
     * Executes the command by inserting the token at the specified position and decreasing the player's tokens.
     */
    @Override
    public void execute() {
        this.board.insert(token, posFinale);
        player.decreaseTokens(token.getSymbol());
    }

    /**
     * Unexecutes the command by removing the token from the specified position and increasing the player's tokens.
     */
    @Override
    public void unexecute() {
        this.board.removeToken(posFinale);
        player.increaseTokens(token.getSymbol());
    }
}
