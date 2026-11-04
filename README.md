# Tic-Tac-Toe

**Team members:** Ben Blair, Ron Jangam
**Language:** Java 17 (builds on JDK 17 or newer; built with Gradle, tested with JUnit 5)

A console Tic-Tac-Toe game for two players. Each player can be a **human** (types a cell number 1-9)
a **computer** (picks a random empty cell), or a **smart** computer (wins if it can, blocks if it must,
otherwise picks at random).

```
 1 | 2 | 3
---+---+---
 4 | 5 | 6
---+---+---
 7 | 8 | 9
```

## How to run

```bash
./gradlew run    # play the game   (Windows: gradlew.bat run)
./gradlew test   # run the tests
```

In IntelliJ you can also open the project and use the Gradle panel: **Tasks → application → run**
and **Tasks → verification → test**.

## Classes

| Class | Job |
|---|---|
| `Mark` | Enum for the two symbols, `X` and `O`. `opponent()` returns the other one. |
| `Position` | A row/column spot on the board. |
| `Board` | Holds the 3x3 grid, places marks (returns `false` if the cell is taken), and checks rows, columns, and diagonals for a winner. Can `copy()` itself. |
| `Player` | Has a `Mark` and a `MoveStrategy`. |
| `MoveStrategy` | Interface for choosing a move. |
| `HumanStrategy` | Asks the user to type a cell number. |
| `RandomStrategy` | Computer player that picks a random empty cell. |
| `StrategyDecorator` | Abstract decorator: is a `MoveStrategy` and wraps another `MoveStrategy`. |
| `WinningMoveDecorator` | Takes a winning move if there is one, otherwise asks the wrapped strategy. |
| `BlockingMoveDecorator` | Blocks the opponent's winning move if needed, otherwise asks the wrapped strategy. |
| `PlayerFactory` | Turns `"human"` / `"computer"` / `"smart"` into a `Player`. |
| `GameListener` | Interface for anything that wants to be told about game events. |
| `ConsoleView` | A `GameListener` that prints the board. |
| `Game` | Runs the turns until someone wins or it's a draw. |
| `Main` | Creates all the objects and connects them, then starts the game. |

## Design patterns

### 1. Strategy (`MoveStrategy`, `HumanStrategy`, `RandomStrategy`)

**Where:** `MoveStrategy` is an interface with one method, `chooseMove(board, mark)`. `HumanStrategy`
and `RandomStrategy` each implement it differently.
**How it's used:** A `Player` holds a `MoveStrategy` and just calls `strategy.chooseMove(...)`. The
player doesn't know or care whether a human or the computer is choosing. Adding a smarter
computer player later means writing one new class, and `Player` and `Game` stay the same.

### 2. Observer (`GameListener`, `ConsoleView`)

**Where:** `GameListener` is an interface with `onMoveMade(...)` and `onGameOver(...)`. `Game` keeps a
list of listeners and calls them after every move and when the game ends.
**How it's used:** `ConsoleView` is a listener that prints the board. `Game` never prints anything
itself, so the game logic is separate from the display. A score tracker or a GUI could be added
as another listener without changing `Game`.

### 3. Factory (`PlayerFactory`)

**Where:** `PlayerFactory.createPlayer(type, mark)` builds a `Player` from a text type like `"human"`.
**How it's used:** `Main` asks the user what kind of player they want and passes the answer to the
factory. The factory stores the strategies in a `Map`, so it uses no `if/else` chain or `switch`.
Adding a new player type is one `put(...)` line. The `"smart"` type is put together here from decorators (see below).

### 4. Decorator (`StrategyDecorator`, `WinningMoveDecorator`, `BlockingMoveDecorator`)

**Where:** `StrategyDecorator` is an abstract class that implements `MoveStrategy` **and** holds a
`MoveStrategy` that it wraps. Each subclass adds one rule. If the rule doesn't apply, it passes the
decision to the strategy it wraps.
**How it's used:** `PlayerFactory` builds the smart computer player by stacking decorators:

```java
new WinningMoveDecorator(new BlockingMoveDecorator(new RandomStrategy(random)))
```

The smart player tries to win first, then to block, then picks at random. Each rule is its own small
class, so there's no growing `if/else` in one "AI" class. Rules can be added, removed, or reordered by
changing how they're wrapped, and `Player` and `Game` don't change at all, because a decorated strategy
is still just a `MoveStrategy` (polymorphism).

## OO principles

- **Coding to abstractions:** `Player` depends on the `MoveStrategy` interface, and `Game` depends
on the `GameListener` interface. Neither depends on a concrete class like `HumanStrategy` or
`ConsoleView`.
- **Polymorphism:** `player.chooseMove(board)` runs either `HumanStrategy` or `RandomStrategy`  
code, depending on which object the plafixyer was given. Likewise, `listener.onMoveMade(...)` runs
whichever listener is registered.
- **Dependency injection:** Objects get what they need through their constructors instead of
creating it themselves:
  - `Game(board, firstPlayer, secondPlayer)`
  - `Player(mark, strategy)`
  - `HumanStrategy(scanner)` and `RandomStrategy(random)`
  - each decorator gets the strategy it wraps, e.g. `WinningMoveDecorator(wrapped)`

  All the objects are created in one place (`Main`). Because of this, the tests can pass in fakes:
  a `Scanner` reading a test string, a `Random` with a fixed seed, and the `ScriptedStrategy` and
  `RecordingListener` test helpers.

## Tests (23 total)

| Test class | What it checks |
|---|---|
| `BoardTest` | Empty board, placing marks, rejecting taken and off-board cells, row, column, and diagonal wins, a draw |
| `GameTest` | Players take turns, and the game stops on a win and tells its listeners |
| `DecoratorTest` | Winning decorator wins, blocking decorator blocks, each passes the move on when its rule doesn't apply, stacked decorators prefer winning to blocking, the factory's smart player blocks, `Board.copy()` is independent, `Mark.opponent()` |
| `StrategyTest` | Random only picks empty cells, human input is converted to the right cell, bad input asks again, the factory creates players and rejects unknown types |

## Test coverage

The tests cover 95% of the methods in the core model classes (38 of 40). The two that aren't covered are
`Position.hashCode()` and `Position.toString()`. `Main` and `ConsoleView` are UI code and
are not counted. To see the numbers, run the tests with coverage in the IDE (IntelliJ: right-click
`src/test/java` → **Run 'All Tests' with Coverage**).
