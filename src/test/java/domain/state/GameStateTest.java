
package domain.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

class GameStateTest {

    @Test
    void getActivePlayerName_MinimumNonEmptyNames_ReturnsWhitePlayerName() {
        GameState gameState = new GameState("A", "B");

        assertEquals("A", gameState.getActivePlayerName());
    }

    @Test
    void getActivePlayerName_TypicalPlayerNames_ReturnsWhitePlayerName() {
        GameState gameState = new GameState("Alice", "Bob");

        assertEquals("Alice", gameState.getActivePlayerName());
    }

    @Test
    void getActivePlayerName_ReversedPlayerNames_ReturnsWhitePlayerName() {
        GameState gameState = new GameState("Bob", "Alice");

        assertEquals("Bob", gameState.getActivePlayerName());
    }

    @Test
    void getStatus_NewGame_ReturnsPlaying() {
        GameState gameState = new GameState("Alice", "Bob");

        assertEquals(Status.PLAYING, gameState.getStatus());
    }

    @Test
    void getMoveHistory_NewGame_ReturnsEmptyList() {
        GameState gameState = new GameState("Alice", "Bob");

        assertTrue(gameState.getMoveHistory().isEmpty());
    }

    @Test
    void getCapturedPieceCount_NewGame_ReturnsZeroForBothPlayers() {
        GameState gameState = new GameState("Alice", "Bob");

        for (PieceType type : PieceType.values()) {
            assertEquals(0, gameState.getCapturedPieceCount(PieceColor.WHITE, type));
            assertEquals(0, gameState.getCapturedPieceCount(PieceColor.BLACK, type));
        }
    }
}