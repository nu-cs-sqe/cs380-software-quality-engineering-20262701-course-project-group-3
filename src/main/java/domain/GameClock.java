package domain;

import domain.piece.PieceColor;
import java.util.Objects;
import java.util.function.LongSupplier;

public final class GameClock {
    public static final long UNLIMITED_SECONDS = -1L;

    private final long whiteRemainingSeconds;
    private final long blackRemainingSeconds;
    private final LongSupplier nanoTimeSource;

    public GameClock(int minutesPerPlayer) {
        this(minutesPerPlayer, System::nanoTime);
    }

    GameClock(int minutesPerPlayer, LongSupplier nanoTimeSource) {
        if (minutesPerPlayer < 0) {
            throw new IllegalArgumentException(
                    "Minutes per player cannot be negative.");
        }

        this.nanoTimeSource = Objects.requireNonNull(
                nanoTimeSource, "Time source cannot be null.");

        long startingSeconds = minutesPerPlayer == 0
                ? UNLIMITED_SECONDS
                : minutesPerPlayer * 60L;

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
