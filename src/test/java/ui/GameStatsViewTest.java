
package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.GameClock;
import domain.TimeControl;
import domain.piece.PieceColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import org.junit.jupiter.api.Test;
import javax.swing.JPanel;
import java.util.concurrent.atomic.AtomicLong;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class GameStatsViewTest {

    private static List<Component> allComponents(GameStatsView view) {
        return collect(view.createContentPanel(new JPanel()));
    }

    private static List<Component> collect(Container root) {
        List<Component> result = new ArrayList<>();
        for (Component child : root.getComponents()) {
            result.add(child);
            if (child instanceof Container) {
                result.addAll(collect((Container) child));
            }
        }
        return result;
    }

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
            assertTrue(hasLabelContaining(view, "Player 1: Alex"));
            assertTrue(hasLabelContaining(view, "Player 2: Taylor"));

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

                assertEquals("Time: " + expected, namedLabel(view, "whiteClockLabel"));
                assertEquals("Time: " + expected, namedLabel(view, "blackClockLabel"));
            }
            return null;
        });
    }

    @Test
    void countdown_BelowFirstDisplayChange_KeepsInitialTime() {
        AtomicLong time = new AtomicLong(0L);

        SwingTestSupport.onEventThread(() -> {
            GameClock clock = new GameClock(3, time::get);
            GameStatsView view = new GameStatsView("Alex", "Taylor", clock);

            clock.start();
            time.set(999_000_000L);
            view.refreshClocks();

            assertEquals("Time: 03:00", namedLabel(view, "whiteClockLabel"));
            assertEquals("Time: 03:00", namedLabel(view, "blackClockLabel"));
            return null;
        });
    }

    @Test
    void countdown_FirstScheduledRefresh_UpdatesWhiteClockAutomatically()
            throws InterruptedException {
        AtomicLong time = new AtomicLong(0L);
        CountDownLatch updated = new CountDownLatch(1);

        GameStatsView view = SwingTestSupport.onEventThread(() -> {
            GameClock clock = new GameClock(3, time::get);
            GameStatsView stats = new GameStatsView("Alex", "Taylor", clock);

            JLabel label = findNamedLabel(stats, "whiteClockLabel");

            label.addPropertyChangeListener("text", event -> {
                if ("Time: 02:59".equals(event.getNewValue())) {
                    updated.countDown();
                }
            });

            clock.start();
            time.set(1_000_000_000L);
            stats.startClockUpdates();

            return stats;
        });

        try {
            assertTrue(updated.await(3, TimeUnit.SECONDS));

            SwingTestSupport.onEventThread(() -> {
                assertEquals("Time: 02:59",
                        namedLabel(view, "whiteClockLabel"));
                return null;
            });
        } finally {
            SwingTestSupport.onEventThread(() -> {
                view.stopClockUpdates();
                return null;
            });
        }
    }

    @Test
    void countdown_BlackDisplay_RemainsUnchanged() {
        AtomicLong time = new AtomicLong(0L);

        SwingTestSupport.onEventThread(() -> {
            GameClock clock = new GameClock(3, time::get);
            GameStatsView view = new GameStatsView("Alex", "Taylor", clock);

            clock.start();
            time.set(2_000_000_000L);
            view.refreshClocks();

            assertEquals("Time: 02:58", namedLabel(view, "whiteClockLabel"));
            assertEquals("Time: 03:00", namedLabel(view, "blackClockLabel"));
            return null;
        });
    }

    @Test
    void countdown_PausedDisplay_KeepsRemainingTime() {
        AtomicLong time = new AtomicLong(0L);

        SwingTestSupport.onEventThread(() -> {
            GameClock clock = new GameClock(3, time::get);
            GameStatsView view = new GameStatsView("Alex", "Taylor", clock);

            clock.start();
            time.set(1_000_000_000L);
            clock.pause();

            time.set(6_000_000_000L);
            view.refreshClocks();

            assertEquals("Time: 02:59", namedLabel(view, "whiteClockLabel"));
            assertEquals("Time: 03:00", namedLabel(view, "blackClockLabel"));
            return null;
        });
    }

    @Test
    void countdown_ResumedDisplay_ContinuesCountdown() {
        AtomicLong time = new AtomicLong(0L);

        SwingTestSupport.onEventThread(() -> {
            GameClock clock = new GameClock(3, time::get);
            GameStatsView view = new GameStatsView("Alex", "Taylor", clock);

            clock.start();
            time.set(1_000_000_000L);
            clock.pause();

            time.set(6_000_000_000L);
            clock.start();

            time.set(7_000_000_000L);
            view.refreshClocks();

            assertEquals("Time: 02:58", namedLabel(view, "whiteClockLabel"));
            assertEquals("Time: 03:00", namedLabel(view, "blackClockLabel"));
            return null;
        });
    }

    @Test
    void countdown_JustBeforeExpiration_DisplaysOneSecond() {
        AtomicLong time = new AtomicLong(0L);

        SwingTestSupport.onEventThread(() -> {
            GameClock clock = new GameClock(3, time::get);
            GameStatsView view = new GameStatsView("Alex", "Taylor", clock);

            clock.start();
            time.set(179_000_000_000L);
            view.refreshClocks();

            assertEquals("Time: 00:01", namedLabel(view, "whiteClockLabel"));
            assertEquals("Time: 03:00", namedLabel(view, "blackClockLabel"));
            return null;
        });
    }

    @Test
    void countdown_ExactlyAtExpiration_DisplaysZero() {
        AtomicLong time = new AtomicLong(0L);

        SwingTestSupport.onEventThread(() -> {
            GameClock clock = new GameClock(3, time::get);
            GameStatsView view = new GameStatsView("Alex", "Taylor", clock);

            clock.start();
            time.set(180_000_000_000L);
            view.refreshClocks();

            assertEquals("Time: 00:00", namedLabel(view, "whiteClockLabel"));
            assertEquals("Time: 03:00", namedLabel(view, "blackClockLabel"));
            return null;
        });
    }

    private void assertInitialTimes(int minutes, String expected) {
        SwingTestSupport.onEventThread(() -> {
            GameStatsView view = new GameStatsView("Alex", "Taylor", new GameClock(minutes));
            assertEquals("Time: " + expected, namedLabel(view, "whiteClockLabel"));
            assertEquals("Time: " + expected, namedLabel(view, "blackClockLabel"));
            return null;
        });
    }

    private JLabel findNamedLabel(GameStatsView view, String name) {
        for (Component component : allComponents(view)) {
            if (component instanceof JLabel && name.equals(component.getName())) {
                return (JLabel) component;
            }
        }

        throw new AssertionError("Could not find clock label: " + name);
    }

    private String namedLabel(GameStatsView view, String name) {
        return findNamedLabel(view, name).getText();
    }

    private boolean hasLabelContaining(GameStatsView view, String text) {
        for (Component component : allComponents(view)) {
            if (component instanceof JLabel && ((JLabel) component).getText().contains(text)) {
                return true;
            }
        }
        return false;
    }
}