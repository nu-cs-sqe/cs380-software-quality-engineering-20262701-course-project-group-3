
package domain.state;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}