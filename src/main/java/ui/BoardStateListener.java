package ui;

public interface BoardStateListener {
    default void onTurnChanged(String activePlayerName) {};
}
