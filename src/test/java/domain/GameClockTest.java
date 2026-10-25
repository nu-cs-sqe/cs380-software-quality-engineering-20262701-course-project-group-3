package domain;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import domain.piece.PieceColor;


public class GameClockTest {

    @Test
    void constructor_NegativeMinutes_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new GameClock(-1));
    }

    @Test
    void constructor_ZeroMinutes_InitializesUntimedGame() {
        GameClock clock = new GameClock(0);

        assertFalse(clock.isTimed());
        assertEquals(-1L, clock.getRemainingSeconds(PieceColor.WHITE));
        assertEquals(-1L, clock.getRemainingSeconds(PieceColor.BLACK));
    }


}
