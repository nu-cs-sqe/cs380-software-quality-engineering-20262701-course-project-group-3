package ui;

import domain.TimeControl;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class MainView extends JFrame {

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

    private void configureMainView(TimeControl timeControl) {
        String duration = timeControl.getMinutesPerPlayer() == 0
                ? "Untimed"
                : timeControl.getMinutesPerPlayer() + " min";

        setTitle("Yiji's Chess Game GUI Example :)! - " + duration);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
    }

    /** Square board on the left, history panel flush against its right edge. */
    private static class BoardWithHistoryLayout implements LayoutManager {
        private final Component board;
        private final Component history;

        BoardWithHistoryLayout(Component board, Component history) {
            this.board = board;
            this.history = history;
        }

        @Override
        public void layoutContainer(Container parent) {
            int side = GameStatsView.boardSide(parent.getWidth(), parent.getHeight());
            int y = (parent.getHeight() - side) / 2;
            board.setBounds(0, y, side, side);
            // same top and height as the board, width fills everything to the right
            history.setBounds(side, y, parent.getWidth() - side, side);
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            Dimension b = board.getPreferredSize();
            int width = (int) (b.width * (1 + GameStatsView.HISTORY_TO_BOARD_RATIO));
            return new Dimension(width, b.height);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(0, 0);
        }

        @Override public void addLayoutComponent(String name, Component comp) {
        }

        @Override public void removeLayoutComponent(Component comp) {
        }
    }
}
