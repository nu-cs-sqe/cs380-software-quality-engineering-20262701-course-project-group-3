package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import domain.piece.PieceColor;
import java.util.concurrent.atomic.AtomicLong;

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

    @Test
    void constructor_MaxInteger_InitializesWithoutOverflow() {
        GameClock clock = new GameClock(Integer.MAX_VALUE);
        long expectedSeconds = (long) Integer.MAX_VALUE * 60;

        assertEquals(expectedSeconds, clock.getRemainingSeconds(PieceColor.WHITE));
        assertEquals(expectedSeconds, clock.getRemainingSeconds(PieceColor.BLACK));
    }

    @Test
    void isTimed_UntimedGame_ReturnsFalse() {
        GameClock clock = new GameClock(0);

        assertFalse(clock.isTimed());
    }

    @Test
    void isTimed_MinimumPositiveTime_ReturnsTrue() {
        GameClock clock = new GameClock(1);

        assertTrue(clock.isTimed());
    }

    @Test
    void isTimed_BlitzGame_ReturnsTrue() {
        GameClock clock = new GameClock(3);

        assertTrue(clock.isTimed());
    }

    @Test
    void isTimed_ClassicalGame_ReturnsTrue() {
        GameClock clock = new GameClock(60);

        assertTrue(clock.isTimed());
    }

    @Test
    void constructor_OneMinute_InitializesBothPlayersWith60Seconds() {
        GameClock clock = new GameClock(1);

        assertEquals(60L, clock.getRemainingSeconds(PieceColor.WHITE));
        assertEquals(60L, clock.getRemainingSeconds(PieceColor.BLACK));
    }

    @Test
    void getRemainingSeconds_NullColor_ThrowsIllegalArgumentException() {
        GameClock clock = new GameClock(3);

        assertThrows(IllegalArgumentException.class, () -> clock.getRemainingSeconds(null));
    }

    @Test
    void countdown_NotStarted_DoesNotDecreaseTime() {
        AtomicLong time = new AtomicLong(0L);
        GameClock clock = new GameClock(3, time::get);

        time.set(5_000_000_000L);

        assertEquals(180L, clock.getRemainingSeconds(PieceColor.WHITE));
        assertEquals(180L, clock.getRemainingSeconds(PieceColor.BLACK));
    }

    @Test
    void countdown_Start_DecreasesWhiteOnly() {
        AtomicLong time = new AtomicLong(0L);
        GameClock clock = new GameClock(3, time::get);

        clock.start();
        time.set(2_000_000_000L);

        assertEquals(178L, clock.getRemainingSeconds(PieceColor.WHITE));
        assertEquals(180L, clock.getRemainingSeconds(PieceColor.BLACK));
    }

    @Test
    void countdown_ZeroElapsedTime_KeepsInitialTime() {
        AtomicLong time = new AtomicLong(42_000_000_000L);
        GameClock clock = new GameClock(3, time::get);

        clock.start();

        assertEquals(180L, clock.getRemainingSeconds(PieceColor.WHITE));
    }

    @Test
    void countdown_BelowOneSecond_KeepsInitialTime() {
        AtomicLong time = new AtomicLong(0L);
        GameClock clock = new GameClock(3, time::get);

        clock.start();
        time.set(999_000_000L);

        assertEquals(180L, clock.getRemainingSeconds(PieceColor.WHITE));
    }

    @Test
    void countdown_ExactlyOneSecond_DecreasesWhiteTime() {
        AtomicLong time = new AtomicLong(0L);
        GameClock clock = new GameClock(3, time::get);

        clock.start();
        time.set(1_000_000_000L);

        assertEquals(179L, clock.getRemainingSeconds(PieceColor.WHITE));
    }

    @Test
    void countdown_JustAboveOneSecond_Keeps179Seconds() {
        AtomicLong time = new AtomicLong(0L);
        GameClock clock = new GameClock(3, time::get);

        clock.start();
        time.set(1_001_000_000L);

        assertEquals(179L, clock.getRemainingSeconds(PieceColor.WHITE));
    }
}
