package tictactoe;

import java.util.ArrayList;
import java.util.List;

/** A 3x3 tic-tac-toe board. An empty cell holds null. */
public class Board {

    public static final int SIZE = 3;

    private final Mark[][] cells = new Mark[SIZE][SIZE];

    /** Returns the mark at the position, or null if the cell is empty. */
    public Mark getMark(Position position) {
        return cells[position.row()][position.col()];
    }

    public boolean isOnBoard(Position position) {
        return position.row() >= 0 && position.row() < SIZE
                && position.col() >= 0 && position.col() < SIZE;
    }

    public boolean isEmpty(Position position) {
        return getMark(position) == null;
    }

    public void place(Position position, Mark mark) {
        if (!isOnBoard(position) || !isEmpty(position)) {
            throw new IllegalArgumentException("Can't place a mark at " + position);
        }
        cells[position.row()][position.col()] = mark;
    }

    public List<Position> getEmptyPositions() {
        List<Position> empty = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Position position = new Position(row, col);
                if (isEmpty(position)) {
                    empty.add(position);
                }
            }
        }
        return empty;
    }

    public boolean isFull() {
        return getEmptyPositions().isEmpty();
    }

    /** Returns the winning mark, or null if nobody has three in a row yet. */
    public Mark getWinner() {
        for (int i = 0; i < SIZE; i++) {
            Mark rowWinner = lineWinner(cells[i][0], cells[i][1], cells[i][2]);
            if (rowWinner != null) {
                return rowWinner;
            }
            Mark columnWinner = lineWinner(cells[0][i], cells[1][i], cells[2][i]);
            if (columnWinner != null) {
                return columnWinner;
            }
        }
        Mark diagonalWinner = lineWinner(cells[0][0], cells[1][1], cells[2][2]);
        if (diagonalWinner != null) {
            return diagonalWinner;
        }
        return lineWinner(cells[0][2], cells[1][1], cells[2][0]);
    }

    /** If all three cells hold the same mark, return it. Otherwise return null. */
    private Mark lineWinner(Mark a, Mark b, Mark c) {
        if (a != null && a == b && b == c) {
            return a;
        }
        return null;
    }
}
