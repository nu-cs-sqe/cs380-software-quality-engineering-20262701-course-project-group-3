package domain.state;

import domain.locale.I18n;
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

    public int getCapturedPieceCount(PieceColor color, PieceType type) {
        switch (color) {
            case WHITE:
                return player1CapturedPieces.get(type);
            case BLACK:
                return player2CapturedPieces.get(type);
            default:
                throw new IllegalArgumentException("Invalid piece color");
        }
    }

    public String getProgress() {
        return progress == Progress.PLAYING
            ? I18n.get().t("gameState.inProgress")
            : I18n.get().t("gameState.complete");
    }
}
