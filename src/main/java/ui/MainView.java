package ui;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    public MainView(BoardView boardView, GameStatsView gameStatsView) {
        configureMainView();
        add(gameStatsView, BorderLayout.PAGE_START);
        add(boardView, BorderLayout.CENTER);

        validate();
    }

    private void configureMainView(){
        setTitle("Yiji's Chess Game GUI Example :)!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 1200);
        setLocationRelativeTo(null); // Center the window on the screen
    }
}
