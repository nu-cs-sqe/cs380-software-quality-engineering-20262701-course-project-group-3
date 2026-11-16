
package domain.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.state.GameState;
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
    void getProgress_NewGame_ReturnsPlaying() {
        GameState gameState = new GameState("Alice", "Bob");

        assertEquals(Progress.PLAYING, gameState.getProgress());
    }

    @Test
    void getMoveHistory_NewGame_ReturnsEmptyList() {
        GameState gameState = new GameState("Alice", "Bob");

        assertTrue(gameState.getMoveHistory().isEmpty());
    }
}