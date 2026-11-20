package domain;

import domain.state.PlayerState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @Test
    public void getCastleKingsideEligibleTC02_KingsideRevoke() {
        PlayerState playerState = new PlayerState();
        playerState.revokeKingsideCastling();

        assertFalse(playerState.getCastleKingsideEligible());
    }

    @Test
    public void getCastleKingsideEligibleTC03_QueensideRevoke() {
        PlayerState playerState = new PlayerState();
        playerState.revokeQueensideCastling();

        assertTrue(playerState.getCastleKingsideEligible());
    }

    @Test
    public void getCastleQueensideEligibleTC01_InitialState() {
        PlayerState playerState = new PlayerState();

        assertTrue(playerState.getCastleQueensideEligible());
    }

    @Test
    public void getCastleQueensideEligibleTC02_QueensideRevoke() {
        PlayerState playerState = new PlayerState();
        playerState.revokeQueensideCastling();

        assertFalse(playerState.getCastleQueensideEligible());
    }

    @Test
    public void getCastleQueensideEligibleTC03_KingsideRevoke() {
        PlayerState playerState = new PlayerState();
        playerState.revokeKingsideCastling();

        assertTrue(playerState.getCastleQueensideEligible());
    }

    @Test
    public void revokeKingsideCastlingTC01_RevokeFromEligible() {
        PlayerState playerState = new PlayerState();
        playerState.revokeKingsideCastling();

        assertFalse(playerState.getCastleKingsideEligible());
        assertTrue(playerState.getCastleQueensideEligible());
    }
}
