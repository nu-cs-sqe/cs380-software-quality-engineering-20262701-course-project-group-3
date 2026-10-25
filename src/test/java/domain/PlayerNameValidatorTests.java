package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerNameValidatorTests {
    @Test
    public void validateLength_EmptyName(){
        String player1Name = "";
        String player2Name = "";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateLength(player1Name, player2Name));

        assertEquals("Player name cannot be empty.", e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertEquals("Player name cannot be empty.", e.getErrors().get(ValidationException.Field.PLAYER_2));
    }

    @Test
    public void validateLength_OneCharacter(){
        String player1Name = "a";
        String player2Name = "b";

        assertDoesNotThrow(() -> PlayerNameValidator.validateLength(player1Name, player2Name));
    }

    @Test
    public void validateLength_MaxLength(){
        String player1Name = "abcdefghijklmnopqrstuvwxyz";
        String player2Name = "zabcdefghijklmnopqrstuvwxy";

        assertDoesNotThrow(() -> PlayerNameValidator.validateLength(player1Name, player2Name));
    }
}
