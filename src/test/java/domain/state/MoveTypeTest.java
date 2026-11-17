package domain.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoveTypeTest {

    @Test
    void values_SupportedMoveTypes_ContainsExpectedTypes() {
        assertArrayEquals(
                new MoveType[]{MoveType.NORMAL, MoveType.CASTLE, MoveType.EN_PASSANT},
                MoveType.values()
        );
    }
}