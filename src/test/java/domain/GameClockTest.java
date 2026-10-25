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

    @Test
    void constructor_ThreeMinutes_InitializesBlitzGame() {
        GameClock clock = new GameClock(3);

        assertEquals(180L, clock.getRemainingSeconds(PieceColor.WHITE));
        assertEquals(180L, clock.getRemainingSeconds(PieceColor.BLACK));
    }

    @Test
    void constructor_TenMinutes_InitializesRapidGame() {
        GameClock clock = new GameClock(10);

        assertEquals(600L, clock.getRemainingSeconds(PieceColor.WHITE));
        assertEquals(600L, clock.getRemainingSeconds(PieceColor.BLACK));
    }

    @Test
    void constructor_SixtyMinutes_InitializesClassicalGame() {
        GameClock clock = new GameClock(60);

        assertEquals(3600L, clock.getRemainingSeconds(PieceColor.WHITE));
        assertEquals(3600L, clock.getRemainingSeconds(PieceColor.BLACK));
    }

}
