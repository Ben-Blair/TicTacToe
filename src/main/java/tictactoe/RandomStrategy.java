package tictactoe;

import java.util.List;
import java.util.Random;

/** Strategy for a simple computer player that picks any empty cell at random. */
public class RandomStrategy implements MoveStrategy {

    private final Random random;

    /** The Random is injected so tests can use a fixed seed. */
    public RandomStrategy(Random random) {
        this.random = random;
    }

    @Override
    public Position chooseMove(Board board, Mark mark) {
        List<Position> emptyPositions = board.getEmptyPositions();
        return emptyPositions.get(random.nextInt(emptyPositions.size()));
    }
}
