package domain.piece;
import domain.Board;
import domain.Location;
import java.util.List;
import java.util.ArrayList;

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
        List<Location> destinations = new ArrayList<>();

        Location firstSquare = new Location(from.getX() + direction, from.getY());

        if (board.getPiece(firstSquare) != null) {
            return destinations;
        }

        destinations.add(firstSquare);
        int startingRow = getColor() == PieceColor.WHITE ? 6 : 1;

        if (from.getX() == startingRow) {
            Location secondSquare =
                    new Location(from.getX() + 2 * direction, from.getY());

            if (board.getPiece(secondSquare) == null) {
                destinations.add(secondSquare);
            }
        }

        return destinations;
    }
}
