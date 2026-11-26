
package domain.state;

public enum MoveType {
    NORMAL("Normal"),
    CASTLE("Castle"),
    EN_PASSANT("En Passant");

    private final String label;

    MoveType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}