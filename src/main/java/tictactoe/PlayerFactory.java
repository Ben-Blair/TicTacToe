package tictactoe;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * FACTORY PATTERN: builds a Player from a type name like "human", "computer", or "smart".
 * A map (like a dictionary: name -> strategy) is used instead of an if/else chain,
 * so adding a new player type is one new line.
 */
public class PlayerFactory {

    private final HashMap<String, MoveStrategy> strategies = new HashMap<>();

    public PlayerFactory(Scanner scanner, Random random) {
        strategies.put("human", new HumanStrategy(scanner));
        strategies.put("computer", new RandomStrategy(random));
        // DECORATOR: a random player, wrapped so it blocks first, then wrapped again so winning comes first.
        strategies.put("smart",
                new WinningMoveDecorator(new BlockingMoveDecorator(new RandomStrategy(random))));
    }

    /** Returns a new Player, or null if the type isn't one we know. */
    public Player createPlayer(String type, Mark mark) {
        if (type == null) {
            return null;
        }
        MoveStrategy strategy = strategies.get(type.toLowerCase());
        if (strategy == null) {
            return null;
        }
        return new Player(mark, strategy);
    }
}
