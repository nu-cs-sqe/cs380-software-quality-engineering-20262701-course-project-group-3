package ui;

import domain.Board;
import domain.BoardState;

import javax.swing.*;

public class Main{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            new WelcomeView(Main::startGame).setVisible(true);
        });
    }

    private static void startGame(String player1Name, String player2Name) {
        Board board = new Board();
        BoardState boardState = new BoardState(player1Name, player2Name);
        BoardController controller = new BoardController(board, boardState);
        BoardView boardView = new BoardView(controller);

        GameStatsView statsView = new GameStatsView(player1Name, player2Name);

        MainView mainView = new MainView(boardView, statsView);
        mainView.setVisible(true);
    }
}