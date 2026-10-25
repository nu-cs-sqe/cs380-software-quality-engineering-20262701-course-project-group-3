package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerNameValidatorTests {
    @Test
    public void validateLength_EmptyName() {
        PlayerNameValidator pnv = new PlayerNameValidator();
        String player1Name = "";
        String player2Name = "";

        ValidationResult result = pnv.validateLength(player1Name, player2Name);

        assertFalse(result.success);
        assertEquals(result.errors.player1, "Player name cannot be empty.");
        assertEquals(result.errors.player2, "Player name cannot be empty.");
    }
}
