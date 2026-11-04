package ui;

import domain.Board;
import domain.state.GameState;
import domain.Location;
import domain.piece.Piece;

public class BoardController {
    private BoardView boardView;
    private final Board board;
    private final GameState gameState;
    private final GameStateListener listener;

    public BoardController(Board board, GameState gameState, GameStateListener listener) {
        this.board = board;
        this.gameState = gameState;
        this.listener = listener;

        this.listener.onTurnChanged(gameState.getActivePlayerName());
    }

    void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public void handleSquareClick(Location location) {
        // TODO
        System.out.println("TEST: Square clicked at " + location.getX() + ", " + location.getY());
    }

    public Piece[][] getBoardSnapshot() {
        return this.board.getSnapshot();
    }
}
