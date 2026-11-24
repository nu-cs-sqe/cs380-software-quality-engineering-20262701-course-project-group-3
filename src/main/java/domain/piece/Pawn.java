package domain.piece;

import domain.Board;
import domain.Location;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private static final int WHITE_START_ROW = 6;
    private static final int BLACK_START_ROW = 1;
    private static final int BOARD_SIZE = 8;

    public Pawn(PieceColor color) {
        super(PieceType.PAWN, color);
    }

    /**
     * Returns pawn destinations allowed by forward movement and diagonal capture rules.
     * King safety, en passant, promotion execution, and board mutation are outside this method.
     *
     * @param board current board position
     * @param from source square occupied by this pawn
     * @return pseudo-legal destinations without changing the board
     */
    public List<Location> getPseudoLegalDestinations(Board board, Location from) {
        int forwardDirection = getColor() == PieceColor.WHITE ? -1 : 1;
        int nextRow = from.getX() + forwardDirection;

        if (nextRow < 0 || nextRow >= BOARD_SIZE) {
            return List.of();
        }

        List<Location> destinations = getForwardDestinations(board, from, forwardDirection);

        addCaptureIfAvailable(board, new Location(nextRow, from.getY() - 1), destinations);
        addCaptureIfAvailable(board, new Location(nextRow, from.getY() + 1), destinations);
        return destinations;
    }

    private List<Location> getForwardDestinations(
            Board board, Location from, int direction) {
        List<Location> destinations = new ArrayList<>();
        Location oneStepForward = new Location(from.getX() + direction, from.getY());

        if (board.getPiece(oneStepForward) == null) {
            destinations.add(oneStepForward);

            if (isStartingRow(from.getX())) {
                Location twoStepsForward =
                        new Location(from.getX() + 2 * direction, from.getY());

                if (board.getPiece(twoStepsForward) == null) {
                    destinations.add(twoStepsForward);
                }
            }
        }

        return destinations;
    }

    private void addCaptureIfAvailable(
            Board board, Location square, List<Location> destinations) {
        if (square.getY() < 0 || square.getY() >= BOARD_SIZE) {
            return;
        }

        Piece occupant = board.getPiece(square);

        if (occupant != null && occupant.getColor() != getColor()) {
            destinations.add(square);
        }
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