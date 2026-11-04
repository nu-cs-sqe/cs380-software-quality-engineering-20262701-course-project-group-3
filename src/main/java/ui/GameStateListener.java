package ui;

public interface GameStateListener {
    default void onTurnChanged(String activePlayerName) {};
}
