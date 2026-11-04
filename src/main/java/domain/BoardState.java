package domain;

import domain.piece.PieceColor;

public class BoardState {
    private final String player1Name; // WHITE
    private final String player2Name; // BLACK
    private PieceColor activeColor = PieceColor.WHITE;

    public BoardState(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public String getActivePlayerName(){
        return activeColor == PieceColor.WHITE ? player1Name : player2Name;
    }
}
