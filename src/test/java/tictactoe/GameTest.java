package tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void playersTakeTurns() {
        Player x = new Player(Mark.X, new ScriptedStrategy(new Position(0, 0)));
        Player o = new Player(Mark.O, new ScriptedStrategy(new Position(1, 1)));
        Game game = new Game(new Board(), x, o);

        assertSame(x, game.getCurrentPlayer());
        game.playTurn();
        assertSame(o, game.getCurrentPlayer());
        game.playTurn();
        assertSame(x, game.getCurrentPlayer());
    }

    @Test
    void gameEndsWhenSomeoneWinsAndListenersAreTold() {
        // X takes the top row; O plays in the middle row.
        Player x = new Player(Mark.X, new ScriptedStrategy(
                new Position(0, 0), new Position(0, 1), new Position(0, 2)));
        Player o = new Player(Mark.O, new ScriptedStrategy(
                new Position(1, 0), new Position(1, 1)));
        Game game = new Game(new Board(), x, o);
        RecordingListener listener = new RecordingListener();
        game.addListener(listener);

        game.play();

        assertTrue(game.isOver());
        assertEquals(5, listener.movesSeen);
        assertTrue(listener.gameOverCalled);
        assertEquals(Mark.X, listener.winner);
    }

    @Test
    void invalidMoveDoesNotEndTheTurn() {
        Board board = new Board();
        board.place(new Position(0, 0), Mark.O);
        // X tries the taken cell (0,0).
        Player x = new Player(Mark.X, new ScriptedStrategy(new Position(0, 0)));
        Player o = new Player(Mark.O, new ScriptedStrategy(new Position(1, 1)));
        Game game = new Game(board, x, o);
        RecordingListener listener = new RecordingListener();
        game.addListener(listener);

        game.playTurn();

        assertEquals(Mark.O, board.getMark(new Position(0, 0)));   // cell didn't change
        assertSame(x, game.getCurrentPlayer());                    // still X's turn
        assertEquals(0, listener.movesSeen);                       // no move was reported
    }

    @Test
    void noMovesAfterTheGameIsOver() {
        Board board = new Board();
        board.place(new Position(0, 0), Mark.X);
        board.place(new Position(0, 1), Mark.X);
        board.place(new Position(0, 2), Mark.X);   // X already won
        Player x = new Player(Mark.X, new ScriptedStrategy(new Position(2, 2)));
        Player o = new Player(Mark.O, new ScriptedStrategy(new Position(2, 2)));
        Game game = new Game(board, x, o);

        game.playTurn();

        assertNull(board.getMark(new Position(2, 2)));   // nothing was placed
    }
}
