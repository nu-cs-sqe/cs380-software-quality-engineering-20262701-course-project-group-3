package domain;

public enum TimeControl {
    UNTIMED(0),
    BLITZ_3_MIN(3);

    private final int minutesPerPlayer;

    TimeControl(int minutesPerPlayer) {
        this.minutesPerPlayer = minutesPerPlayer;
    }

    public int getMinutesPerPlayer() {
        return minutesPerPlayer;
    }
}