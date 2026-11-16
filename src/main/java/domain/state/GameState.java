package domain.state;

import domain.piece.PieceColor;
import domain.piece.PieceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {
    private final String whitePlayerName;
    private final String blackPlayerName;
    private PieceColor activeColor = PieceColor.WHITE;

    private Progress progress = Progress.PLAYING;

    private final List<Move> moveHistory = new ArrayList<Move>();

    private final Map<PieceType, Integer> player1CapturedPieces = new HashMap<>();
    private final Map<PieceType, Integer> player2CapturedPieces = new HashMap<>();

    public GameState(String whitePlayerName, String blackPlayerName) {
        this.whitePlayerName = whitePlayerName;
        this.blackPlayerName = blackPlayerName;

        for (PieceType type : PieceType.values()) {
            player1CapturedPieces.put(type, 0);
            player2CapturedPieces.put(type, 0);
        }
    }

    public String getActivePlayerName() {
        return activeColor == PieceColor.WHITE ? whitePlayerName : blackPlayerName;
    }

    public Progress getProgress() {
        return progress;
    }

    public List<Move> getMoveHistory() {
        return List.copyOf(moveHistory);
    }
}
