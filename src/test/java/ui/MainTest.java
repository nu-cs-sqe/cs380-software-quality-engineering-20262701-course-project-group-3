package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.GameClock;
import domain.TimeControl;
import domain.piece.PieceColor;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void countdown_AutomaticStartup_StartsWhiteClock() {
        AtomicLong time = new AtomicLong(0L);

        GameClock clock = Main.createStartedClock(
                TimeControl.BLITZ_3_MIN, time::get);

        time.set(2_000_000_000L);

        assertEquals(178L, clock.getRemainingSeconds(PieceColor.WHITE));
        assertEquals(180L, clock.getRemainingSeconds(PieceColor.BLACK));
    }
}