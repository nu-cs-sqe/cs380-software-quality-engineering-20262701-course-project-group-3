package domain.piece;

public enum PieceColor {
    BLACK("Black"),
    WHITE("White");

    private final String label;

    PieceColor(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
