# Tic-Tac-Toe

**Team members:** _your names here_
**Language:** Java 17 (built with Maven, tested with JUnit 5)

A console Tic-Tac-Toe game for two players. Each player can be a **human** (types a cell number 1-9)
or a **computer** (picks a random empty cell).

```
 1 | 2 | 3
---+---+---
 4 | 5 | 6
---+---+---
 7 | 8 | 9
```

## How to run

```bash
mvn compile exec:java   # play the game
mvn test                # run the tests
```

## Classes

| Class | Job |
|---|---|
| `Mark` | Enum for the two symbols, `X` and `O`. |
| `Position` | A row/column spot on the board. |
| `Board` | Holds the 3x3 grid, places marks, and checks for a winner or a full board. |
| `Player` | Has a `Mark` and a `MoveStrategy`. |
| `MoveStrategy` | Interface for choosing a move. |
| `HumanStrategy` | Asks the user to type a cell number. |
| `RandomStrategy` | Computer player that picks a random empty cell. |
| `PlayerFactory` | Turns `"human"` / `"computer"` into a `Player`. |
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
Adding a new player type is one `put(...)` line.

### 4. Command (planned for the final version)
Each move will be wrapped in a `MoveCommand` object with `execute()` and `undo()` methods. `Game`
will keep a stack of past commands so a player can **undo** their last move.

## OO principles

- **Coding to abstractions:** `Player` depends on the `MoveStrategy` interface, and `Game` depends
  on the `GameListener` interface. Neither depends on a concrete class like `HumanStrategy` or
  `ConsoleView`.
- **Polymorphism:** `player.chooseMove(board)` runs either `HumanStrategy` or `RandomStrategy`
  code, depending on which object the player was given. Likewise, `listener.onMoveMade(...)` runs
  whichever listener is registered.
- **Dependency injection:** Objects get what they need through their constructors instead of
  creating it themselves:
  - `Game(board, firstPlayer, secondPlayer)`
  - `Player(mark, strategy)`
  - `HumanStrategy(scanner)` and `RandomStrategy(random)`

  All the objects are created in one place (`Main`). Because of this, the tests can pass in fakes:
  a `Scanner` reading a test string, a `Random` with a fixed seed, and the `ScriptedStrategy` and
  `RecordingListener` test helpers.

## Tests (15 total)

| Test class | What it checks |
|---|---|
| `BoardTest` | Empty board, placing marks, rejecting taken and off-board cells, row, column, and diagonal wins, a draw |
| `GameTest` | Players take turns, and the game stops on a win and tells its listeners |
| `StrategyTest` | Random only picks empty cells, human input is converted to the right cell, bad input asks again, the factory creates players and rejects unknown types |

## Still to do
- Command pattern for undo (above)
- A smarter computer player (one that tries to win, then tries to block)
- Save the win/loss record to a file between runs
