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

    @Test
    void getPseudoLegalDestinations_BlackPawnFirstSquareBlocked_ReturnsNoDestinations() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e7", PieceColor.BLACK);

        board.setPiece(location("e6"), new Knight(PieceColor.WHITE));

        assertDestinations(pawn, board, "e7");
    }

    @Test
    void getPseudoLegalDestinations_BlackPawnSecondSquareBlocked_ReturnsOnlyFirstSquare() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e7", PieceColor.BLACK);

        board.setPiece(location("e5"), new Rook(PieceColor.BLACK));

        assertDestinations(pawn, board, "e7", "e6");
    }

    @Test
    void getPseudoLegalDestinations_WhiteLeftEnemyRightFriendly_IncludesOnlyLeftCapture() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e4", PieceColor.WHITE);
        board.setPiece(location("d5"), new Knight(PieceColor.BLACK));
        board.setPiece(location("f5"), new Bishop(PieceColor.WHITE));

        assertDestinations(pawn, board, "e4", "d5", "e5");
    }

    @Test
    void getPseudoLegalDestinations_WhiteRightEnemyLeftFriendly_IncludesOnlyRightCapture() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e4", PieceColor.WHITE);
        board.setPiece(location("d5"), new Knight(PieceColor.WHITE));
        board.setPiece(location("f5"), new Bishop(PieceColor.BLACK));

        assertDestinations(pawn, board, "e4", "e5", "f5");
    }

    @Test
    void getPseudoLegalDestinations_WhiteBothDiagonalEnemies_IncludesBothCaptures() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e4", PieceColor.WHITE);
        board.setPiece(location("d5"), new Knight(PieceColor.BLACK));
        board.setPiece(location("f5"), new Bishop(PieceColor.BLACK));

        assertDestinations(pawn, board, "e4", "d5", "e5", "f5");
    }

    @Test
    void getPseudoLegalDestinations_BlackLeftEnemyRightFriendly_IncludesOnlyLeftCapture() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e5", PieceColor.BLACK);
        board.setPiece(location("d4"), new Knight(PieceColor.WHITE));
        board.setPiece(location("f4"), new Bishop(PieceColor.BLACK));

        assertDestinations(pawn, board, "e5", "d4", "e4");
    }

    @Test
    void getPseudoLegalDestinations_BlackRightEnemyLeftFriendly_IncludesOnlyRightCapture() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e5", PieceColor.BLACK);
        board.setPiece(location("d4"), new Knight(PieceColor.BLACK));
        board.setPiece(location("f4"), new Bishop(PieceColor.WHITE));

        assertDestinations(pawn, board, "e5", "e4", "f4");
    }

    @Test
    void getPseudoLegalDestinations_ForwardBlockedBothCapturesAvailable_ReturnsOnlyCaptures() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e5", PieceColor.BLACK);
        board.setPiece(location("e4"), new Rook(PieceColor.WHITE));
        board.setPiece(location("d4"), new Knight(PieceColor.WHITE));
        board.setPiece(location("f4"), new Bishop(PieceColor.WHITE));

        assertDestinations(pawn, board, "e5", "d4", "f4");
    }

    @Test
    void getPseudoLegalDestinations_WhiteStartingRankWithTwoCaptures_ReturnsFourMoves() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e2", PieceColor.WHITE);
        board.setPiece(location("d3"), new Knight(PieceColor.BLACK));
        board.setPiece(location("f3"), new Bishop(PieceColor.BLACK));

        assertDestinations(pawn, board, "e2", "d3", "e3", "e4", "f3");
    }

    @Test
    void getPseudoLegalDestinations_BlackStartingRankWithTwoCaptures_ReturnsFourMoves() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "e7", PieceColor.BLACK);
        board.setPiece(location("d6"), new Knight(PieceColor.WHITE));
        board.setPiece(location("f6"), new Bishop(PieceColor.WHITE));

        assertDestinations(pawn, board, "e7", "d6", "e6", "e5", "f6");
    }

    @Test
    void getPseudoLegalDestinations_WhitePawnOnLeftFile_ExcludesOffBoardDiagonal() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "a4", PieceColor.WHITE);
        board.setPiece(location("b5"), new Knight(PieceColor.BLACK));

        assertDestinations(pawn, board, "a4", "a5", "b5");
    }

    @Test
    void getPseudoLegalDestinations_WhitePawnOnRightFile_ExcludesOffBoardDiagonal() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "h4", PieceColor.WHITE);
        board.setPiece(location("g5"), new Knight(PieceColor.BLACK));

        assertDestinations(pawn, board, "h4", "g5", "h5");
    }

    @Test
    void getPseudoLegalDestinations_BlackPawnOnLeftFile_ExcludesOffBoardDiagonal() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "a5", PieceColor.BLACK);
        board.setPiece(location("b4"), new Knight(PieceColor.WHITE));

        assertDestinations(pawn, board, "a5", "a4", "b4");
    }

    @Test
    void getPseudoLegalDestinations_BlackPawnOnRightFile_ExcludesOffBoardDiagonal() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "h5", PieceColor.BLACK);
        board.setPiece(location("g4"), new Knight(PieceColor.WHITE));

        assertDestinations(pawn, board, "h5", "g4", "h4");
    }

    @Test
    void getPseudoLegalDestinations_WhitePawnBeforePromotion_ReturnsFinalRankSquare() {
        Board board = Board.emptyBoard();
        Pawn pawn = placePawn(board, "a7", PieceColor.WHITE);

        assertDestinations(pawn, board, "a7", "a8");
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