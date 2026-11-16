
package domain.state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.state.GameState;
import org.junit.jupiter.api.Test;

class GameStateTest {

    @Test
    void whitePlayerStartsFirst() {
        GameState gameState = new GameState("A", "B");

        assertEquals("A", gameState.getActivePlayerName());
    }
}