package ui;

import domain.GameClock;
import java.util.Locale;

public final class ClockTimeFormatter {
    private ClockTimeFormatter() {
    }

    public static String format(long remainingSeconds) {
        if (remainingSeconds == GameClock.UNLIMITED_SECONDS) {
            return "Untimed";
        }

        if (remainingSeconds < 0) {
            throw new IllegalArgumentException("Remaining seconds cannot be negative: " + remainingSeconds);
        }

        long hours = remainingSeconds / 3600;
        long minutes = (remainingSeconds % 3600) / 60;
        long seconds = remainingSeconds % 60;

        if (hours > 0) {
            return String.format(Locale.ROOT, "%02d:%02d:%02d", hours, minutes, seconds);
        }

        return String.format(Locale.ROOT, "%02d:%02d", minutes, seconds);
    }
}