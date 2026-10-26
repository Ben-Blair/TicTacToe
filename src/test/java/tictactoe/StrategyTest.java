package tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class StrategyTest {

    @Test
    void randomStrategyOnlyPicksEmptyCells() {
        Board board = new Board();
        board.place(new Position(0, 0), Mark.X);
        board.place(new Position(1, 1), Mark.O);
        MoveStrategy strategy = new RandomStrategy(new Random(42));

        for (int i = 0; i < 20; i++) {
            assertTrue(board.isEmpty(strategy.chooseMove(board, Mark.X)));
        }
    }

    @Test
    void humanStrategyConvertsCellNumberToPosition() {
        MoveStrategy strategy = new HumanStrategy(new Scanner("6\n"));
        assertEquals(new Position(1, 2), strategy.chooseMove(new Board(), Mark.X));
    }

    @Test
    void humanStrategyAsksAgainAfterBadInput() {
        Board board = new Board();
        board.place(new Position(0, 0), Mark.O);
        // "hello" is not a number, "1" is taken, "10" is off the board, "5" is valid.
        MoveStrategy strategy = new HumanStrategy(new Scanner("hello\n1\n10\n5\n"));
        assertEquals(new Position(1, 1), strategy.chooseMove(board, Mark.X));
    }

    @Test
    void factoryCreatesPlayerWithRequestedMark() {
        PlayerFactory factory = new PlayerFactory(new Scanner(""), new Random());
        Player player = factory.createPlayer("computer", Mark.O);
        assertEquals(Mark.O, player.getMark());
    }

    @Test
    void factoryRejectsUnknownType() {
        PlayerFactory factory = new PlayerFactory(new Scanner(""), new Random());
        assertThrows(IllegalArgumentException.class, () -> factory.createPlayer("alien", Mark.X));
    }
}
