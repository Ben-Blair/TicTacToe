package tictactoe;

/** Decorator that blocks the opponent's winning move. Otherwise the wrapped strategy decides. */
public class BlockingMoveDecorator extends StrategyDecorator {

    public BlockingMoveDecorator(MoveStrategy wrapped) {
        super(wrapped);
    }

    @Override
    public Position chooseMove(Board board, Mark mark) {
        Position block = findWinningCell(board, mark.opponent());
        if (block != null) {
            return block;
        }
        return wrapped.chooseMove(board, mark);
    }
}
