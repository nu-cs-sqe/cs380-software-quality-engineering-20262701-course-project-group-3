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
    void rejectsNullClock() {
        SwingTestSupport.onEventThread(() -> {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new GameStatsView("Alex", "Taylor", null));

            assertTrue(exception.getMessage() != null && !exception.getMessage().isBlank());
            return null;
        });
    }
}