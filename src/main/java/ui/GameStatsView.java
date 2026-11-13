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

/**
 * <u>GameStatsView</u>
 * <p> This builds and updates the player information panels around the chess board.
 * The view is split into three panels: <ul>
 *   <li> Black player panel (top)
 *   <li> White player panel (bottom)
 *   <li> History panel (right of the board)
 * </ul>
 * <p> All text and padding scale with the window size so the panels stay consistent
 * with {@link BoardView}. This class is NOT itself a Swing component. Callers retrieve
 * the panels with <ul>
 *   <li> {@link #getBlackPanel()}
 *   <li> {@link #getWhitePanel()}
 *   <li> {@link #getHistoryPanel()}
 * </ul>
 */
public class GameStatsView implements GameStateListener {

    private static final int BASE_SIZE = 400;
    private static final double MIN_SCALE = 0.5;
    private static final Color BACKGROUND_COLOR = new Color(172, 125, 110);
    private static final int HISTORY_BASE_WIDTH = 200;

    public JLabel currentPlayerLabel;
    public JLabel gameStatusLabel;

    private final PlayerPanel whitePanel = new PlayerPanel(Color.WHITE);
    private final PlayerPanel blackPanel = new PlayerPanel(Color.BLACK);
    private final ScalingPanel historyPanel = new ScalingPanel(Color.WHITE) {
        @Override
        double computeScale(Container parent) {
            int board = boardSide(parent.getWidth(), parent.getHeight());
            double widthScale = (parent.getWidth() - board) / (double) HISTORY_BASE_WIDTH;
            return Math.max(MIN_SCALE, Math.min(super.computeScale(parent), widthScale));
        }
    };
    static final double HISTORY_TO_BOARD_RATIO = 0.5; // history width relative to board side

    /**
     * <u>boardSide</u>
     * <p> Computes the largest board side length that fits alongside the history panel.
     * The result is rounded down to a multiple of 8 to make tile sizes whole numbers.
     */
    static int boardSide(int width, int height) {
        int side = Math.min(height, (int) (width / (1 + HISTORY_TO_BOARD_RATIO)));
        return Math.max(0, side / 8 * 8);
    }

    /**
     * <u>GameStatsView</u>
     * <p> Creates the player panels and history panel.
     */
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
        JTextArea historyArea = new JTextArea();   // Placeholder for real history
        historyPanel.addFlexible(historyArea, 14);
    }

    /**
     * <u>getBlackPanel</u>
     * <p> Returns the black player's panel to place above the board.
     */
    public JPanel getBlackPanel() {
        return blackPanel;
    }

    /**
     * <u>getWhitePanel</u>
     * <p> Returns the white player's panel to place below the board.
     */
    public JPanel getWhitePanel() {
        return whitePanel;
    }

    /**
     * <u>getCapturedPanel</u>
     * <p> Returns the panel where a player's captured pieces are displayed.
     */
    public JPanel getCapturedPanel(PieceColor color) {
        return color == PieceColor.WHITE ? whitePanel.captured : blackPanel.captured;
    }

    /**
     * <u>getHistoryPanel</u>
     * <p> Returns the history panel to be placed on the right of board.
     */
    public JPanel getHistoryPanel() {
        return historyPanel;
    }

    /**
     * <u>getHistoryPanel</u>
     * <p> Sets the game status to the provided input.
     */
    public void setGameStatus(String status) {
        gameStatusLabel.setText("Game Status: " + status);
    }

    /**
     * <u>onTurnChanged</u>
     * <p> Updates the current player label when the turn changes.
     */
    @Override
    public void onTurnChanged(String activePlayerName) {
        currentPlayerLabel.setText("Current Player: " + activePlayerName);
    }

    /**
     * <u>ScalingPanel</u>
     * <p> A panel whose fonts and padding scale with the size of its parent container.
     *
     * <p> Components added through {@link #addFooterLabel} or {@link #addFlexible} are
     * registered with a base font size and rescaled whenever the parent is resized.
     */
    private static class ScalingPanel extends JPanel {
        private final List<JComponent> labels = new ArrayList<>();        private final List<Integer> baseSizes = new ArrayList<>();
        private final ComponentAdapter parentListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                applyScale();
            }
        };

        private double lastScale = -1;
        protected final Color textColor;

        /**
         * <u>ScalingPanel</u>
         * <p> Creates a panel that'll organize stats vertically.
         */
        ScalingPanel(Color textColor) {
            this.textColor = textColor;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(true);
            setBackground(BACKGROUND_COLOR);
        }

        /**
         * <u>addFooterLabel</u>
         * <p> Used to add text to a panel (allows text to scale).
         */
        void addFooterLabel(JLabel label, int baseSize) {
            style(label, baseSize, Color.WHITE);
            add(label);
        }

        /**
         * <u>style</u>
         * <p> Applies the font, color, and alignment shared by all labels and registers
         * the label for scaling.
         */
        protected void style(JLabel label, int baseSize, Color textcolor) {
            label.setFont(new Font("Arial", Font.BOLD, baseSize));
            label.setForeground(textcolor);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            labels.add(label);
            baseSizes.add(baseSize);
        }


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

        /**
         * <u>applyScale</u>
         * <p> Rescales all registered fonts and the padding to match the parent's size.
         * Does nothing if the scale changed by less than 1%.
         */
        private void applyScale() {
            Container parent = getParent();
            if (parent == null) return;

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

        /**
         * <u>computeScale</u>
         * <p> Computes the scale factor from the parent's smaller dimension. Subclasses
         * override this to base scaling on something else.
         */
        double computeScale(Container parent) {
            int size = Math.min(parent.getWidth(), parent.getHeight());
            return Math.max(MIN_SCALE, size / (double) BASE_SIZE);
        }

        /**
         * <u>addFlexible</u>
         * <p> Adds a transparent, read-only text area that fills the vertical space left
         * below the labels. It does not scroll, so text past the bottom is clipped.
         */
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

    /**
     * <u>PlayerPanel</u>
     * <p> A scaling panel for one player: name, clock, and captured pieces on separate lines.
     * */
    private static class PlayerPanel extends ScalingPanel {
        final JPanel captured = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));

        /**
         * <u>PlayerPanel</u>
         * <p> Creates a player panel with a transparent captured-pieces row.
         */
        PlayerPanel(Color textColor) {
            super(textColor);
            captured.setOpaque(false);
        }

        /**
         * <u>init</u>
         * <p> Lays out the player's name, clock, and captured pieces on separate lines.
         */
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
    }
}

