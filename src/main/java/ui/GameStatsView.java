
package ui;

import domain.GameClock;
import domain.locale.I18n;
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
import java.awt.BorderLayout;
import javax.swing.Timer;

/**
 * <u>GameStatsView</u>
 * <p> This builds and updates the player information panels around the chess board.
 * The view is split into three panels: <ul>
 *   <li> Black player panel (top)
 *   <li> White player panel (bottom)
 *   <li> History panel (right of the board)
 * </ul>
 * <p> All text and padding scale with the window size so the panels stay consistent
 * with {@link BoardView}. This class is NOT itself a Swing component. Callers use
 * {@link #createContentPanel(Component)} to assemble the game display.
 */
public final class GameStatsView implements GameStateListener {

    private static final int BASE_SIZE = 400;
    private static final double MIN_SCALE = 0.5;
    private static final Color BACKGROUND_COLOR = new Color(172, 125, 110);
    private static final int HISTORY_BASE_WIDTH = 200;

    public JLabel currentPlayerLabel;
    public JLabel gameStatusLabel;

    private final GameClock clock;
    private final JLabel whiteClockLabel;
    private final JLabel blackClockLabel;
    private Timer clockRefreshTimer;

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

    void startClockUpdates() {
        if (!clock.isTimed()) {
            return;
        }

        if (clockRefreshTimer == null) {
            clockRefreshTimer = new Timer(200, event -> refreshClocks());
        }

        clockRefreshTimer.start();
    }

    void stopClockUpdates() {
        if (clockRefreshTimer != null) {
            clockRefreshTimer.stop();
        }
    }

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

        this.clock = clock;

        // Black (top)
        blackClockLabel = new JLabel(I18n.get().t("gameStatsView.clockLabel",
                ClockTimeFormatter.format(clock.getRemainingSeconds(PieceColor.BLACK))));
        blackClockLabel.setName("blackClockLabel");
        JLabel player2Label = new JLabel(I18n.get().t("gameStatsView.player2Name", player2Name));
        blackPanel.init(player2Label, blackClockLabel);

        // White (bottom)
        whiteClockLabel = new JLabel(I18n.get().t("gameStatsView.clockLabel",
                ClockTimeFormatter.format(clock.getRemainingSeconds(PieceColor.BLACK))));
        whiteClockLabel.setName("whiteClockLabel");
        JLabel player1Label = new JLabel(I18n.get().t("gameStatsView.player1Name", player1Name));
        whitePanel.init(player1Label, whiteClockLabel);

        currentPlayerLabel = new JLabel();
        gameStatusLabel = new JLabel();
        historyPanel.addFooterLabel(currentPlayerLabel, 14);
        historyPanel.addFooterLabel(gameStatusLabel, 14);
        JTextArea historyArea = new JTextArea();   // Placeholder for real history
        historyPanel.addFlexible(historyArea, 14);
    }

    /**
     * Refreshes both clock labels using the current remaining times.
     * Must be called on the Swing Event Dispatch Thread.
     */
    void refreshClocks() {
        whiteClockLabel.setText("Time: "
                + ClockTimeFormatter.format(clock.getRemainingSeconds(PieceColor.WHITE)));
        blackClockLabel.setText("Time: "
                + ClockTimeFormatter.format(clock.getRemainingSeconds(PieceColor.BLACK)));
    }

    /**
     * Returns whether the GUI clock refresh timer is running.
     */
    boolean isClockUpdatesRunning() {
        return clockRefreshTimer != null && clockRefreshTimer.isRunning();
    }

    /**
     * <u>createContentPanel</u>
     * <p> Assembles the board, player information, and history panels.
     */
    public JPanel createContentPanel(Component boardView) {
        JPanel content = new JPanel(new BorderLayout());

        content.add(blackPanel, BorderLayout.NORTH);

        JPanel center = new JPanel(
                new MainView.BoardWithHistoryLayout(boardView, historyPanel)
        );
        center.add(boardView);
        center.add(historyPanel);
        content.add(center, BorderLayout.CENTER);

        content.add(whitePanel, BorderLayout.SOUTH);

        return content;
    }

    /**
     * <u>getCapturedPanel</u>
     * <p> Returns the panel where a player's captured pieces are displayed.
     */
    public JPanel getCapturedPanel(PieceColor color) {
        return color == PieceColor.WHITE ? whitePanel.captured : blackPanel.captured;
    }

    /**
     * <u>onGameProgressChanged</u>
     * <p> Updates the current status label when the game status changes
     */
    public void onGameProgressChanged(String gameProgress) {
        gameStatusLabel.setText(I18n.get().t("gameStatsView.gameStatusLabel", gameProgress));
    }

    /**
     * <u>onTurnChanged</u>
     * <p> Updates the current player label when the turn changes.
     */
    @Override
    public void onTurnChanged(String activePlayerName) {
        currentPlayerLabel.setText(I18n.get()
                .t("gameStatsView.currentPlayerLabel", activePlayerName));
    }

    /**
     * <u>ScalingPanel</u>
     * <p> A panel whose fonts and padding scale with the size of its parent container.
     *
     * <p> Components added through {@link #addFooterLabel} or {@link #addFlexible} are
     * registered with a base font size and rescaled whenever the parent is resized.
     */
    private static class ScalingPanel extends JPanel {
        private final List<JComponent> labels = new ArrayList<>();
        private final List<Integer> baseSizes = new ArrayList<>();
        private transient ComponentAdapter parentListener;

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
                if (parentListener == null) {
                    parentListener = new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                            applyScale();
                        }
                    };
                }
                parent.addComponentListener(parentListener);
                applyScale();
            }
        }

        @Override
        public void removeNotify() {
            Container parent = getParent();
            if (parent != null && parentListener != null) {
                parent.removeComponentListener(parentListener);
            }
            super.removeNotify();
        }

        /**
         * <u>applyScale</u>
         * <p> Rescales all registered fonts and padding to match the parent's size.
         * Does nothing if the scale changed by less than 1%.
         */
        private void applyScale() {
            Container parent = getParent();
            if (parent == null){
                return;
            }

            double scale = computeScale(parent);
            if (Math.abs(scale - lastScale) < 0.01){
                return;
            }
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
     * <p> A panel whose labels and captured pieces are arranged vertically.
     */
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

            JLabel capturedTitle = new JLabel(I18n.get().t("gameStatsView.capturedLabel"));
            style(capturedTitle, 10, textColor);
            captured.add(capturedTitle);
            captured.setAlignmentX(Component.LEFT_ALIGNMENT);

            add(nameLabel);
            add(clockLabel);
            add(captured);
        }
    }
}
