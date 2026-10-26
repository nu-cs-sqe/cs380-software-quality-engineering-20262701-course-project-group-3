package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimeControlTest {
    @Test
    void getMinutesPerPlayer_Untimed_ReturnsZero() {
        assertEquals(0, TimeControl.UNTIMED.getMinutesPerPlayer());
    }
}
