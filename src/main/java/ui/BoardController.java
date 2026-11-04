package ui;

import domain.Board;
import domain.BoardState;
import domain.Location;
import domain.piece.Piece;

public class BoardController {
    private BoardView boardView;
    private final Board board;
    private final BoardState boardState;
    private final BoardStateListener listener;

    public BoardController(Board board, BoardState boardState, BoardStateListener listener) {
        this.board = board;
        this.boardState = boardState;
        this.listener = listener;

        this.listener.onTurnChanged(boardState.getActivePlayerName());
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
