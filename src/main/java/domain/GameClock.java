package domain;

import domain.piece.PieceColor;
import java.util.Objects;
import java.util.function.LongSupplier;

public final class GameClock {
    public static final long UNLIMITED_SECONDS = -1L;

    private final long whiteRemainingSeconds;
    private final long blackRemainingSeconds;
    private final LongSupplier nanoTimeSource;
    private boolean running;
    private long startedAtNanos;
    private long elapsedNanosBeforePause;

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

        if (color == PieceColor.BLACK || !isTimed()) {
            return color == PieceColor.WHITE
                    ? whiteRemainingSeconds
                    : blackRemainingSeconds;
        }

        long elapsedNanos = elapsedNanosBeforePause;

        if (running) {
            elapsedNanos += nanoTimeSource.getAsLong() - startedAtNanos;
        }

        long elapsedSeconds = elapsedNanos / 1_000_000_000L;

        return Math.max(0L, whiteRemainingSeconds - elapsedSeconds);
    }

    public void start() {
        if (!isTimed() || running) {
            return;
        }

        startedAtNanos = nanoTimeSource.getAsLong();
        running = true;
    }

    public void pause() {
        if (!running) {
            return;
        }

        elapsedNanosBeforePause += nanoTimeSource.getAsLong() - startedAtNanos;
        running = false;
    }
}
