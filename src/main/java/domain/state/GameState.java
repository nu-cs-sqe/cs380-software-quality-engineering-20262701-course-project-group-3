package domain.state;

import domain.piece.PieceColor;
import domain.piece.PieceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {
    private final String player1Name; // WHITE
    private final String player2Name; // BLACK
    private PieceColor activeColor = PieceColor.WHITE;

    private GameProgress progress = GameProgress.PLAYING;

    private final List<Move> moveHistory = new ArrayList<Move>();

    private final Map<PieceType, Integer> player1CapturedPieces = new HashMap<>();
    private final Map<PieceType, Integer> player2CapturedPieces = new HashMap<>();

    public GameState(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;

        for (PieceType type : PieceType.values()) {
            player1CapturedPieces.put(type, 0);
            player2CapturedPieces.put(type, 0);
        }
    }

    public String getActivePlayerName(){
        return activeColor == PieceColor.WHITE ? player1Name : player2Name;
    }
}
