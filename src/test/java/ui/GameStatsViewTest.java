
package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.GameClock;
import domain.TimeControl;
import domain.piece.PieceColor;
import java.awt.Component;
import javax.swing.JLabel;
import org.junit.jupiter.api.Test;

class GameStatsViewTest {

    @Test
    void constructor_NullClock_ThrowsIllegalArgumentException() {
        SwingTestSupport.onEventThread(() -> {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new GameStatsView("Alex", "Taylor", null));

            assertTrue(exception.getMessage() != null && !exception.getMessage().isBlank());
            return null;
        });
    }

    @Test
    void constructor_UntimedGame_DisplaysUntimedForBothPlayers() {
        assertInitialTimes(0, "Untimed");
    }

    @Test
    void constructor_BlitzGame_DisplaysThreeMinutesForBothPlayers() {
        assertInitialTimes(3, "03:00");
    }

    @Test
    void constructor_RapidGame_DisplaysTenMinutesForBothPlayers() {
        assertInitialTimes(10, "10:00");
    }

    @Test
    void constructor_ClassicalGame_DisplaysOneHourForBothPlayers() {
        assertInitialTimes(60, "01:00:00");
    }

    @Test
    void onTurnChanged_ValidPlayer_PreservesPlayerInformationAndUpdatesTurn() {
        SwingTestSupport.onEventThread(() -> {
            GameStatsView view = new GameStatsView("Alex", "Taylor", new GameClock(3));
            assertTrue(hasLabelContaining(view, "Player 1: Alex (Team: White)"));
            assertTrue(hasLabelContaining(view, "Player 2: Taylor (Team: Black)"));

            view.onTurnChanged("Alex");
            assertEquals("Current Player: Alex", view.currentPlayerLabel.getText());
            return null;
        });
    }

    @Test
    void constructor_SelectedTimeControl_DisplaysCorrectTimeForBothPlayers() {
        SwingTestSupport.onEventThread(() -> {
            TimeControlSelector selector = new TimeControlSelector();

            for (TimeControl preset : TimeControl.values()) {
                selector.setSelectedItem(preset);
                GameClock clock = new GameClock(selector.getSelectedTimeControl().getMinutesPerPlayer());

                GameStatsView view = new GameStatsView("Alex", "Taylor", clock);

                String expected = ClockTimeFormatter.format(clock.getRemainingSeconds(PieceColor.WHITE));

                assertEquals("White Time: " + expected, namedLabel(view, "whiteClockLabel"));
                assertEquals("Black Time: " + expected, namedLabel(view, "blackClockLabel"));
            }
            return null;
        });
    }

    private void assertInitialTimes(int minutes, String expected) {
        SwingTestSupport.onEventThread(() -> {GameStatsView view = new GameStatsView("Alex", "Taylor", new GameClock(minutes));
            assertEquals("White Time: " + expected, namedLabel(view, "whiteClockLabel"));
            assertEquals("Black Time: " + expected, namedLabel(view, "blackClockLabel"));
            return null;
        });
    }

    private String namedLabel(GameStatsView view, String name) {
        for (Component component : view.getComponents()) {
            if (component instanceof JLabel && name.equals(component.getName())) {
                return ((JLabel) component).getText();
            }
        }
        throw new AssertionError("Could not find clock label: " + name);
    }

    private boolean hasLabelContaining(GameStatsView view, String text) {
        for (Component component : view.getComponents()) {
            if (component instanceof JLabel && ((JLabel) component).getText().contains(text)) {
                return true;
            }
        }
        return false;
    }
}