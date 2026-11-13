
package ui;

import domain.GameClock;
import domain.piece.PieceColor;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class GameStatsView implements GameStateListener {
    private static final int BASE_SIZE = 400; // 8 * 50, matches BoardView's preferred size
    private static final double MIN_SCALE = 0.5;
    private static final Color BACKGROUND_COLOR = new Color(104, 76, 150);

    public JLabel currentPlayerLabel;

    private final PlayerPanel whitePanel = new PlayerPanel();
    private final PlayerPanel blackPanel = new PlayerPanel();

    public GameStatsView(String player1Name, String player2Name, GameClock clock) {

        if (clock == null) {
            throw new IllegalArgumentException("Game clock cannot be null.");
        }

        // Black (top)
        blackPanel.addLabel(new JLabel("Player 2: " + player2Name + " (Team: Black)"), 20);
        JLabel blackClockLabel = new JLabel("Black Time: "
                + ClockTimeFormatter.format(clock.getRemainingSeconds(PieceColor.BLACK)));
        blackClockLabel.setName("blackClockLabel");
        blackPanel.addLabel(blackClockLabel, 20);

        // White (bottom)
        whitePanel.addLabel(new JLabel("Player 1: " + player1Name + " (Team: White)"), 20);
        JLabel whiteClockLabel = new JLabel("White Time: "
                + ClockTimeFormatter.format(clock.getRemainingSeconds(PieceColor.WHITE)));
        whiteClockLabel.setName("whiteClockLabel");
        whitePanel.addLabel(whiteClockLabel, 20);

        currentPlayerLabel = new JLabel();
        whitePanel.addLabel(currentPlayerLabel, 30);
    }

    /** Add to BorderLayout.NORTH. */
    public JPanel getBlackPanel() {
        return blackPanel;
    }

    /** Add to BorderLayout.SOUTH. */
    public JPanel getWhitePanel() {
        return whitePanel;
    }

        @Override
    public void onTurnChanged(String activePlayerName) {
        currentPlayerLabel.setText("Current Player: " + activePlayerName);
    }


    /** A purple panel whose fonts and padding scale with the window. */
    private static class PlayerPanel extends JPanel {
        private final List<JLabel> labels = new ArrayList<>();
        private final List<Integer> baseSizes = new ArrayList<>();
        private final ComponentAdapter parentListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                applyScale();
            }
        };
        private double lastScale = -1;

        PlayerPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(true);
            setBackground(BACKGROUND_COLOR);
        }

        void addLabel(JLabel label, int baseSize) {
            label.setFont(new Font("Arial", Font.BOLD, baseSize));
            label.setForeground(Color.WHITE);
            labels.add(label);
            baseSizes.add(baseSize);
            add(label);
        }

        // Track the parent's size, not our own: our width stays the same when
        // only the window height changes, but the board scales with both.
        @Override
        public void addNotify() {
            super.addNotify();
            Container parent = getParent();
            if (parent != null) {
                parent.addComponentListener(parentListener);
                applyScale();
            }
        }

        @Override
        public void removeNotify() {
            Container parent = getParent();
            if (parent != null) {
                parent.removeComponentListener(parentListener);
            }
            super.removeNotify();
        }

        private void applyScale() {
            Container parent = getParent();
            if (parent == null) return;

            int size = Math.min(parent.getWidth(), parent.getHeight());
            double scale = Math.max(MIN_SCALE, size / (double) BASE_SIZE);
            if (Math.abs(scale - lastScale) < 0.01) return;
            lastScale = scale;

            for (int i = 0; i < labels.size(); i++) {
                JLabel label = labels.get(i);
                label.setFont(label.getFont().deriveFont((float) Math.round(baseSizes.get(i) * scale)));
            }

            int pad = (int) Math.round(8 * scale);
            setBorder(new EmptyBorder(pad, pad * 2, pad, pad * 2));
        }
    }
}

