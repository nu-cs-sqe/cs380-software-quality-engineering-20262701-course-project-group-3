package ui;

import domain.state.Progress;

public interface GameStateListener {
    default void onTurnChanged(String activePlayerName) {
    }

    default void onGameProgressChanged(Progress gameProgress) {
    }
}
