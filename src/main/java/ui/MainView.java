package ui;

import domain.TimeControl;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * <u>MainView</u>
 * <p> The main window of the chess game. It arranges the black player panel (top),
 * the board with the history panel (center), and the white player panel (bottom).
 *
 * <p> The board and history panel are placed by {@link BoardWithHistoryLayout} so the
 * history panel stays flush against the board and fills the remaining width.
 */
public class MainView extends JFrame {

    /**
     * <u>MainView</u>
     * <p> Creates the window and places the board and the stats panels in it.
     */
    public MainView(BoardView boardView, GameStatsView gameStatsView, TimeControl timeControl) {

        configureMainView(timeControl);

        add(gameStatsView.getBlackPanel(), BorderLayout.NORTH);

        JPanel historyPanel = gameStatsView.getHistoryPanel();
        JPanel center = new JPanel(new BoardWithHistoryLayout(boardView, historyPanel));
        center.add(boardView);
        center.add(historyPanel);
        add(center, BorderLayout.CENTER);
        center.add(boardView);
        center.add(gameStatsView.getHistoryPanel());
        add(center, BorderLayout.CENTER);

        add(gameStatsView.getWhitePanel(), BorderLayout.SOUTH);

        validate();
    }

    /**
     * <u>configureMainView</u>
     * <p> Sets the window title , close behavior, size, and position.
     */
    private void configureMainView(TimeControl timeControl) {
        String duration = timeControl.getMinutesPerPlayer() == 0
                ? "Untimed"
                : timeControl.getMinutesPerPlayer() + " min";

        setTitle("Yiji's Chess Game GUI Example :)! - " + duration);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
    }

    /**
     * <u>BoardWithHistoryLayout</u>
     * <p> A layout manager that places a square board on the left and a history panel
     * directly to its right. The history panel matches the board's height and fills
     * the rest of the width.
     */
    private static class BoardWithHistoryLayout implements LayoutManager {
        private final Component board;
        private final Component history;

        /**
         * <u>BoardWithHistoryLayout</u>
         * <p> Creates a layout that manages the given board and history components.
         */
        BoardWithHistoryLayout(Component board, Component history) {
            this.board = board;
            this.history = history;
        }

        /**
         * <u>layoutContainer</u>
         * <p> Sizes the board as the largest square that fits , centers it vertically,
         * and gives the history panel the same height and all remaining width.
         */
        @Override
        public void layoutContainer(Container parent) {
            int side = GameStatsView.boardSide(parent.getWidth(), parent.getHeight());
            int y = (parent.getHeight() - side) / 2;
            board.setBounds(0, y, side, side);
            // same top and height as the board, width fills everything to the right
            history.setBounds(side, y, parent.getWidth() - side, side);
        }

        /**
         * <u>preferredLayoutSize</u>
         * <p> Returns the board's preferred size, widened to leave room for the history panel.
         */
        @Override
        public Dimension preferredLayoutSize(Container parent) {
            Dimension b = board.getPreferredSize();
            int width = (int) (b.width * (1 + GameStatsView.HISTORY_TO_BOARD_RATIO));
            return new Dimension(width, b.height);
        }

        /**
         * <u>minimumLayoutSize</u>
         * <p> Returns zero size, so the container can shrink freely.
         */
        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(0, 0);
        }

        /** NOT USED: Just here to fulfill interface requirements */
        @Override public void addLayoutComponent(String name, Component comp) {
        }

        /** NOT USED: Just here to fulfill interface requirements */
        @Override public void removeLayoutComponent(Component comp) {
        }
    }
}
