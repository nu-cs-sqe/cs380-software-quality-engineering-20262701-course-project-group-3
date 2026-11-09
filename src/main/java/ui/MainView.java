
package ui;

import domain.TimeControl;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class MainView extends JFrame {

    public MainView(BoardView boardView, GameStatsView gameStatsView, TimeControl timeControl) {

        configureMainView(timeControl);

        add(gameStatsView, BorderLayout.PAGE_START);
        add(boardView, BorderLayout.CENTER);

        validate();
    }

    private void configureMainView(TimeControl timeControl) {
        String duration = timeControl.getMinutesPerPlayer() == 0 ? "Untimed" : timeControl.getMinutesPerPlayer() + " min";

        setTitle("Yiji's Chess Game GUI Example :)! - " + duration);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 1200);
        setLocationRelativeTo(null);
    }
}
