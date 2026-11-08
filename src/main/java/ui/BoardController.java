package ui;

import domain.Board;
import domain.state.GameState;
import domain.Location;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class BoardController {
    private BoardView boardView;
    private final Board board;
    private final GameState gameState;

    private final List<GameStateListener> listeners = new ArrayList<>();

    public BoardController(Board board, GameState gameState) {
        this.board = board;
        this.gameState = gameState;
    }

    void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public void addListener(GameStateListener listener) {
        listeners.add(listener);

        // sync state changes for each added listener
        // fine for now because each listener should only be added once when the game initialized
        listener.onTurnChanged(gameState.getActivePlayerName());
    }

    private void fireTurnChanged() {
        for (GameStateListener listener : listeners) listener.onTurnChanged(gameState.getActivePlayerName());
    }

    public void handleSquareClick(Location location) {
        // TODO
        System.out.println("TEST: Square clicked at " + location.getX() + ", " + location.getY());
    }

    public Piece[][] getBoardSnapshot() {
        return this.board.getSnapshot();
    }
}
