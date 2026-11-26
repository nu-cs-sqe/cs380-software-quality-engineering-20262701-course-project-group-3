
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
    void constructor_NullFromLocation_ThrowsIllegalArgumentException() {
        Location from = new Location(6, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        assertThrows(IllegalArgumentException.class, () -> {
            new Move(from, null, pawn, null, null, MoveType.NORMAL);
        });
    }

    @Test
    void constructor_NullToLocation_ThrowsIllegalArgumentException() {
        Location to = new Location(5, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        assertThrows(IllegalArgumentException.class, () -> {
            new Move(null, to, pawn, null, null, MoveType.NORMAL);
        });
    }

    @Test
    void constructor_NullMovedPiece_ThrowsIllegalArgumentException() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);

        assertThrows(IllegalArgumentException.class, () -> {
            new Move(from, to, null, null, null, MoveType.NORMAL);
        });
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

    @Test
    void getFrom_ValidFromLocation() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        Move move = new Move(from, to, pawn, null, null, MoveType.NORMAL);

        Location expectedFromLocation = new Location(6, 4);
        assertEquals(expectedFromLocation, move.getFrom());
    }

    @Test
    void getFrom_ValidToLocation() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        Move move = new Move(from, to, pawn, null, null, MoveType.NORMAL);

        Location expectedToLocation = new Location(5, 4);
        assertEquals(expectedToLocation, move.getTo());
    }

    @Test
    void getMovedPiece_Pawn() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        Move move = new Move(from, to, pawn, null, null, MoveType.NORMAL);

        Piece expectedMovedPiece = new Pawn(PieceColor.WHITE);
        assertEquals(expectedMovedPiece, move.getMovedPiece());
    }

    @Test
    void getMovedPiece_Rook() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece rook = new Rook(PieceColor.WHITE);

        Move move = new Move(from, to, rook, null, null, MoveType.NORMAL);

        Piece expectedMovedPiece = new Rook(PieceColor.WHITE);
        assertEquals(expectedMovedPiece, move.getMovedPiece());
    }

    @Test
    void getMovedPiece_Knight() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece knight = new Knight(PieceColor.WHITE);

        Move move = new Move(from, to, knight, null, null, MoveType.NORMAL);

        Piece expectedMovedPiece = new Knight(PieceColor.WHITE);
        assertEquals(expectedMovedPiece, move.getMovedPiece());
    }

    @Test
    void getMovedPiece_Bishop() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece bishop = new Bishop(PieceColor.WHITE);

        Move move = new Move(from, to, bishop, null, null, MoveType.NORMAL);

        Piece expectedMovedPiece = new Bishop(PieceColor.WHITE);
        assertEquals(expectedMovedPiece, move.getMovedPiece());
    }

    @Test
    void getMovedPiece_Queen() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece queen = new Queen(PieceColor.WHITE);

        Move move = new Move(from, to, queen, null, null, MoveType.NORMAL);

        Piece expectedMovedPiece = new Queen(PieceColor.WHITE);
        assertEquals(expectedMovedPiece, move.getMovedPiece());
    }

    @Test
    void getMovedPiece_King() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece king = new King(PieceColor.WHITE);

        Move move = new Move(from, to, king, null, null, MoveType.NORMAL);

        Piece expectedMovedPiece = new King(PieceColor.WHITE);
        assertEquals(expectedMovedPiece, move.getMovedPiece());
    }

    @Test
    void equals_EqualNormalMoves() {
        Location firstFrom = new Location(6, 4);
        Location firstTo = new Location(5, 4);
        Piece firstPiece = new Pawn(PieceColor.WHITE);
        Move firstMove = new Move(firstFrom, firstTo, firstPiece, null, null, MoveType.NORMAL);

        Location secondFrom = new Location(6, 4);
        Location secondTo = new Location(5, 4);
        Piece secondPiece = new Pawn(PieceColor.WHITE);
        Move secondMove = new Move(secondFrom, secondTo, secondPiece, null, null, MoveType.NORMAL);

        assertEquals(firstMove, secondMove);
    }

    @Test
    void equals_EqualPromotionMoves() {
        Location firstFrom = new Location(1, 4);
        Location firstTo = new Location(0, 4);
        Piece firstPawn = new Pawn(PieceColor.WHITE);
        Move firstMove = new Move(firstFrom, firstTo, firstPawn, null, PieceType.QUEEN, MoveType.NORMAL);

        Location secondFrom = new Location(1, 4);
        Location secondTo = new Location(0, 4);
        Piece secondPawn = new Pawn(PieceColor.WHITE);
        Move secondMove = new Move(secondFrom, secondTo, secondPawn, null, PieceType.QUEEN, MoveType.NORMAL);

        assertEquals(firstMove, secondMove);
    }

    @Test
    void equals_EqualCastlingMoves() {
        Location firstFrom = new Location(6, 4);
        Location firstTo = new Location(5, 4);
        Piece firstKing = new King(PieceColor.WHITE);
        Move firstMove = new Move(firstFrom, firstTo, firstKing, null, null, MoveType.CASTLE);

        Location secondFrom = new Location(6, 4);
        Location secondTo = new Location(5, 4);
        Piece secondKing = new King(PieceColor.WHITE);
        Move secondMove = new Move(secondFrom, secondTo, secondKing, null, null, MoveType.CASTLE);

        assertEquals(firstMove, secondMove);
    }

    @Test
    void equals_EqualEnPassantMoves() {
        Location firstFrom = new Location(3, 4);
        Location firstTo = new Location(2, 5);
        Piece firstWhitePawn = new Pawn(PieceColor.WHITE);
        Piece firstBlackPawn = new Pawn(PieceColor.BLACK);
        Move firstMove = new Move(firstFrom, firstTo, firstWhitePawn, firstBlackPawn, null, MoveType.EN_PASSANT);

        Location secondFrom = new Location(3, 4);
        Location secondTo = new Location(2, 5);
        Piece secondWhitePawn = new Pawn(PieceColor.WHITE);
        Piece secondBlackPawn = new Pawn(PieceColor.BLACK);
        Move secondMove = new Move(secondFrom, secondTo, secondWhitePawn, secondBlackPawn, null, MoveType.EN_PASSANT);

        assertEquals(firstMove, secondMove);
    }

    @Test
    void equals_UnequalMoves() {
        Location firstFrom = new Location(6, 4);
        Location firstTo = new Location(5, 4);
        Piece firstPiece = new Pawn(PieceColor.WHITE);
        Move firstMove = new Move(firstFrom, firstTo, firstPiece, null, null, MoveType.NORMAL);

        Location secondFrom = new Location(4, 6);
        Location secondTo = new Location(4, 5);
        Piece secondPiece = new Pawn(PieceColor.WHITE);
        Move secondMove = new Move(secondFrom, secondTo, secondPiece, null, null, MoveType.NORMAL);

        assertNotEquals(firstMove, secondMove);
    }
}
