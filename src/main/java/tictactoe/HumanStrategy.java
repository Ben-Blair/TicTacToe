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

            // If there is nothing left to read (for example, the user pressed Ctrl+D), stop asking.
            if (!scanner.hasNext()) {
                throw new IllegalStateException("No more input.");
            }

            if (scanner.hasNextInt()) {
                int cell = scanner.nextInt();
                if (cell >= 1 && cell <= 9) {
                    // Cells 1-9 go left to right, top to bottom. For example, cell 6 is row 1, column 2.
                    Position position = new Position((cell - 1) / 3, (cell - 1) % 3);
                    if (board.isEmpty(position)) {
                        return position;
                    }
                }
            } else {
                scanner.next(); // throw away the word that wasn't a number
            }
            System.out.println("That cell isn't available. Try again.");
        }
    }
}
