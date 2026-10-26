package tictactoe;

import java.util.Random;
import java.util.Scanner;

/** Entry point. This is the only place where objects are created and wired together. */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlayerFactory factory = new PlayerFactory(scanner, new Random());

        Player playerX = askForPlayer(scanner, factory, Mark.X);
        Player playerO = askForPlayer(scanner, factory, Mark.O);

        Game game = new Game(new Board(), playerX, playerO);
        game.addListener(new ConsoleView());
        game.play();
    }

    private static Player askForPlayer(Scanner scanner, PlayerFactory factory, Mark mark) {
        while (true) {
            System.out.print("Is player " + mark + " a human or computer? ");
            try {
                return factory.createPlayer(scanner.nextLine().trim(), mark);
            } catch (IllegalArgumentException e) {
                System.out.println("Please type 'human' or 'computer'.");
            }
        }
    }
}
