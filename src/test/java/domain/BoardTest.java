package domain;

import domain.piece.*;
import org.junit.jupiter.api.Test;

import static domain.piece.PieceColor.BLACK;
import static domain.piece.PieceColor.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void BoardTC01_Initialization() {
        Board board = new Board(true);
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

    @Test
    void BoardTC02_EmptyInitialization() {
        Board board = new Board(false);
        Piece[][] snapshot = board.getSnapshot();

        // Verify board dimensions
        assertEquals(8, snapshot.length);

        for (Piece[] row : snapshot) {
            assertEquals(8, row.length);
        }

        // Verify every square is empty
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                assertNull(snapshot[row][col],
                        "Expected square at (" + row + ", " + col + ") to be empty");
            }
        }
    }

    @Test
    void setPieceTC01_InCenter() {
        Board board = new Board(false);
        Location location = new Location(4, 4);
        Pawn pawn = new Pawn(WHITE);

        board.setPiece(location, pawn);

        Piece[][] snapshot = board.getSnapshot();

        assertInstanceOf(Pawn.class, snapshot[4][4]);
        assertEquals(WHITE, snapshot[4][4].getColor());
    }

    @Test
    void setPieceTC02_AtCorner() {
        Board board = new Board(false);
        Location location = new Location(0, 0);
        Rook rook = new Rook(WHITE);

        board.setPiece(location, rook);

        Piece[][] snapshot = board.getSnapshot();

        assertInstanceOf(Rook.class, snapshot[0][0]);
        assertEquals(WHITE, snapshot[0][0].getColor());
    }

    @Test
    void setPieceTC03_AtOppositeCorner() {
        Board board = new Board(false);
        Location location = new Location(7, 7);
        Knight knight = new Knight(BLACK);

        board.setPiece(location, knight);

        Piece[][] snapshot = board.getSnapshot();

        assertInstanceOf(Knight.class, snapshot[7][7]);
        assertEquals(BLACK, snapshot[7][7].getColor());
    }

    @Test
    void setPieceTC04_replaceExistingPiece() {
        Board board = new Board(false);
        Location location = new Location(4, 4);

        Pawn firstPiece = new Pawn(WHITE);
        Rook secondPiece = new Rook(BLACK);

        board.setPiece(location, firstPiece);
        board.setPiece(location, secondPiece);

        Piece[][] snapshot = board.getSnapshot();

        assertInstanceOf(Rook.class, snapshot[4][4]);
        assertEquals(BLACK, snapshot[4][4].getColor());
    }

    @Test
    void getPieceTC01_RetrieveExistingPiece() {
        Board board = new Board(false);
        Location location = new Location(4, 4);
        Pawn pawn = new Pawn(WHITE);

        board.setPiece(location, pawn);

        Piece retrievedPiece = board.getPiece(location);

        assertInstanceOf(Pawn.class, retrievedPiece);
        assertEquals(WHITE, retrievedPiece.getColor());
    }

    @Test
    void getPieceTC02_RetrieveEmptySquare() {
        Board board = new Board(false);
        Location location = new Location(4, 4);

        Piece retrievedPiece = board.getPiece(location);

        assertNull(retrievedPiece);
    }

    @Test
    void getPieceTC03_RetrieveCornerPiece() {
        Board board = new Board(false);
        Location location = new Location(0, 0);
        Rook rook = new Rook(WHITE);

        board.setPiece(location, rook);

        Piece retrievedPiece = board.getPiece(location);

        assertInstanceOf(Rook.class, retrievedPiece);
        assertEquals(WHITE, retrievedPiece.getColor());
    }

    @Test
    void getPieceTC04_RetrieveOppositeCornerPiece() {
        Board board = new Board(false);
        Location location = new Location(7, 7);
        Knight knight = new Knight(BLACK);

        board.setPiece(location, knight);

        Piece retrievedPiece = board.getPiece(location);

        assertInstanceOf(Knight.class, retrievedPiece);
        assertEquals(BLACK, retrievedPiece.getColor());
    }

    @Test
    void getPieceTC05_RetrievePieceAfterReplacement() {
        Board board = new Board(false);
        Location location = new Location(4, 4);

        Pawn firstPiece = new Pawn(WHITE);
        Rook secondPiece = new Rook(BLACK);

        board.setPiece(location, firstPiece);
        board.setPiece(location, secondPiece);

        Piece retrievedPiece = board.getPiece(location);

        assertInstanceOf(Rook.class, retrievedPiece);
        assertEquals(BLACK, retrievedPiece.getColor());
    }

    @Test
    void getSnapshotTC01_StandardBoard() {
        Board board = new Board(true);

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

    @Test
    void getSnapshotTC02_EmptyBoard() {
        Board board = new Board(false);

        Piece[][] snapshot = board.getSnapshot();

        // Verify board dimensions
        assertEquals(8, snapshot.length);

        for (Piece[] row : snapshot) {
            assertEquals(8, row.length);
        }

        // Verify every square is empty
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                assertNull(snapshot[row][col],
                        "Expected square at (" + row + ", " + col + ") to be empty");
            }
        }
    }

    @Test
    void getSnapshotTC03_SnapshotIndependence() {
        Board board = new Board(false);
        Location location = new Location(4, 4);

        Pawn pawn = new Pawn(WHITE);
        board.setPiece(location, pawn);

        // Get a snapshot and modify the returned array
        Piece[][] snapshot = board.getSnapshot();
        snapshot[4][4] = new Rook(BLACK);

        // Verify the original board remains unchanged
        Piece originalPiece = board.getPiece(location);

        assertInstanceOf(Pawn.class, originalPiece);
        assertEquals(WHITE, originalPiece.getColor());
    }

    @Test
    void getSnapshotTC04_SnapshotPieceIndependence() {
        Board board = new Board(false);
        Location location = new Location(4, 4);
        Pawn pawn = new Pawn(WHITE);

        board.setPiece(location, pawn);

        // Get a snapshot of the board
        Piece[][] snapshot = board.getSnapshot();

        // Verify the snapshot contains a copy, not the original object
        Piece snapshotPiece = snapshot[4][4];
        Piece originalPiece = board.getPiece(location);

        assertNotSame(originalPiece, snapshotPiece);
        assertInstanceOf(Pawn.class, snapshotPiece);
        assertEquals(WHITE, snapshotPiece.getColor());
    }
}
