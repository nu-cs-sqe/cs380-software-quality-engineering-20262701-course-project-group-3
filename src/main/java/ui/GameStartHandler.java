
package ui;

import domain.TimeControl;

@FunctionalInterface
public interface GameStartHandler {

    void start(String whitePlayerName, String blackPlayerName, TimeControl timeControl);
}