package domain;

import domain.piece.*;
import org.junit.jupiter.api.Test;

import static domain.piece.PieceColor.BLACK;
import static domain.piece.PieceColor.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void Board() {
        Board board = new Board();
        Piece[][] snapshot = board.getSnapshot();

        // Verify board dimensions
        assertEquals(8, snapshot.length);

        for (Piece[] row : snapshot) {
            assertEquals(8, row.length);
        }

        // Verify Black's pawns
        for (int col = 0; col < 8; col++) {
            assertInstanceOf(Pawn.class, snapshot[1][col]);
            assertEquals(BLACK, snapshot[1][col].getColor());
        }

        // Verify White's pawns
        for (int col = 0; col < 8; col++) {
            assertInstanceOf(Pawn.class, snapshot[6][col]);
            assertEquals(WHITE, snapshot[6][col].getColor());
        }

        // Verify Black's back rank
        assertInstanceOf(Rook.class, snapshot[0][0]);
        assertInstanceOf(Knight.class, snapshot[0][1]);
        assertInstanceOf(Bishop.class, snapshot[0][2]);
        assertInstanceOf(Queen.class, snapshot[0][3]);
        assertInstanceOf(King.class, snapshot[0][4]);
        assertInstanceOf(Bishop.class, snapshot[0][5]);
        assertInstanceOf(Knight.class, snapshot[0][6]);
        assertInstanceOf(Rook.class, snapshot[0][7]);

        // Verify White's back rank
        assertInstanceOf(Rook.class, snapshot[7][0]);
        assertInstanceOf(Knight.class, snapshot[7][1]);
        assertInstanceOf(Bishop.class, snapshot[7][2]);
        assertInstanceOf(Queen.class, snapshot[7][3]);
        assertInstanceOf(King.class, snapshot[7][4]);
        assertInstanceOf(Bishop.class, snapshot[7][5]);
        assertInstanceOf(Knight.class, snapshot[7][6]);
        assertInstanceOf(Rook.class, snapshot[7][7]);

        // Verify back-rank colors
        for (int col = 0; col < 8; col++) {
            assertEquals(BLACK, snapshot[0][col].getColor());
            assertEquals(WHITE, snapshot[7][col].getColor());
        }

        // Verify all remaining squares are empty
        for (int row = 2; row <= 5; row++) {
            for (int col = 0; col < 8; col++) {
                assertNull(snapshot[row][col]);
            }
        }
    }
}
