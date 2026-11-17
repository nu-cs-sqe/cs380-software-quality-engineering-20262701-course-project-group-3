
package domain.state;

import domain.Location;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {

    @Test
    void getMoveType_NormalMove_ReturnsNormal() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 4);
        Piece pawn = new Pawn(PieceColor.WHITE);

        Move move = new Move(from, to, pawn, null, null, MoveType.NORMAL);

        assertEquals(MoveType.NORMAL, move.getMoveType());
    }
}
