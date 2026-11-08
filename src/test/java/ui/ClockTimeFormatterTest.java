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
}
