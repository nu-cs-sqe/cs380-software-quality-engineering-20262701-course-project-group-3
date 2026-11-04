package domain.state;

import domain.Location;

import domain.piece.Piece;
import domain.piece.PieceType;

public final class Move {
    private final Location from;
    private final Location to;
    private final Piece movedPiece;
    private final Piece capturedPiece;   // null if no capture
    private final PieceType promotion;   // null unless a pawn promoted
    private final boolean castle;
    private final boolean enPassant;

    public Move(Location from, Location to, Piece movedPiece, Piece capturedPiece,
                PieceType promotion, boolean castle, boolean enPassant) {
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.promotion = promotion;
        this.castle = castle;
        this.enPassant = enPassant;
    }
}