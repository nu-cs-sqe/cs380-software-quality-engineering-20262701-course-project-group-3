package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ClockTimeFormatterTest {
    @Test
    void format_NegativeSeconds_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ClockTimeFormatter.format(-2));

        assertTrue(exception.getMessage() != null && !exception.getMessage().isBlank());
    }

    @Test
    void format_UntimedSentinel_ReturnsUntimed() {
        assertEquals("Untimed", ClockTimeFormatter.format(-1));
    }

    @Test
    void format_ZeroSeconds_ReturnsZeroTime() {
        assertEquals("00:00", ClockTimeFormatter.format(0));
    }

    @Test
    void format_OneSecond_ReturnsCorrectTime() {
        assertEquals("00:01", ClockTimeFormatter.format(1));
    }

    @Test
    void format_FiftyNineSeconds_ReturnsCorrectTime() {
        assertEquals("00:59", ClockTimeFormatter.format(59));
    }

    @Test
    void format_SixtySeconds_ReturnsOneMinute() {
        assertEquals("01:00", ClockTimeFormatter.format(60));
    }
}
