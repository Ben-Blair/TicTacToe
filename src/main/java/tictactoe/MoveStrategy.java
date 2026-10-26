package tictactoe;

/**
 * STRATEGY PATTERN: the interface for "how a player picks a move".
 * Each kind of player (human, computer) is a different implementation.
 */
public interface MoveStrategy {

    Position chooseMove(Board board, Mark mark);
}
