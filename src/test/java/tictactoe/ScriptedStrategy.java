package tictactoe;

/** Test helper: a fake strategy that plays a fixed list of moves in order. */
class ScriptedStrategy implements MoveStrategy {

    private final Position[] moves;
    private int nextMove = 0;

    /** "Position..." lets you pass any number of positions. Java hands them to us as an array. */
    ScriptedStrategy(Position... moves) {
        this.moves = moves;
    }

    @Override
    public Position chooseMove(Board board, Mark mark) {
        Position move = moves[nextMove];
        nextMove++;
        return move;
    }
}
