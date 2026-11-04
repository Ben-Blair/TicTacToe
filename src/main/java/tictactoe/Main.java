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

    /** Keeps asking until the user types a player type the factory knows. */
    private static Player askForPlayer(Scanner scanner, PlayerFactory factory, Mark mark) {
        Player player = null;
        while (player == null) {
            System.out.print("Is player " + mark + " human, computer, or smart? ");
            player = factory.createPlayer(scanner.next(), mark);
            if (player == null) {
                System.out.println("Please type 'human', 'computer', or 'smart'.");
            }
        }
        return player;
    }
}
