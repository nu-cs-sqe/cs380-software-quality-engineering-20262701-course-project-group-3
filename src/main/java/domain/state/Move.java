package domain.state;

import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceType;

public final class Move {
    private final Location from;
    private final Location to;
    private final Piece movedPiece;
    private final Piece capturedPiece;        // null if no capture
    private final PieceType promotedTo;        // null unless a pawn promoted
    private boolean isCastle;
    private boolean isEnPassant;

    public Move(
            Location from,
            Location to,
            Piece movedPiece,
            Piece capturedPiece,
            PieceType promotedTo,
            boolean isCastle,
            boolean isEnPassant
    ) {
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.promotedTo = promotedTo;
        this.isCastle = isCastle;
        this.isEnPassant = isEnPassant;
    }
}