package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.TimeControl;
import javax.swing.JLabel;
import javax.swing.JList;
import org.junit.jupiter.api.Test;

public class TimeControlSelectorTest {
    @Test
    void constructor_DefaultSelection_ReturnsUntimed() {
        SwingTestSupport.onEventThread(() -> {
            TimeControlSelector selector = new TimeControlSelector();

            assertEquals(TimeControl.UNTIMED, selector.getSelectedTimeControl());
            return null;
        });
    }

    @Test
    void constructor_AvailablePresets_ContainsAllFourTimeControls() {
        SwingTestSupport.onEventThread(() -> {
            TimeControlSelector selector = new TimeControlSelector();

            assertEquals(4, selector.getItemCount());
            assertEquals(TimeControl.UNTIMED, selector.getItemAt(0));
            assertEquals(TimeControl.BLITZ_3_MIN, selector.getItemAt(1));
            assertEquals(TimeControl.RAPID_10_MIN, selector.getItemAt(2));
            assertEquals(TimeControl.CLASSICAL_60_MIN, selector.getItemAt(3));
            return null;
        });
    }

    @Test
    void constructor_DisplayNames_ShowsReadableNames() {
        SwingTestSupport.onEventThread(() -> {
            TimeControlSelector selector = new TimeControlSelector();
            String[] expected = {"Untimed", "Blitz (3 min)", "Rapid (10 min)", "Classical (60 min)"};

            for (int i = 0; i < expected.length; i++) {
                TimeControl item = selector.getItemAt(i);
                JLabel rendered = (JLabel) selector.getRenderer().getListCellRendererComponent(new JList<TimeControl>(), item, i, false, false);
                assertEquals(expected[i], rendered.getText());
            }
            return null;
        });
    }

    @Test
    void getSelectedTimeControl_ChangeSelection_ReturnsSelectedPreset() {
        SwingTestSupport.onEventThread(() -> {
            TimeControlSelector selector = new TimeControlSelector();

            for (TimeControl control : TimeControl.values()) {
                selector.setSelectedItem(control);
                assertEquals(control, selector.getSelectedTimeControl());
            }
            return null;
        });
    }

    @Test
    void getSelectedTimeControl_RevertSelection_ReturnsUntimed() {
        SwingTestSupport.onEventThread(() -> {
            TimeControlSelector selector = new TimeControlSelector();

            selector.setSelectedItem(TimeControl.CLASSICAL_60_MIN);
            selector.setSelectedItem(TimeControl.UNTIMED);

            assertEquals(TimeControl.UNTIMED, selector.getSelectedTimeControl());
            return null;
        });
    }
}
