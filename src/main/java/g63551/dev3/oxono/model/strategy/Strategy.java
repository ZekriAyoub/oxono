package g63551.dev3.oxono.model.strategy;

import g63551.dev3.oxono.model.Game;

/**
 * Interface for different strategies that define how a player makes a move in the game.
 * Implementing classes should provide the logic for playing a game based on a specific strategy.
 */
public interface Strategy {

    /**
     * Executes the strategy for making a move in the game.
     *
     * @param game the game instance in which the move is made
     */
    void play(Game game);
}
