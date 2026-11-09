
package ui;

import domain.GameClock;
import domain.piece.PieceColor;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStatsView extends JPanel implements GameStateListener {

    public JLabel currentPlayerLabel;

    public GameStatsView(String player1Name, String player2Name, GameClock clock) {

        if (clock == null) {
            throw new IllegalArgumentException("Game clock cannot be null.");
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(new Color(104, 76, 150));

        JLabel playerInfoLabel = new JLabel("Player Information");
        playerInfoLabel.setFont(new Font("Arial", Font.BOLD, 30));
        playerInfoLabel.setForeground(Color.WHITE);

        JLabel player1Label = new JLabel("Player 1: " + player1Name + " (Team: White)");
        player1Label.setFont(new Font("Arial", Font.BOLD, 20));
        player1Label.setForeground(Color.WHITE);

        JLabel player2Label = new JLabel("Player 2: " + player2Name + " (Team: Black)");
        player2Label.setFont(new Font("Arial", Font.BOLD, 20));
        player2Label.setForeground(Color.WHITE);

        currentPlayerLabel = new JLabel();
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        currentPlayerLabel.setForeground(Color.WHITE);

        JLabel whiteClockLabel = new JLabel("White Time: " + ClockTimeFormatter.format(clock.getRemainingSeconds(PieceColor.WHITE)));
        whiteClockLabel.setName("whiteClockLabel");
        whiteClockLabel.setFont(new Font("Arial", Font.BOLD, 20));
        whiteClockLabel.setForeground(Color.WHITE);

        JLabel blackClockLabel = new JLabel("Black Time: " + ClockTimeFormatter.format(clock.getRemainingSeconds(PieceColor.BLACK)));
        blackClockLabel.setName("blackClockLabel");
        blackClockLabel.setFont(new Font("Arial", Font.BOLD, 20));
        blackClockLabel.setForeground(Color.WHITE);

        add(playerInfoLabel);
        add(player1Label);
        add(player2Label);
        add(whiteClockLabel);
        add(blackClockLabel);
        add(currentPlayerLabel);
    }

    @Override
    public void onTurnChanged(String activePlayerName) {
        currentPlayerLabel.setText("Current Player: " + activePlayerName);
    }
}
