package tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class DecoratorTest {

    // The wrapped strategy always plays the top-right corner, so we can tell when the decorator passed the decision on.
    private static final Position FALLBACK = new Position(0, 2);

    private MoveStrategy fallback() {
        return new ScriptedStrategy(FALLBACK);
    }

    @Test
    void winningDecoratorCompletesThreeInARow() {
        Board board = new Board();
        board.place(new Position(1, 0), Mark.X);
        board.place(new Position(1, 1), Mark.X);
        MoveStrategy strategy = new WinningMoveDecorator(fallback());
        assertEquals(new Position(1, 2), strategy.chooseMove(board, Mark.X));
    }

    @Test
    void winningDecoratorDelegatesWhenNoWinExists() {
        MoveStrategy strategy = new WinningMoveDecorator(fallback());
        assertEquals(FALLBACK, strategy.chooseMove(new Board(), Mark.X));
    }

    @Test
    void blockingDecoratorBlocksOpponent() {
        Board board = new Board();
        board.place(new Position(0, 0), Mark.O);
        board.place(new Position(1, 0), Mark.O);
        MoveStrategy strategy = new BlockingMoveDecorator(fallback());
        assertEquals(new Position(2, 0), strategy.chooseMove(board, Mark.X));
    }

    @Test
    void blockingDecoratorDelegatesWhenNothingToBlock() {
        MoveStrategy strategy = new BlockingMoveDecorator(fallback());
        assertEquals(FALLBACK, strategy.chooseMove(new Board(), Mark.X));
    }

    @Test
    void stackedDecoratorsPreferWinningOverBlocking() {
        Board board = new Board();
        board.place(new Position(0, 0), Mark.X);
        board.place(new Position(0, 1), Mark.X);   // X can win at (0,2)
        board.place(new Position(2, 0), Mark.O);
        board.place(new Position(2, 1), Mark.O);   // O threatens (2,2)
        MoveStrategy strategy = new WinningMoveDecorator(new BlockingMoveDecorator(new RandomStrategy(new Random(1))));
        assertEquals(new Position(0, 2), strategy.chooseMove(board, Mark.X));
    }

    @Test
    void factoryBuildsSmartPlayerThatBlocks() {
        Board board = new Board();
        board.place(new Position(0, 1), Mark.X);
        board.place(new Position(1, 1), Mark.X);
        Player smart = new PlayerFactory(new Scanner(""), new Random(1)).createPlayer("smart", Mark.O);
        assertEquals(new Position(2, 1), smart.chooseMove(board));
    }

    @Test
    void copyIsIndependentOfOriginal() {
        Board board = new Board();
        board.place(new Position(0, 0), Mark.X);
        Board copy = board.copy();
        copy.place(new Position(1, 1), Mark.O);
        assertEquals(Mark.X, copy.getMark(new Position(0, 0)));
        assertNull(board.getMark(new Position(1, 1)));
    }

    @Test
    void opponentSwapsMarks() {
        assertEquals(Mark.O, Mark.X.opponent());
        assertEquals(Mark.X, Mark.O.opponent());
    }
}
