package ui;

public interface GameStateListener {
    default void onTurnChanged(String activePlayerName) {
    }

    default void onGameProgressChanged(String gameProgress) {
    }
}
