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

    @Test
    public void validateLength_BelowLimit(){
        String player1Name = "ABCDEFGHIJKLMNOPQRSTUVWXY";
        String player2Name = "zabcdefghijklmnopqrstuvwx";

        assertDoesNotThrow(() -> PlayerNameValidator.validateLength(player1Name, player2Name));
    }

    @Test
    public void validateLength_AboveLimit(){
        String player1Name = "ABCDEFGHIJKLMNOPQRSTUVWXYZA";
        String player2Name = "zabcdefghijklmnopqrstuvwxyz";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateLength(player1Name, player2Name));

        assertEquals("Player name cannot contain more than 26 characters.", e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertEquals("Player name cannot contain more than 26 characters.", e.getErrors().get(ValidationException.Field.PLAYER_2));
    }
}
