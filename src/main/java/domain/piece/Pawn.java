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
        return List.of(new Location(from.getX() - 1, from.getY()));
    }
}
