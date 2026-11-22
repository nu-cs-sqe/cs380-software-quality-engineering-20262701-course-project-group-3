package domain.piece;

import domain.Board;
import domain.Location;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private static final int WHITE_START_ROW = 6;
    private static final int BLACK_START_ROW = 1;

    public Pawn(PieceColor color) {
        super(PieceType.PAWN, color);
    }

    /**
     * Returns forward destinations allowed by pawn movement and occupancy.
     * Diagonal captures will be added in a later increment (TC11-TC18).
     * The board is never modified.
     *
     * @param board current board position
     * @param from source square occupied by this pawn
     * @return forward destinations in increasing distance order
     */
    public List<Location> getPseudoLegalDestinations(Board board, Location from) {
        List<Location> destinations = new ArrayList<>();
        int forwardDirection = getColor() == PieceColor.WHITE ? -1 : 1;

        Location oneStepForward =
                new Location(from.getX() + forwardDirection, from.getY());

        if (board.getPiece(oneStepForward) == null) {
            destinations.add(oneStepForward);

            if (isStartingRow(from.getX())) {
                Location twoStepsForward = new Location(
                        from.getX() + 2 * forwardDirection, from.getY());

                if (board.getPiece(twoStepsForward) == null) {
                    destinations.add(twoStepsForward);
                }
            }
        }

        int captureColumn = from.getY() - 1;

        if (captureColumn >= 0) {
            Location leftCapture = new Location(
                    from.getX() + forwardDirection, captureColumn);

            Piece target = board.getPiece(leftCapture);

            if (target != null && target.getColor() != getColor()) {
                destinations.add(leftCapture);
            }
        }

        return destinations;
    }

    private boolean isStartingRow(int row) {
        int startingRow = getColor() == PieceColor.WHITE
                ? WHITE_START_ROW : BLACK_START_ROW;

        return row == startingRow;
    }

    @Override
    public Piece makeCopy() {
        return new Pawn(getColor());
    }
}