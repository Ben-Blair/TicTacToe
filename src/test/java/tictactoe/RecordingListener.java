package tictactoe;

/** Test helper: a fake observer that remembers what it was told. */
class RecordingListener implements GameListener {

    int movesSeen = 0;
    boolean gameOverCalled = false;
    Mark winner = null;

    @Override
    public void onMoveMade(Player player, Position position, Board board) {
        movesSeen++;
    }

    @Override
    public void onGameOver(Mark winner, Board board) {
        gameOverCalled = true;
        this.winner = winner;
    }
}
