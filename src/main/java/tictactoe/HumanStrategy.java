package tictactoe;

import java.util.Scanner;

/** Strategy that asks a person to type a cell number from 1 to 9. */
public class HumanStrategy implements MoveStrategy {

    private final Scanner scanner;

    /** The Scanner is injected so tests can feed in fake input. */
    public HumanStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Position chooseMove(Board board, Mark mark) {
        while (true) {
            System.out.print("Player " + mark + ", choose a cell (1-9): ");
            String input = scanner.nextLine().trim();
            try {
                int cell = Integer.parseInt(input);
                // Cells 1-9 are numbered left to right, top to bottom.
                Position position = new Position((cell - 1) / 3, (cell - 1) % 3);
                if (board.isOnBoard(position) && board.isEmpty(position)) {
                    return position;
                }
            } catch (NumberFormatException e) {
                // Not a number: fall through and ask again.
            }
            System.out.println("That cell isn't available. Try again.");
        }
    }
}
