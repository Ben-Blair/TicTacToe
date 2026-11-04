package tictactoe;

/** The two symbols a player can put on the board. */
public enum Mark {
    X,
    O;

    /** The other player's mark: X gives O, and O gives X. */
    public Mark opponent() {
        if (this == X) {
            return O;
        }
        return X;
    }
}
