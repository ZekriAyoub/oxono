package g63551.dev3.oxono.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(6, 1);
    }

    @Test
    void testGameInitialization() {
        assertEquals(6, game.getSize());
        assertEquals(GameState.MOVE, game.getGameState());
        assertNotNull(game.displayCurrentPlayer());
        assertFalse(game.isEnd());
    }

    @Test
    void testMove() {
        game.move(2, 0, Symbol.CIRCLE);
        assertEquals(GameState.INSERT, game.getGameState());
        assertEquals(Symbol.CIRCLE, game.getToInsert());
    }

    @Test
    void testMoveTwo() {
        game.move(3, 5, Symbol.CROSS);
        assertEquals(GameState.INSERT, game.getGameState());
        assertEquals(Symbol.CROSS, game.getToInsert());
    }

    @Test
    void testInvalidMove() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            game.move(0,0, Symbol.CIRCLE);
        });
        assertEquals("Invalid position for moving this totem pole!", exception.getMessage());
    }

    @Test
    void testInvalidMoveTwo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            game.move(1,2, Symbol.CROSS);
        });
        assertEquals("Invalid position for moving this totem pole!", exception.getMessage());
    }

    @Test
    void testMoveValid() {
        game.move(3, 5, Symbol.CROSS);
        assertEquals(GameState.INSERT, game.getGameState());
        assertEquals(Symbol.CROSS, game.getToInsert());
    }

    @Test
    void testInsertToken() {
        game.move(2, 0, Symbol.CIRCLE);
        game.insert(2, 1);
        assertEquals(GameState.MOVE, game.getGameState());
    }

    @Test
    void testInsertTokenTwo() {
        game.move(3, 0, Symbol.CROSS);
        game.insert(3, 1);
        assertEquals(GameState.MOVE, game.getGameState());
    }

    @Test
    void testValidMove() {
        game.move(3, 0, Symbol.CROSS);
        assertEquals(GameState.INSERT, game.getGameState());
    }

    @Test
    void testWinningCondition() {
        game.move(2, 0, Symbol.CIRCLE);
        game.insert(2, 1);
        game.move(1, 0, Symbol.CIRCLE);
        game.insert(1, 1);
        game.move(0, 0, Symbol.CIRCLE);
        game.insert(0, 1);
        game.move(3, 0, Symbol.CIRCLE);
        game.insert(3, 1);
        assertTrue(game.isEnd());
    }

    @Test
    void testAutoPlay() {
        game.move(2, 0, Symbol.CIRCLE);
        game.insert(2, 1);
        assertEquals(game.getCurrentColor(), Color.BLACK);
    }

    @Test
    void testGetPieceAtPos() {
        game.move(2, 0, Symbol.CIRCLE);
        Piece piece = game.getPieceAtPos(2, 0);
        assertNotNull(piece);
    }

    @Test
    void testCountEmptyPositions() {
        int initialEmptyCount = game.countEmpty();
        game.move(2, 5, Symbol.CIRCLE);
        game.insert(2, 4);
        assertEquals(initialEmptyCount - 1, game.countEmpty());
    }

    @Test
    void testGetEmptyPositions() {
        assertFalse(game.getEmptyPositions(Symbol.CROSS, Color.BLACK).isEmpty());
    }

    @Test
    void testGetNbTokens() {
        int initialNbTokens = game.getNbTokens(Color.PINK, Symbol.CIRCLE);
        game.move(2, 0, Symbol.CIRCLE);
        game.insert(2, 1);
        assertEquals(initialNbTokens - 1, game.getNbTokens(Color.PINK, Symbol.CIRCLE));
    }

    @Test
    void testUndoRedo() {
        game.move(2, 0, Symbol.CIRCLE);
        game.insert(2, 1);
        assertTrue(game.canUndo());
        game.undo();
        assertEquals(GameState.INSERT, game.getGameState());
        assertTrue(game.canRedo());
    }

    @Test
    void testUndoWithMultipleMoves() {
        game.move(2, 5, Symbol.CIRCLE);
        game.insert(2, 4);
        game.move(5, 5, Symbol.CIRCLE);
        game.insert(5, 4);

        // Undo dernier mouvement
        game.undo();
        assertEquals(GameState.INSERT, game.getGameState());
        assertEquals(Symbol.CIRCLE, game.getToInsert());
        assertNull(game.getPieceAtPos(5,4));
        assertTrue(game.canRedo());
        game.redo();
        assertEquals(GameState.MOVE, game.getGameState());
    }

    @Test
    void testRedoAfterUndo() {
        game.move(2, 0, Symbol.CIRCLE);
        game.insert(2, 1);

        game.undo();
        game.undo();
        assertTrue(game.canRedo());

        game.redo();
        assertEquals(GameState.INSERT, game.getGameState());
        assertNull(game.getPieceAtPos(2, 1));
    }

    @Test
    void testUndoRedoSequence() {
        game.move(3, 0, Symbol.CROSS);
        game.insert(3, 1);
        game.move(2, 0, Symbol.CIRCLE);
        game.insert(2, 1);

        game.undo();
        game.undo();
        game.redo();
        game.redo();

        assertEquals(Symbol.CIRCLE, game.getPieceAtPos(2, 1).getSymbol());
        assertEquals(GameState.MOVE, game.getGameState());
    }

    @Test
    void testUndoLimitations() {
        assertFalse(game.canUndo());
        assertEquals(GameState.MOVE, game.getGameState());

        game.move(2, 0, Symbol.CIRCLE);
        game.insert(2, 1);
        game.undo();
        game.undo();
        assertFalse(game.canUndo());
        assertTrue(game.canRedo());

        assertFalse(game.canUndo());
    }


    @Test
    void testMoveInvalidOutOfBounds() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            game.move(2,10, Symbol.CIRCLE);
        });
        assertEquals("Invalid position for moving this totem pole!", exception.getMessage());
    }

    @Test
    void testInsertValid() {
        game.move(2, 5, Symbol.CIRCLE);
        game.insert(2, 4);

        assertNotNull(game.getPieceAtPos(2, 4));
        assertEquals(Symbol.CIRCLE, game.getPieceAtPos(2, 4).getSymbol());
        assertEquals(GameState.MOVE, game.getGameState());
    }

    @Test
    void testInsertWithoutMove() {
        game.insert(1, 1);
        assertNull(game.getPieceAtPos(1, 1));
        assertEquals(GameState.MOVE, game.getGameState());
    }

    @Test
    void testInsertOutOfBounds() {
        game.move(2, 0, Symbol.CIRCLE);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            game.insert(2, 10);
        });
        assertEquals("Invalid position for insertion!", exception.getMessage());

    }

    @Test
    void testInsertWinningCondition() {
        game.move(2, 0, Symbol.CIRCLE);
        game.insert(2, 1);
        game.move(1, 0, Symbol.CIRCLE);
        game.insert(1, 1);
        game.move(0, 0, Symbol.CIRCLE);
        game.insert(0, 1);
        game.move(3, 0, Symbol.CIRCLE);
        game.insert(3, 1);
        assertTrue(game.isEnd());
        assertEquals(GameState.INSERT, game.getGameState());
    }


    @Test
    void testSetToPlay(){
        game.move(2,0,Symbol.CIRCLE);
        game.insert(2,1);
        assertEquals(Color.BLACK,game.getCurrentColor());
    }

    @Test
    void testMoveValidTotem() {
        game.move(2,0,Symbol.CIRCLE);
        game.insert(2,1);
        game.move(0,0,Symbol.CIRCLE);
        game.insert(0,1);
        game.move(1,0,Symbol.CIRCLE);
        game.insert(1,1);
        game.move(0,0,Symbol.CIRCLE);
        game.insert(1,0);
        game.move(0,2,Symbol.CIRCLE);
    }

    @Test
    void testGetEmptyPositionsToInsert(){
        game.move(2,0,Symbol.CIRCLE);
        List<Position> liste = List.of(new Position(1,0),new Position(2,1),new Position(3,0));
        assertEquals(liste,game.getEmptyPositions(Symbol.CIRCLE,Color.PINK));
    }

}
