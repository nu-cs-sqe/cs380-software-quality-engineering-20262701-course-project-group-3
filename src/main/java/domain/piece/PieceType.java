package domain.piece;

public enum PieceType {
    PAWN("Pawn"),
    ROOK("Rook"),
    KNIGHT("Knight"),
    BISHOP("Bishop"),
    QUEEN("Queen"),
    KING("King");

    private final String label;

    PieceType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
