package tictactoe;

/** Decorator that takes a winning move if there is one. Otherwise the wrapped strategy decides. */
public class WinningMoveDecorator extends StrategyDecorator {

    public WinningMoveDecorator(MoveStrategy wrapped) {
        super(wrapped);
    }

    @Override
    public Position chooseMove(Board board, Mark mark) {
        Position win = findWinningCell(board, mark);
        if (win != null) {
            return win;
        }
        return wrapped.chooseMove(board, mark);
    }
}
