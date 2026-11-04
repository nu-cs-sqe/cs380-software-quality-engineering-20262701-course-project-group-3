package domain.state;

import domain.piece.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final String player1Name; // WHITE
    private final String player2Name; // BLACK
    private PieceColor activeColor = PieceColor.WHITE;

    private GameProgress progress = GameProgress.PLAYING;

    private final List<Move> moveHistory = new ArrayList<Move>();

    public GameState(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public String getActivePlayerName(){
        return activeColor == PieceColor.WHITE ? player1Name : player2Name;
    }
}
