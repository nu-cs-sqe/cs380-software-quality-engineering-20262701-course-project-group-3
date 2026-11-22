package domain.piece;
import domain.Board;
import domain.Location;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(PieceColor color) {
        super(PieceType.PAWN, color);
    }

    @Override
    public Piece makeCopy() {
        return new Pawn(getColor());
    }

    public List<Location> getPseudoLegalDestinations(Board board, Location from) {
        int direction = getColor() == PieceColor.WHITE ? -1 : 1;

        return List.of(new Location(from.getX() + direction, from.getY()));
    }
}
