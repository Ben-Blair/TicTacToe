package tictactoe;

/** An observer that prints the board to the console. */
public class ConsoleView implements GameListener {

    @Override
    public void onMoveMade(Player player, Position position, Board board) {
        System.out.println();
        System.out.println("Player " + player.getMark() + " moved:");
        printBoard(board);
    }

    @Override
    public void onGameOver(Mark winner, Board board) {
        if (winner == null) {
            System.out.println("It's a draw!");
        } else {
            System.out.println("Player " + winner + " wins!");
        }
    }

    private void printBoard(Board board) {
        for (int row = 0; row < Board.SIZE; row++) {
            StringBuilder line = new StringBuilder(" ");
            for (int col = 0; col < Board.SIZE; col++) {
                Mark mark = board.getMark(new Position(row, col));
                line.append(mark == null ? " " : mark.toString());
                if (col < Board.SIZE - 1) {
                    line.append(" | ");
                }
            }
            System.out.println(line);
            if (row < Board.SIZE - 1) {
                System.out.println("---+---+---");
            }
        }
    }
}
