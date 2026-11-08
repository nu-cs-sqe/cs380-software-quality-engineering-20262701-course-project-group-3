package ui;

import domain.Board;
import domain.GameClock;
import domain.TimeControl;
import domain.state.GameState;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WelcomeView(Main::startGame).setVisible(true);
        });
    }

    private static void startGame(String player1Name, String player2Name, TimeControl timeControl) {
        Board board = new Board();
        GameState gameState = new GameState(player1Name, player2Name);
        BoardController controller = new BoardController(board, gameState);

        GameClock clock = new GameClock(timeControl.getMinutesPerPlayer());
        GameStatsView statsView = new GameStatsView(player1Name, player2Name, clock);
        controller.addListener(statsView);

        BoardView boardView = new BoardView(controller);
        MainView mainView = new MainView(boardView, statsView, timeControl);
        mainView.setVisible(true);
    }
}