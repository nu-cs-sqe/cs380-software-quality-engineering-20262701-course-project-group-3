package ui;

import domain.state.Status;

public interface GameStateListener {
    default void onTurnChanged(String activePlayerName) {
    }

    default void onGameStatusChanged(Status status) {
    }
}
