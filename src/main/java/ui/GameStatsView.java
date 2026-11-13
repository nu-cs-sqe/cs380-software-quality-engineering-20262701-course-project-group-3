
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
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JTextArea;

public class GameStatsView implements GameStateListener {
    private static final int BASE_SIZE = 400; // 8 * 50, matches BoardView's preferred size
    private static final double MIN_SCALE = 0.5;
    private static final Color BACKGROUND_COLOR = new Color(172, 125, 110);
    private static final int HISTORY_BASE_WIDTH = 200;

    public JLabel currentPlayerLabel;
    public JLabel gameStatusLabel;

    private final PlayerPanel whitePanel = new PlayerPanel(Color.WHITE);
    private final PlayerPanel blackPanel = new PlayerPanel(Color.BLACK);
    private final PlayerPanel historyPanel = new PlayerPanel(Color.WHITE) {

        @Override
        double computeScale(Container parent) {
            int board = boardSide(parent.getWidth(), parent.getHeight());
            double widthScale = (parent.getWidth() - board) / (double) HISTORY_BASE_WIDTH;
            return Math.max(MIN_SCALE, Math.min(super.computeScale(parent), widthScale));
        }
    };
    static final double HISTORY_TO_BOARD_RATIO = 0.5; // history width relative to board side

    /** Largest board side (a multiple of 8) that fits alongside the history panel. */
    static int boardSide(int width, int height) {
        int side = Math.min(height, (int) (width / (1 + HISTORY_TO_BOARD_RATIO)));
        return Math.max(0, side / 8 * 8);
    }

    public GameStatsView(String player1Name, String player2Name, GameClock clock) {

        if (clock == null) {
            throw new IllegalArgumentException("Game clock cannot be null.");
        }

        // Black (top)
        JLabel blackClockLabel = new JLabel("Time: "
                + ClockTimeFormatter.format(clock.getRemainingSeconds(PieceColor.BLACK)));
        blackClockLabel.setName("blackClockLabel");
        blackPanel.init(new JLabel("Player 2: " + player2Name), blackClockLabel);

        // White (bottom)
        JLabel whiteClockLabel = new JLabel("Time: "
                + ClockTimeFormatter.format(clock.getRemainingSeconds(PieceColor.WHITE)));
        whiteClockLabel.setName("whiteClockLabel");
        whitePanel.init(new JLabel("Player 1: " + player1Name), whiteClockLabel);

        currentPlayerLabel = new JLabel();
        gameStatusLabel = new JLabel("Game Status: In progress");
        historyPanel.addFooterLabel(currentPlayerLabel, 14);
        historyPanel.addFooterLabel(gameStatusLabel, 14);
        JTextArea historyArea = new JTextArea();   // placeholder: swap in your real history component
        historyPanel.addFlexible(historyArea, 14);
    }

    /** Add to BorderLayout.NORTH. */
    public JPanel getBlackPanel() {
        return blackPanel;
    }

    /** Add to BorderLayout.SOUTH. */
    public JPanel getWhitePanel() {
        return whitePanel;
    }

    /** Pieces captured BY this color. Add your piece icons here. */
    public JPanel getCapturedPanel(PieceColor color) {
        return color == PieceColor.WHITE ? whitePanel.captured : blackPanel.captured;
    }

    /** Add to BorderLayout.EAST. Add your move history below the status label. */
    public JPanel getHistoryPanel() {
        return historyPanel;
    }

    public void setGameStatus(String status) {
        gameStatusLabel.setText("Game Status: " + status);
    }

    @Override
    public void onTurnChanged(String activePlayerName) {
        currentPlayerLabel.setText("Current Player: " + activePlayerName);
    }

    /** A purple panel whose fonts and padding scale with the window. */
    private static class PlayerPanel extends JPanel {
        private final List<JComponent> labels = new ArrayList<>();        private final List<Integer> baseSizes = new ArrayList<>();
        private final ComponentAdapter parentListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                applyScale();
            }
        };

        private double lastScale = -1;
        final JPanel captured = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        private final Color textColor;

        PlayerPanel(Color textColor) {
            this.textColor = textColor;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(true);
            setBackground(BACKGROUND_COLOR);
            captured.setOpaque(false);
        }

        /** Name on its own row; clock and captured pieces side by side below it. */
        void init(JLabel nameLabel, JLabel clockLabel) {
            style(nameLabel, 10, textColor);
            style(clockLabel, 10, textColor);

            JLabel capturedTitle = new JLabel("Captured:");
            style(capturedTitle, 10, textColor);
            captured.add(capturedTitle);
            captured.setAlignmentX(Component.LEFT_ALIGNMENT);

            add(nameLabel);
            add(clockLabel);
            add(captured);
        }

        void addFooterLabel(JLabel label, int baseSize) {
            style(label, baseSize, Color.WHITE);
            add(label);
        }

        private void style(JLabel label, int baseSize, Color textcolor) {
            label.setFont(new Font("Arial", Font.BOLD, baseSize));
            label.setForeground(textcolor);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            labels.add(label);
            baseSizes.add(baseSize);
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
            double scale = computeScale(parent);
            if (Math.abs(scale - lastScale) < 0.01) return;
            lastScale = scale;

            for (int i = 0; i < labels.size(); i++) {
                JComponent c = labels.get(i);
                c.setFont(c.getFont().deriveFont((float) Math.round(baseSizes.get(i) * scale)));
            }

            int pad = (int) Math.round(8 * scale);
            setBorder(new EmptyBorder(pad, pad * 2, pad, pad * 2));
        }

        double computeScale(Container parent) {
            int size = Math.min(parent.getWidth(), parent.getHeight());
            return Math.max(MIN_SCALE, size / (double) BASE_SIZE);
        }

        /** Fills whatever vertical space is left below the labels. No scrolling. */
        void addFlexible(JTextArea content, int baseSize) {
            content.setFont(new Font("Arial", Font.PLAIN, baseSize));
            content.setForeground(textColor);
            content.setOpaque(false);
            content.setEditable(false);
            content.setFocusable(false);
            content.setLineWrap(true);
            content.setWrapStyleWord(true);
            content.setAlignmentX(Component.LEFT_ALIGNMENT);

            labels.add(content);
            baseSizes.add(baseSize);
            add(content);
        }
    }
}

