package tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
