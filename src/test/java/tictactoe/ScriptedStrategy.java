package tictactoe;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/** Test helper: a fake strategy that plays a fixed list of moves in order. */
class ScriptedStrategy implements MoveStrategy {

    private final Queue<Position> moves;

    ScriptedStrategy(Position... moves) {
        this.moves = new LinkedList<>(List.of(moves));
    }

    @Override
    public Position chooseMove(Board board, Mark mark) {
        return moves.remove();
    }
}
