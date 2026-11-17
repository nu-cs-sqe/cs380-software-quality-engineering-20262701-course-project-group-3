package domain.state;

import domain.Location;

import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.Optional;

public final class Move {
    private final Location from;
    private final Location to;
    private final Piece movedPiece;
    private final Piece capturedPiece;   // null if no capture
    private final PieceType promotion;   // null unless a pawn promoted
    private final MoveType moveType;

    public Move(Location from, Location to, Piece movedPiece,
                Piece capturedPiece, PieceType promotion, MoveType moveType) {
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.promotion = promotion;
        this.moveType = moveType;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public Optional<Piece> capturedPiece() {
        return Optional.ofNullable(capturedPiece);
    }

    public Optional<PieceType> promotion() {
        return Optional.ofNullable(promotion);
    }
}