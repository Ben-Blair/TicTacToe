package tictactoe;

/**
 * DECORATOR PATTERN: a StrategyDecorator IS a MoveStrategy and also HAS a MoveStrategy (the one it wraps).
 * Each decorator adds one rule. If its rule doesn't apply, it asks the wrapped strategy instead.
 * Decorators can be stacked inside each other.
 */
public abstract class StrategyDecorator implements MoveStrategy {

    protected final MoveStrategy wrapped;

    /** DEPENDENCY INJECTION: the strategy being decorated is passed in. */
    public StrategyDecorator(MoveStrategy wrapped) {
        this.wrapped = wrapped;
    }

    /** Returns an empty cell that would give 'mark' three in a row, or null if there isn't one. */
    protected Position findWinningCell(Board board, Mark mark) {
        for (Position position : board.getEmptyPositions()) {
            Board trial = board.copy();          // try the move on a copy so the real board doesn't change
            trial.place(position, mark);
            if (trial.getWinner() == mark) {
                return position;
            }
        }
        return null;
    }
}
