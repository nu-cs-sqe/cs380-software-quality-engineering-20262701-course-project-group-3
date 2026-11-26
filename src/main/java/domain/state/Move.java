
package domain.state;

import domain.Location;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.Objects;
import java.util.Optional;

public final class Move {
    private final Location from;
    private final Location to;
    private final Piece movedPiece;
    private final Piece capturedPiece;
    private final PieceType promotion;
    private final MoveType moveType;
    private final RookMovement rookMovement;
    private final Location capturedLocation;

    public Move(Location from, Location to, Piece movedPiece,
                Piece capturedPiece, PieceType promotion, MoveType moveType) {
        this(from, to, movedPiece, capturedPiece, promotion, moveType, null, null);
    }

    public Move(Location from, Location to, Piece movedPiece,
                Piece capturedPiece, PieceType promotion, MoveType moveType,
                RookMovement rookMovement) {
        this(from, to, movedPiece, capturedPiece, promotion,
                moveType, rookMovement, null);
    }

    public Move(Location from, Location to, Piece movedPiece,
                Piece capturedPiece, PieceType promotion, MoveType moveType,
                RookMovement rookMovement, Location capturedLocation) {
        if (moveType == null) {
            throw new IllegalArgumentException("Move type cannot be null");
        }

        validatePromotion(movedPiece, promotion);
        validateSpecialMove(moveType, movedPiece, capturedPiece, promotion);

        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.promotion = promotion;
        this.moveType = moveType;
        this.rookMovement = rookMovement;
        this.capturedLocation = capturedLocation;
    }

    private static void validatePromotion(Piece movedPiece, PieceType promotion) {
        if (promotion == null) {
            return;
        }
        if (!(movedPiece instanceof Pawn)) {
            throw new IllegalArgumentException("Only pawns can promote");
        }
        if (promotion == PieceType.KING || promotion == PieceType.PAWN) {
            throw new IllegalArgumentException("Invalid promotion piece");
        }
    }

    private static void validateSpecialMove(
            MoveType moveType, Piece movedPiece, Piece capturedPiece,
            PieceType promotion) {
        switch (moveType) {
            case NORMAL:
                break;
            case CASTLE:
                if (promotion != null) {
                    throw new IllegalArgumentException("Castling cannot include promotion");
                }
                if (capturedPiece != null) {
                    throw new IllegalArgumentException("Castling cannot capture pieces");
                }
                if (!(movedPiece instanceof King)) {
                    throw new IllegalArgumentException("Only kings can castle");
                }
                break;
            case EN_PASSANT:
                if (!(capturedPiece instanceof Pawn)) {
                    throw new IllegalArgumentException("En passant requires a captured pawn");
                }
                if (!(movedPiece instanceof Pawn)) {
                    throw new IllegalArgumentException("Only pawns can perform en passant");
                }
                if (promotion != null) {
                    throw new IllegalArgumentException("En passant cannot include promotion");
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported move type");
        }
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public Piece getMovedPiece() {
        return movedPiece;
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

    public Optional<Location> getCapturedLocation() {
        return Optional.ofNullable(capturedLocation);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Move other = (Move) obj;

        return from.equals(other.from)
                && to.equals(other.to)
                && movedPiece.equals(other.movedPiece)
                && Objects.equals(capturedPiece, other.capturedPiece)
                && promotion == other.promotion
                && moveType == other.moveType
                && Objects.equals(rookMovement, other.rookMovement)
                && Objects.equals(capturedLocation, other.capturedLocation);
    }

    @Override
    public String toString() {
        return String.format(
                "Move{from=%s, to=%s, movedPiece=%s, capturedPiece=%s, promotion=%s, "
                        + "moveType=%s, rookMovement=%s, capturedLocation=%s}",
                from, to, movedPiece, capturedPiece, promotion,
                moveType, rookMovement, capturedLocation);
    }
}
