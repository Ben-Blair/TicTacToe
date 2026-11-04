package tictactoe;

/** A spot on the board. Rows and columns are numbered 0, 1, 2. */
public class Position {

    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /** Two positions are equal if they have the same row and column. */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Position)) {
            return false;
        }
        Position that = (Position) other;
        return row == that.row && col == that.col;
    }

    /** Java's rule: if you write equals, also write hashCode so equal objects get the same number. */
    @Override
    public int hashCode() {
        return row * 3 + col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
