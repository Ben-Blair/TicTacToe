package tictactoe;

/**
 * OBSERVER PATTERN: anything that wants to know what happens in the game
 * (the console display, a score tracker, a future GUI) implements this interface.
 */
public interface GameListener {

    void onMoveMade(Player player, Position position, Board board);

    /** winner is null when the game is a draw. */
    void onGameOver(Mark winner, Board board);
}
