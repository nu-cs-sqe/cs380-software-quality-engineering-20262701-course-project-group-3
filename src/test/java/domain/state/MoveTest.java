
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
        Piece pawn = new Pawn(PieceColor.WHITE);

        Move move = new Move(from, to, pawn, null, null, MoveType.EN_PASSANT);

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
}
