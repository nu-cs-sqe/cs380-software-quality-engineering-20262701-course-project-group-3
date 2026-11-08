package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.TimeControl;
import javax.swing.JLabel;
import javax.swing.JList;
import org.junit.jupiter.api.Test;

public class TimeControlSelectorTest {
    @Test
    void defaultsToUntimed() { // TC01
        SwingTestSupport.onEventThread(() -> {
            TimeControlSelector selector = new TimeControlSelector();

            assertEquals(TimeControl.UNTIMED, selector.getSelectedTimeControl());
            return null;
        });
    }

}
