package domain;

import domain.state.PlayerState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerStateTest {

    @Test
    public void PlayerStateTC01_NewPlayerCanCastle(){
        PlayerState playerState = new PlayerState();

        assertTrue(playerState.getCastleKingsideEligible());
        assertTrue(playerState.getCastleQueensideEligible());
    }

    @Test
    public void getCastleKingsideEligibleTC01_InitialState() {
        PlayerState playerState = new PlayerState();

        assertTrue(playerState.getCastleKingsideEligible());
    }
}
