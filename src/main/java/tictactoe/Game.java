package tictactoe;

import java.util.ArrayList;

/** Runs a game: players take turns until someone wins or the board is full. */
public class Game {

    private final Board board;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final ArrayList<GameListener> listeners = new ArrayList<>();
    private Player currentPlayer;

    /** DEPENDENCY INJECTION: the board and players are passed in instead of created here. */
    public Game(Board board, Player firstPlayer, Player secondPlayer) {
        this.board = board;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.currentPlayer = firstPlayer;
    }

    public void addListener(GameListener listener) {
        listeners.add(listener);
    }

    public void play() {
        while (!isOver()) {
            playTurn();
        }
        for (GameListener listener : listeners) {
            listener.onGameOver(board.getWinner(), board);
        }
    }

    public void playTurn() {
        // If the game is already finished, nobody gets to move.
        if (isOver()) {
            return;
        }

        Position position = currentPlayer.chooseMove(board);
        boolean placed = board.place(position, currentPlayer.getMark());

        // If the move wasn't allowed (cell taken or off the board), it's still the same player's turn.
        if (!placed) {
            return;
        }

        for (GameListener listener : listeners) {
            listener.onMoveMade(currentPlayer, position, board);
        }
        switchPlayer();
    }

    public boolean isOver() {
        return board.getWinner() != null || board.isFull();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    private void switchPlayer() {
        if (currentPlayer == firstPlayer) {
            currentPlayer = secondPlayer;
        } else {
            currentPlayer = firstPlayer;
        }
    }
}
