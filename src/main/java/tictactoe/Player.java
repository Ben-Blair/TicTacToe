package tictactoe;

/** A player has a mark (X or O) and a strategy for choosing moves. */
public class Player {

    private final Mark mark;
    private final MoveStrategy strategy;

    public Player(Mark mark, MoveStrategy strategy) {
        this.mark = mark;
        this.strategy = strategy;
    }

    public Mark getMark() {
        return mark;
    }

    /** The player doesn't know HOW the move is chosen, it just asks its strategy (polymorphism). */
    public Position chooseMove(Board board) {
        return strategy.chooseMove(board, mark);
    }
}
