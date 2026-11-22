package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import domain.Board;
import domain.Location;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class PawnTest {

    @Test
    void getPseudoLegalDestinations_WhitePawnOffStartingRank_ReturnsOneForwardSquare() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e4", PieceColor.WHITE);

        assertDestinations(pawn, board, "e4", "e5");
    }

    @Test
    void getPseudoLegalDestinations_BlackPawnOffStartingRank_ReturnsOneForwardSquare() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e5", PieceColor.BLACK);

        assertDestinations(pawn, board, "e5", "e4");
    }

    @Test
    void getPseudoLegalDestinations_WhitePawnOnStartingRank_ReturnsTwoForwardSquares() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e2", PieceColor.WHITE);

        assertDestinations(pawn, board, "e2", "e3", "e4");
    }

    @Test
    void getPseudoLegalDestinations_BlackPawnOnStartingRank_ReturnsTwoForwardSquares() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e7", PieceColor.BLACK);

        assertDestinations(pawn, board, "e7", "e6", "e5");
    }

    @Test
    void getPseudoLegalDestinations_WhitePawnPastStartingRank_ReturnsOneForwardSquare() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e3", PieceColor.WHITE);

        assertDestinations(pawn, board, "e3", "e4");
    }

    @Test
    void getPseudoLegalDestinations_BlackPawnPastStartingRank_ReturnsOneForwardSquare() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e6", PieceColor.BLACK);

        assertDestinations(pawn, board, "e6", "e5");
    }

    @Test
    void getPseudoLegalDestinations_WhitePawnFirstSquareBlocked_ReturnsNoDestinations() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e2", PieceColor.WHITE);

        board.setPiece(location("e3"), new Knight(PieceColor.WHITE));

        assertDestinations(pawn, board, "e2");
    }

    @Test
    void getPseudoLegalDestinations_WhitePawnSecondSquareBlocked_ReturnsOnlyFirstSquare() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e2", PieceColor.WHITE);

        board.setPiece(location("e4"), new Rook(PieceColor.BLACK));

        assertDestinations(pawn, board, "e2", "e3");
    }

    private static Pawn placePawn(Board board, String square, PieceColor color) {
        Pawn pawn = new Pawn(color);
        board.setPiece(location(square), pawn);
        return pawn;
    }

    private static Location location(String square) {
        int column = square.charAt(0) - 'a';
        int row = 8 - Character.digit(square.charAt(1), 10);
        return new Location(row, column);
    }

    private static void assertDestinations(
            Pawn pawn, Board board, String from, String... expectedSquares) {

        List<Location> destinations =
                pawn.getPseudoLegalDestinations(board, location(from));
        assertNotNull(destinations);

        Set<String> actualSquares = new HashSet<>();
        for (Location destination : destinations) {
            String square = "" + (char) ('a' + destination.getY())
                    + (8 - destination.getX());
            actualSquares.add(square);
        }

        assertEquals(expectedSquares.length, destinations.size());
        assertEquals(Set.of(expectedSquares), actualSquares);
    }
}