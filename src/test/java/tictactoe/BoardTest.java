package tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void newBoardHasNineEmptyCells() {
        Board board = new Board();
        assertEquals(9, board.getEmptyPositions().size());
        assertNull(board.getWinner());
    }

    @Test
    void placedMarkCanBeReadBack() {
        Board board = new Board();
        board.place(new Position(1, 2), Mark.X);
        assertEquals(Mark.X, board.getMark(new Position(1, 2)));
        assertFalse(board.isEmpty(new Position(1, 2)));
    }

    @Test
    void cannotPlaceOnTakenCell() {
        Board board = new Board();
        board.place(new Position(0, 0), Mark.X);
        assertFalse(board.place(new Position(0, 0), Mark.O));
        assertEquals(Mark.X, board.getMark(new Position(0, 0)));   // still X
    }

    @Test
    void cannotPlaceOffTheBoard() {
        Board board = new Board();
        assertFalse(board.place(new Position(3, 0), Mark.X));
    }

    @Test
    void cannotPlaceAtNullPosition() {
        Board board = new Board();
        assertFalse(board.place(null, Mark.X));
    }

    @Test
    void threeInARowWins() {
        Board board = new Board();
        board.place(new Position(0, 0), Mark.X);
        board.place(new Position(0, 1), Mark.X);
        board.place(new Position(0, 2), Mark.X);
        assertEquals(Mark.X, board.getWinner());
    }

    @Test
    void threeInAColumnWins() {
        Board board = new Board();
        board.place(new Position(0, 1), Mark.O);
        board.place(new Position(1, 1), Mark.O);
        board.place(new Position(2, 1), Mark.O);
        assertEquals(Mark.O, board.getWinner());
    }

    @Test
    void threeOnADiagonalWins() {
        Board board = new Board();
        board.place(new Position(0, 2), Mark.X);
        board.place(new Position(1, 1), Mark.X);
        board.place(new Position(2, 0), Mark.X);
        assertEquals(Mark.X, board.getWinner());
    }

    @Test
    void fullBoardWithNoWinnerIsADraw() {
        // X O X
        // X O O
        // O X X
        Board board = new Board();
        Mark[][] layout = {
            {Mark.X, Mark.O, Mark.X},
            {Mark.X, Mark.O, Mark.O},
            {Mark.O, Mark.X, Mark.X}
        };
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board.place(new Position(row, col), layout[row][col]);
            }
        }
        assertTrue(board.isFull());
        assertNull(board.getWinner());
    }
}
