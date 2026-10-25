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

    @Test
    public void validateLength_AboveLimit_Player1(){
        String player1Name = "ABCDEFGHIJKLMNOPQRSTUVWXYZA";
        String player2Name = "abcdefghijklmnopqrstuvwxyz";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateLength(player1Name, player2Name));

        assertEquals("Player name cannot contain more than 26 characters.", e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertNull(e.getErrors().get(ValidationException.Field.PLAYER_2));
    }

    @Test
    public void validateCharacters_OneSymbol(){
        String player1Name = "abc!";
        String player2Name = "def#";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateCharacters(player1Name, player2Name));

        assertEquals("Player name must only contain alphanumeric characters or underscores.", e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertEquals("Player name must only contain alphanumeric characters or underscores.", e.getErrors().get(ValidationException.Field.PLAYER_2));
    }

    @Test
    public void validateCharacters_MultipleSymbols_Player1(){
        String player1Name = "helloworld123$%^()";
        String player2Name = "worldhello456";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateCharacters(player1Name, player2Name));

        assertEquals("Player name must only contain alphanumeric characters or underscores.", e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertNull(e.getErrors().get(ValidationException.Field.PLAYER_2));
    }

    @Test
    public void validateCharacters_SpecialSymbols_Player2(){
        String player1Name = "helloworld123";
        String player2Name = "ŵôřłďħëļľø¢∞§¡™£“‘";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateCharacters(player1Name, player2Name));

        assertNull(e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertEquals("Player name must only contain alphanumeric characters or underscores.", e.getErrors().get(ValidationException.Field.PLAYER_2));
    }

    @Test
    public void validateCharacters_TerminalCharacters(){
        String player1Name = "09AZaz";
        String player2Name = "z0A9Za";

        assertDoesNotThrow(() -> PlayerNameValidator.validateCharacters(player1Name, player2Name));
    }

    @Test
    public void validateCharacters_DigitAdjacentCharacters(){
        String player1Name = "123/";
        String player2Name = "789:";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateCharacters(player1Name, player2Name));

        assertEquals("Player name must only contain alphanumeric characters or underscores.", e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertEquals("Player name must only contain alphanumeric characters or underscores.", e.getErrors().get(ValidationException.Field.PLAYER_2));
    }

    @Test
    public void validateCharacters_UppercaseAdjacentCharacters(){
        String player1Name = "ABC@";
        String player2Name = "XYZ[";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateCharacters(player1Name, player2Name));

        assertEquals("Player name must only contain alphanumeric characters or underscores.", e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertEquals("Player name must only contain alphanumeric characters or underscores.", e.getErrors().get(ValidationException.Field.PLAYER_2));
    }

    @Test
    public void validateCharacters_LowercaseAdjacentCharacters(){
        String player1Name = "abc`";
        String player2Name = "abc{";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateCharacters(player1Name, player2Name));

        assertEquals("Player name must only contain alphanumeric characters or underscores.", e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertEquals("Player name must only contain alphanumeric characters or underscores.", e.getErrors().get(ValidationException.Field.PLAYER_2));
    }

    @Test
    public void validateCharacters_Underscore_Player1(){
        String player1Name = "abc_def";
        String player2Name = "worldhello456";

        assertDoesNotThrow(() -> PlayerNameValidator.validateCharacters(player1Name, player2Name));
    }

    @Test
    public void validateCharacters_InternalWhitespace_Player1(){
        String player1Name = "hello world 123";
        String player2Name = "worldhello456";

        assertDoesNotThrow(() -> PlayerNameValidator.validateCharacters(player1Name, player2Name));
    }

    @Test
    public void validateCharacters_LeadingTrailingWhitespace_Player2(){
        String player1Name = "helloworld123";
        String player2Name = " worldhello456 ";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateCharacters(player1Name, player2Name));

        assertNull(e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertEquals("Player name cannot contain leading/trailing whitespace.", e.getErrors().get(ValidationException.Field.PLAYER_2));
    }

    @Test
    public void validateCharacters_SingleWhitespace_Player1(){
        String player1Name = " ";
        String player2Name = "worldhello456";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateCharacters(player1Name, player2Name));

        assertEquals("Player name cannot contain leading/trailing whitespace.", e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertNull(e.getErrors().get(ValidationException.Field.PLAYER_2));
    }

    @Test
    public void validateDuplicate_DuplicateNames(){
        String player1Name = "helloworld123";
        String player2Name = "helloworld123";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateDuplicate(player1Name, player2Name));

        assertEquals("Player names cannot match.", e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertEquals("Player names cannot match.", e.getErrors().get(ValidationException.Field.PLAYER_2));
    }

    @Test
    public void validateDuplicate_DuplicateNamesMixedCase(){
        String player1Name = "HelloWorld123";
        String player2Name = "helloworld123";

        ValidationException e = assertThrows(ValidationException.class, () -> PlayerNameValidator.validateDuplicate(player1Name, player2Name));

        assertEquals("Player names cannot match.", e.getErrors().get(ValidationException.Field.PLAYER_1));
        assertEquals("Player names cannot match.", e.getErrors().get(ValidationException.Field.PLAYER_2));
    }
}
