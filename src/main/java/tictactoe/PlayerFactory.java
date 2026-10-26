package tictactoe;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * FACTORY PATTERN: builds a Player from a type name like "human" or "computer".
 * A map is used instead of an if/else chain, so adding a new player type is one new line.
 */
public class PlayerFactory {

    private final Map<String, MoveStrategy> strategies = new HashMap<>();

    public PlayerFactory(Scanner scanner, Random random) {
        strategies.put("human", new HumanStrategy(scanner));
        strategies.put("computer", new RandomStrategy(random));
    }

    public Player createPlayer(String type, Mark mark) {
        MoveStrategy strategy = strategies.get(type.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown player type: " + type);
        }
        return new Player(mark, strategy);
    }
}
