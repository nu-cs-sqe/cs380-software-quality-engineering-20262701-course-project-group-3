package ui;

import domain.Board;
import domain.state.GameState;

import javax.swing.*;

public class Main{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            new WelcomeView(Main::startGame).setVisible(true);
        });
    }

    private static void startGame(String player1Name, String player2Name) {
        Board board = new Board();
        GameState gameState = new GameState(player1Name, player2Name);
        BoardController controller = new BoardController(board, gameState);

        GameStatsView statsView = new GameStatsView(player1Name, player2Name);
        controller.addListener(statsView);

        BoardView boardView = new BoardView(controller);
        MainView mainView = new MainView(boardView, statsView);
        mainView.setVisible(true);
    }
}