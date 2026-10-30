package domain;

import domain.piece.PieceColor;

public class GameClock {
    public static final long UNLIMITED_SECONDS = -1L;

    private final long whiteRemainingSeconds;
    private final long blackRemainingSeconds;

    public GameClock(int minutesPerPlayer) {
        if (minutesPerPlayer < 0) {
            throw new IllegalArgumentException("Minutes per player cannot be negative.");
        }

        long startingSeconds = minutesPerPlayer == 0 ? UNLIMITED_SECONDS : minutesPerPlayer * 60L;

        whiteRemainingSeconds = startingSeconds;
        blackRemainingSeconds = startingSeconds;
    }

    public boolean isTimed() {
        return whiteRemainingSeconds != UNLIMITED_SECONDS;
    }

    public long getRemainingSeconds(PieceColor color) {
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null.");
        }
        return color == PieceColor.WHITE ? whiteRemainingSeconds : blackRemainingSeconds;
    }
}
