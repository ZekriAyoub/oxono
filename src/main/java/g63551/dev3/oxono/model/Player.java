package g63551.dev3.oxono.model;

import g63551.dev3.oxono.model.strategy.RandomStrategy;
import g63551.dev3.oxono.model.strategy.Strategy;

/**
 * Represents a player in the game.
 * Each player has a color, a strategy for making moves, and a number of tokens for each symbol (CIRCLE and CROSS).
 */
public class Player {

    private Strategy strategy;
    private int nbTokensX;
    private int nbTokensO;
    private final Color color;
    private static final int nbTokens = 8;

    /**
     * Constructs a player with the specified color.
     * The player starts with 8 tokens for each symbol (CIRCLE and CROSS).
     *
     * @param color the color of the player
     */
    public Player(Color color) {
        this.color = color;
        this.nbTokensX = nbTokens;
        this.nbTokensO = nbTokens;
    }

    /**
     * Sets the strategy for the player based on the given level.
     *
     * @param level the level of the strategy (1 for RandomStrategy)
     */
    public void setStrategy(int level) {
        if (level == 1) {
            this.strategy = new RandomStrategy();
        }
    }

    /**
     * Executes the player's strategy to play the game.
     *
     * @param game the current game instance
     */
    public void play(Game game) {
        if (strategy != null) {
            strategy.play(game);
        }
    }

    /**
     * Returns the color of the player.
     *
     * @return the color of the player
     */
    public Color getColor() {
        return color;
    }

    /**
     * Decreases the number of tokens for the specified symbol.
     *
     * @param symbol the symbol (CIRCLE or CROSS) for which the token count should be decreased
     */
    public void decreaseTokens(Symbol symbol) {
        if (symbol == Symbol.CIRCLE) {
            this.nbTokensO--;
        } else if (symbol == Symbol.CROSS) {
            this.nbTokensX--;
        }
    }

    /**
     * Increases the number of tokens for the specified symbol.
     *
     * @param symbol the symbol (CIRCLE or CROSS) for which the token count should be increased
     */
    public void increaseTokens(Symbol symbol) {
        if (symbol == Symbol.CIRCLE) {
            this.nbTokensO++;
        } else if (symbol == Symbol.CROSS) {
            this.nbTokensX++;
        }
    }

    /**
     * Returns the number of tokens for the specified symbol.
     *
     * @param symbol the symbol (CIRCLE or CROSS) for which the token count is requested
     * @return the number of tokens for the specified symbol
     */
    public int getNbTokens(Symbol symbol) {
        return (symbol == Symbol.CIRCLE) ? nbTokensO : (symbol == Symbol.CROSS) ? nbTokensX : 0;
    }

    /**
     * Returns a string representation of the player, including their color.
     *
     * @return the string representation of the player
     */
    @Override
    public String toString() {
        return " player " + color;
    }

    /**
     * Returns the number of tokens of the CIRCLE symbol.
     *
     * @return the number of CIRCLE tokens
     */
    public int getNbTokensX() {
        return nbTokensX;
    }

    /**
     * Returns the number of tokens of the CROSS symbol.
     *
     * @return the number of CROSS tokens
     */
    public int getNbTokensO() {
        return nbTokensO;
    }
}
