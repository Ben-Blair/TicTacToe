package tictactoe;

import java.util.ArrayList;

/** A 3x3 tic-tac-toe board. An empty cell holds null. */
public class Board {

    public static final int SIZE = 3;

    private Mark[][] cells = new Mark[SIZE][SIZE];

    /** Returns the mark at the position, or null if the cell is empty. */
    public Mark getMark(Position position) {
        return cells[position.getRow()][position.getCol()];
    }

    public boolean isOnBoard(Position position) {
        return position.getRow() >= 0 && position.getRow() < SIZE
                && position.getCol() >= 0 && position.getCol() < SIZE;
    }

    public boolean isEmpty(Position position) {
        return getMark(position) == null;
    }

    /** Puts the mark on the board. Returns false (and changes nothing) if the position is missing, taken, or off the board. */
    public boolean place(Position position, Mark mark) {
        if (position == null || !isOnBoard(position) || !isEmpty(position)) {
            return false;
        }
        cells[position.getRow()][position.getCol()] = mark;
        return true;
    }

    public ArrayList<Position> getEmptyPositions() {
        ArrayList<Position> empty = new ArrayList<>();
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
        return getEmptyPositions().size() == 0;
    }

    /** Returns a new board with the same marks. Changing the copy does not change this board. */
    public Board copy() {
        Board copy = new Board();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                copy.cells[row][col] = cells[row][col];
            }
        }
        return copy;
    }

    /** Returns the winning mark, or null if nobody has three in a row yet. */
    public Mark getWinner() {
        for (int i = 0; i < SIZE; i++) {
            if (sameMark(cells[i][0], cells[i][1], cells[i][2])) {   // row i
                return cells[i][0];
            }
            if (sameMark(cells[0][i], cells[1][i], cells[2][i])) {   // column i
                return cells[0][i];
            }
        }
        if (sameMark(cells[0][0], cells[1][1], cells[2][2])) {       // diagonal from top-left
            return cells[1][1];
        }
        if (sameMark(cells[0][2], cells[1][1], cells[2][0])) {       // diagonal from top-right
            return cells[1][1];
        }
        return null;
    }

    /** True if all three cells hold the same mark (and aren't empty). */
    private boolean sameMark(Mark a, Mark b, Mark c) {
        return a != null && a == b && b == c;
    }
}
