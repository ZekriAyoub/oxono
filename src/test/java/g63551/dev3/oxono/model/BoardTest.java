package g63551.dev3.oxono.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(6);
    }

    @Test
    void testBoardInitialization() {
        assertEquals(6, board.getSize());
        assertNotNull(board.getPosO());
        assertNotNull(board.getPosX());
        assertEquals(34, board.countEmpty());
    }

    @Test
    void testIsEmpty() {
        Position emptyPos = new Position(0, 0);
        assertTrue(board.isEmpty(emptyPos));

        Token token = new Token(Symbol.CIRCLE, Color.PINK);
        Position pos = new Position(2, 1);
        board.insert(token, pos);
        assertFalse(board.isEmpty(pos));

        assertFalse(board.isEmpty(board.getPosO()));
        assertFalse(board.isEmpty(board.getPosX()));
    }

    @Test
    void testMoveTotem() {
        Totem totemO = board.getTotem(Symbol.CIRCLE);

        board.move(totemO, new Position(1, 2));
        assertTrue(board.isEmpty(new Position(2, 2)));
        assertEquals(totemO, board.getPieceAtPos(1, 2));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            board.move(totemO, new Position(5, 5));
        });
        assertEquals("Invalid position for moving this totem pole!", exception.getMessage());
    }

    @Test
    void testInsertToken1() {
        Token token = new Token(Symbol.CIRCLE, Color.PINK);
        Position validPosition = new Position(2, 1);

        board.insert(token, validPosition);
        assertEquals(token, board.getPieceAtPos(validPosition.getX(), validPosition.getY()));

        Token toke = new Token(Symbol.CIRCLE, Color.PINK);
        Position invalidPosition = new Position(5, 0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            board.insert(toke, invalidPosition);
        });

        assertEquals("Invalid position for insertion!", exception.getMessage());
    }

    @Test
    void testMoveValidTotem() {
        Position startPos = board.getPosO();
        Position targetPos = new Position(startPos.getX() + 1, startPos.getY());
        Totem totemO = board.getTotem(Symbol.CIRCLE);

        assertTrue(board.isValidMove(totemO, targetPos));
        board.move(totemO, targetPos);

        assertEquals(targetPos, board.getPosO());
        assertNull(board.getPieceAtPos(startPos.getX(), startPos.getY()));
        assertEquals(totemO, board.getPieceAtPos(targetPos.getX(), targetPos.getY()));
    }

    @Test
    void testMoveValidTotemAccordingRule1() {
        Totem totemO = board.getTotem(Symbol.CIRCLE);
        Token token = new Token(Symbol.CIRCLE, Color.PINK);
        board.move(totemO, new Position(1, 2));
        board.insert(token, new Position(0, 2));
        board.move(totemO, new Position(2, 2));
        board.insert(token, new Position(1, 2));
        board.move(totemO, new Position(3, 2));
        board.insert(token, new Position(2, 2));
        board.move(totemO, new Position(4, 2));
        board.insert(token, new Position(3, 2));
        board.move(totemO, new Position(5, 2));
        board.insert(token, new Position(4, 2));

        board.move(totemO, new Position(5, 4));
        board.insert(token, new Position(5, 5));
        board.move(totemO, new Position(5, 3));
        board.insert(token, new Position(5, 4));
        board.move(totemO, new Position(5, 1));
        board.insert(token, new Position(5, 0));
        board.move(totemO, new Position(5, 2));
        board.insert(token, new Position(5, 1));
        board.insert(token, new Position(5, 3));

        assertTrue(board.isValidMove(totemO, new Position(0, 0))); //can move everywhere
    }

    @Test
    void testMoveValidTotemAccordingRule2() {
        Token token = new Token(Symbol.CIRCLE, Color.PINK);
        board.insert(token, new Position(1, 2));
        board.insert(token, new Position(3, 2));
        board.insert(token, new Position(2, 1));
        board.insert(token, new Position(2, 3));

        assertTrue(board.isValidMove(board.getTotem(Symbol.CIRCLE), new Position(2, 0)));
        assertFalse(board.isValidMove(board.getTotem(Symbol.CIRCLE), new Position(2, 5)));
    }

    @Test
    void testMoveValidTotemAccordingRule3() {
        board.insert(new Token(Symbol.CIRCLE, Color.PINK), new Position(2, 1));
        board.insert(new Token(Symbol.CIRCLE, Color.PINK), new Position(3, 2));

        assertTrue(board.isValidMove(board.getTotem(Symbol.CIRCLE), new Position(0, 2)));
        assertFalse(board.isValidMove(board.getTotem(Symbol.CIRCLE), new Position(2, 0)));
    }

    @Test
    void testInsertTokenAccordingRule1() {
        Token token = new Token(Symbol.CIRCLE, Color.PINK);
        board.insert(token, new Position(1, 2));
        board.insert(token, new Position(3, 2));
        board.insert(token, new Position(2, 1));
        board.insert(token, new Position(2, 3));

        Token toke = new Token(Symbol.CIRCLE, Color.PINK);
        assertTrue(board.isValidInsert(toke, new Position(5, 5)));
    }

    @Test
    void testInsertTokenAccordingRule2() {
        Token token = new Token(Symbol.CIRCLE, Color.PINK);
        assertTrue(board.isValidInsert(token, new Position(1, 2)));
        assertFalse(board.isValidInsert(token, new Position(0, 0)));

        Token token2 = new Token(Symbol.CIRCLE, Color.PINK);
        assertFalse(board.isValidInsert(token2, new Position(3, 4)));
    }


    @Test
    void testGetPossibleTotemMoves() {
        List<Position> movesForCircle = board.getPossibleTotemMoves(Symbol.CIRCLE);
        assertFalse(movesForCircle.isEmpty());

        for (Position p : movesForCircle) {
            assertTrue(board.isValidMove(board.getTotem(Symbol.CIRCLE), p));
        }
    }

    @Test
    void testGetEmptyPositions() {
        List<Position> emptyPositions = board.getEmptyPositions(Symbol.CIRCLE, Color.PINK);
        assertFalse(emptyPositions.isEmpty());

        for (Position pos : emptyPositions) {
            assertTrue(board.isValidInsert(new Token(Symbol.CIRCLE, Color.PINK), pos));
        }
    }

    @Test
    void testCheckAlignment() {
        Token token = new Token(Symbol.CIRCLE, Color.PINK);
        Totem totemO = board.getTotem(Symbol.CIRCLE);
        board.insert(token, new Position(1, 2));
        board.move(totemO, new Position(2, 3));
        board.insert(token, new Position(1, 3));
        board.move(totemO, new Position(2, 4));
        board.insert(token, new Position(1, 4));
        board.move(totemO, new Position(2, 5));
        board.insert(token, new Position(1, 5));

        assertTrue(board.checkAlignment(1, 5, 0, -1)); // Alignement found
        assertFalse(board.checkAlignment(1, 5, 1, 0)); // no alignement
    }


    @Test
    void testRemoveToken() {
        Token token = new Token(Symbol.CIRCLE, Color.PINK);
        Position pos = new Position(2, 1);

        board.insert(token, pos);
        assertEquals(token, board.getPieceAtPos(pos.getX(), pos.getY()));

        board.removeToken(pos);
        assertNull(board.getPieceAtPos(pos.getX(), pos.getY()));
    }

    @Test
    void testCountEmpty() {
        int initialEmptyCount = board.countEmpty();

        Token token = new Token(Symbol.CIRCLE, Color.PINK);
        board.insert(token, new Position(2, 1));

        assertEquals(initialEmptyCount - 1, board.countEmpty());
    }

    @Test
    void testSetPosTotem() {
        Totem totemO = board.getTotem(Symbol.CIRCLE);
        board.setPosTotem(totemO, new Position(2, 0));
        assertEquals(board.getPosO(), new Position(2, 0));
    }

}