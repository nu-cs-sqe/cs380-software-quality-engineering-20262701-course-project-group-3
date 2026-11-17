
package domain.state;

import domain.Location;
import domain.piece.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {

    @Test
    void getMoveType_NormalMove_ReturnsNormal() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        Move move = new Move(from, to, pawn, null, null, MoveType.NORMAL);

        assertEquals(MoveType.NORMAL, move.getMoveType());
    }

    @Test
    void getMoveType_CastlingMove_ReturnsCastle() {
        Location from = new Location(7, 4);
        Location to = new Location(7, 6);
        Piece king = new King(PieceColor.WHITE);

        Move move = new Move(from, to, king, null, null, MoveType.CASTLE);

        assertEquals(MoveType.CASTLE, move.getMoveType());
    }

    @Test
    void getMoveType_EnPassantMove_ReturnsEnPassant() {
        Location from = new Location(3, 4);
        Location to = new Location(2, 5);
        Piece whitePawn = new Pawn(PieceColor.WHITE);
        Piece blackPawn = new Pawn(PieceColor.BLACK);

        Move move = new Move(from, to, whitePawn, blackPawn, null, MoveType.EN_PASSANT);

        assertEquals(MoveType.EN_PASSANT, move.getMoveType());
    }

    @Test
    void capturedPiece_NoCapture_ReturnsEmptyOptional() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        Move move = new Move(from, to, pawn, null, null, MoveType.NORMAL);

        assertTrue(move.capturedPiece().isEmpty());
    }

    @Test
    void capturedPiece_ValidCapture_ReturnsCapturedPiece() {
        Location from = new Location(3, 4);
        Location to = new Location(2, 5);
        Piece whitePawn = new Pawn(PieceColor.WHITE);
        Piece blackPawn = new Pawn(PieceColor.BLACK);

        Move move = new Move(from, to, whitePawn, blackPawn, null, MoveType.NORMAL);

        assertTrue(move.capturedPiece().isPresent());
        assertSame(blackPawn, move.capturedPiece().get());
    }

    @Test
    void promotion_NoPromotion_ReturnsEmptyOptional() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        Move move = new Move(from, to, pawn, null, null, MoveType.NORMAL);

        assertTrue(move.promotion().isEmpty());
    }

    @Test
    void promotion_PawnPromotesToQueen_ReturnsQueen() {
        Location from = new Location(1, 4);
        Location to = new Location(0, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        Move move = new Move(from, to, pawn, null, PieceType.QUEEN, MoveType.NORMAL);

        assertTrue(move.promotion().isPresent());
        assertEquals(PieceType.QUEEN, move.promotion().get());
    }

    @Test
    void constructor_KingPromotion_ThrowsIllegalArgumentException() {
        Location from = new Location(7, 4);
        Location to = new Location(6, 4);
        Piece king = new King(PieceColor.WHITE);

        assertThrows(IllegalArgumentException.class,
                () -> new Move(from, to, king, null, PieceType.QUEEN, MoveType.NORMAL));
    }

    @Test
    void constructor_PawnPromotesToKing_ThrowsIllegalArgumentException() {
        Location from = new Location(1, 4);
        Location to = new Location(0, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        assertThrows(IllegalArgumentException.class,
                () -> new Move(from, to, pawn, null, PieceType.KING, MoveType.NORMAL));
    }

    @Test
    void constructor_CastlingWithPromotion_ThrowsIllegalArgumentException() {
        Location from = new Location(1, 4);
        Location to = new Location(0, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        assertThrows(IllegalArgumentException.class,
                () -> new Move(from, to, pawn, null, PieceType.QUEEN, MoveType.CASTLE));
    }

    @Test
    void constructor_CastlingWithCapture_ThrowsIllegalArgumentException() {
        Location from = new Location(7, 4);
        Location to = new Location(7, 6);
        Piece king = new King(PieceColor.WHITE);
        Piece blackPawn = new Pawn(PieceColor.BLACK);

        assertThrows(IllegalArgumentException.class,
                () -> new Move(from, to, king, blackPawn, null, MoveType.CASTLE));
    }

    @Test
    void constructor_EnPassantWithoutCapture_ThrowsIllegalArgumentException() {
        Location from = new Location(3, 4);
        Location to = new Location(2, 5);
        Piece pawn = new Pawn(PieceColor.WHITE);

        assertThrows(IllegalArgumentException.class,
                () -> new Move(from, to, pawn, null, null, MoveType.EN_PASSANT));
    }

    @Test
    void constructor_EnPassantCapturesNonPawn_ThrowsIllegalArgumentException() {
        Location from = new Location(3, 4);
        Location to = new Location(2, 5);
        Piece whitePawn = new Pawn(PieceColor.WHITE);
        Piece blackKnight = new Knight(PieceColor.BLACK);

        assertThrows(IllegalArgumentException.class,
                () -> new Move(from, to, whitePawn, blackKnight, null, MoveType.EN_PASSANT));
    }

    @Test
    void constructor_EnPassantWithNonPawnMover_ThrowsIllegalArgumentException() {
        Location from = new Location(3, 4);
        Location to = new Location(2, 5);
        Piece whiteKing = new King(PieceColor.WHITE);
        Piece blackPawn = new Pawn(PieceColor.BLACK);

        assertThrows(IllegalArgumentException.class,
                () -> new Move(from, to, whiteKing, blackPawn, null, MoveType.EN_PASSANT));
    }

    @Test
    void constructor_CastlingWithNonKingMover_ThrowsIllegalArgumentException() {
        Location from = new Location(7, 7);
        Location to = new Location(7, 5);
        Piece rook = new Rook(PieceColor.WHITE);

        assertThrows(IllegalArgumentException.class,
                () -> new Move(from, to, rook, null, null, MoveType.CASTLE));
    }

    @Test
    void constructor_EnPassantWithPromotion_ThrowsIllegalArgumentException() {
        Location from = new Location(3, 4);
        Location to = new Location(2, 5);
        Piece whitePawn = new Pawn(PieceColor.WHITE);
        Piece blackPawn = new Pawn(PieceColor.BLACK);

        assertThrows(IllegalArgumentException.class,
                () -> new Move(from, to, whitePawn, blackPawn,
                        PieceType.QUEEN, MoveType.EN_PASSANT));
    }

    @Test
    void constructor_NullMoveType_ThrowsIllegalArgumentException() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        assertThrows(IllegalArgumentException.class,
                () -> new Move(from, to, pawn, null, null, null));
    }

    @Test
    void constructor_PawnPromotesToPawn_ThrowsIllegalArgumentException() {
        Location from = new Location(1, 4);
        Location to = new Location(0, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        assertThrows(IllegalArgumentException.class,
                () -> new Move(from, to, pawn, null, PieceType.PAWN, MoveType.NORMAL));
    }

    @Test
    void getRookMovement_CastlingMove_ReturnsRookLocations() {
        Location from = new Location(7, 4);
        Location to = new Location(7, 6);
        Piece king = new King(PieceColor.WHITE);

        Location rookFrom = new Location(7, 7);
        Location rookTo = new Location(7, 5);
        RookMovement rookMovement = new RookMovement(rookFrom, rookTo);

        Move move = new Move(from, to, king, null, null,
                MoveType.CASTLE, rookMovement);

        assertTrue(move.getRookMovement().isPresent());
        assertSame(rookFrom, move.getRookMovement().get().getFrom());
        assertSame(rookTo, move.getRookMovement().get().getTo());
    }

    @Test
    void getCapturedLocation_EnPassantMove_ReturnsCapturedPawnLocation() {
        Location from = new Location(3, 4);
        Location to = new Location(2, 5);
        Location capturedLocation = new Location(3, 5);
        Piece whitePawn = new Pawn(PieceColor.WHITE);
        Piece blackPawn = new Pawn(PieceColor.BLACK);

        Move move = new Move(from, to, whitePawn, blackPawn,
                null, MoveType.EN_PASSANT, null, capturedLocation);

        assertTrue(move.getCapturedLocation().isPresent());
        assertSame(capturedLocation, move.getCapturedLocation().get());
    }
}
