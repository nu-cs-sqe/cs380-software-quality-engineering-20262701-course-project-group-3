
package domain.state;

import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Pawn;
import domain.piece.King;

import java.util.Optional;

public final class Move {
    private final Location from;
    private final Location to;
    private final Piece movedPiece;
    private final Piece capturedPiece;
    private final PieceType promotion;
    private final MoveType moveType;
    private final RookMovement rookMovement;

    public Move(Location from, Location to, Piece movedPiece,
                Piece capturedPiece, PieceType promotion, MoveType moveType) {
        this(from, to, movedPiece, capturedPiece, promotion, moveType, null);
    }

    public Move(Location from, Location to, Piece movedPiece,
                Piece capturedPiece, PieceType promotion, MoveType moveType,
                RookMovement rookMovement) {
        if (moveType == null) {
            throw new IllegalArgumentException("Move type cannot be null");
        }
        if (promotion != null && !(movedPiece instanceof Pawn)) {
            throw new IllegalArgumentException("Only pawns can promote");
        }
        if (promotion == PieceType.KING || promotion == PieceType.PAWN) {
            throw new IllegalArgumentException("Invalid promotion piece");
        }
        if (moveType == MoveType.CASTLE && promotion != null) {
            throw new IllegalArgumentException("Castling cannot include promotion");
        }
        if (moveType == MoveType.CASTLE && capturedPiece != null) {
            throw new IllegalArgumentException("Castling cannot capture pieces");
        }
        if (moveType == MoveType.EN_PASSANT && !(capturedPiece instanceof Pawn)) {
            throw new IllegalArgumentException("En passant requires a captured pawn");
        }
        if (moveType == MoveType.EN_PASSANT && !(movedPiece instanceof Pawn)) {
            throw new IllegalArgumentException("Only pawns can perform en passant");
        }
        if (moveType == MoveType.CASTLE && !(movedPiece instanceof King)) {
            throw new IllegalArgumentException("Only kings can castle");
        }
        if (moveType == MoveType.EN_PASSANT && promotion != null) {
            throw new IllegalArgumentException("En passant cannot include promotion");
        }

        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.promotion = promotion;
        this.moveType = moveType;
        this.rookMovement = rookMovement;
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

    public Optional<RookMovement> getRookMovement() {
        return Optional.ofNullable(rookMovement);
    }
}