package g63551.dev3.oxono.model.strategy;

import g63551.dev3.oxono.model.*;

import java.util.List;
import java.util.Random;

/**
 * Strategy that makes random moves for the game, both for the totem and token.
 * It randomly selects a totem move and a token placement.
 */
public class RandomStrategy implements Strategy {
    private final Random random; // Random object to generate random moves

    /**
     * Constructs a RandomStrategy with a new Random object.
     */
    public RandomStrategy() {
        this.random = new Random();
    }

    /**
     * Executes the random strategy by selecting a random totem move and a random token placement.
     *
     * @param game the game instance in which the random moves are made
     */
    @Override
    public void play(Game game) {
        Symbol symbol = random.nextBoolean() ? Symbol.CROSS : Symbol.CIRCLE;

        // Select a random move for the totem
        List<Position> possibleTotemMoves = game.getPossibleTotemMoves(symbol);
        Position randomMove = possibleTotemMoves.get(random.nextInt(possibleTotemMoves.size()));
        game.move(randomMove.getX(), randomMove.getY(), symbol);

        // Select a random position to insert a token
        List<Position> possibleTokenPositions = game.getEmptyPositions(game.getToInsert(), game.getCurrentColor());
        Position randomPosition = possibleTokenPositions.get(random.nextInt(possibleTokenPositions.size()));
        int x = randomPosition.getX();
        int y = randomPosition.getY();
        game.insert(x, y);
    }
}
