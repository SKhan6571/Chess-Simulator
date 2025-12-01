package pieces;

public enum Color {
    White,
    Black;

    public static Color fromMoveCount(int moveCount) {
        return (moveCount % 2 != 0) ? White : Black;
    }
}
