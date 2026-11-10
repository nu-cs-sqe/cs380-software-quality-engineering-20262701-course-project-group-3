package domain;

import java.util.EnumMap;
import java.util.Map;

public class PlayerNameValidator {
    private static final int MAX_LENGTH = 26;

    private static final String ALPHANUMERIC_REGEX = ".*[a-zA-Z0-9].*";
    private static final String ALLOWED_CHARACTERS_REGEX = "^[a-zA-Z0-9_. ]+$";

    private static final String MSG_EMPTY = "Player name cannot be empty.";
    private static final String MSG_TOO_LONG =
            "Player name cannot contain more than " + MAX_LENGTH + " characters.";
    private static final String MSG_NO_ALPHANUMERIC =
            "Player name must contain at least one alphanumeric character.";
    private static final String MSG_INVALID_CHARACTERS =
            "Player name must only contain alphanumeric characters, "
                    + "spaces, underscores, or periods.";
    private static final String MSG_DUPLICATE = "Player names cannot match.";

    public static void validateLength(String player1Name, String player2Name)
            throws ValidationException {
        Map<ValidationException.Field, String> errors =
                new EnumMap<>(ValidationException.Field.class);

        checkLength(errors, ValidationException.Field.PLAYER_1, player1Name);
        checkLength(errors, ValidationException.Field.PLAYER_2, player2Name);

        throwIfErrors(errors);
    }

    public static void validateCharacters(String player1Name, String player2Name)
            throws ValidationException {
        Map<ValidationException.Field, String> errors =
                new EnumMap<>(ValidationException.Field.class);

        checkCharacters(errors, ValidationException.Field.PLAYER_1, player1Name);
        checkCharacters(errors, ValidationException.Field.PLAYER_2, player2Name);

        throwIfErrors(errors);
    }

    public static void validateDuplicate(String player1Name, String player2Name)
            throws ValidationException {
        Map<ValidationException.Field, String> errors =
                new EnumMap<>(ValidationException.Field.class);

        if (player1Name.equalsIgnoreCase(player2Name)) {
            errors.put(ValidationException.Field.PLAYER_1, MSG_DUPLICATE);
            errors.put(ValidationException.Field.PLAYER_2, MSG_DUPLICATE);
        }

        throwIfErrors(errors);
    }

    private static void checkLength(Map<ValidationException.Field, String> errors,
                                    ValidationException.Field field, String name) {
        if (name.isEmpty()) {
            errors.put(field, MSG_EMPTY);
        } else if (name.length() > MAX_LENGTH) {
            errors.put(field, MSG_TOO_LONG);
        }
    }

    private static void checkCharacters(Map<ValidationException.Field, String> errors,
                                        ValidationException.Field field, String name) {
        if (!name.matches(ALPHANUMERIC_REGEX)) {
            errors.put(field, MSG_NO_ALPHANUMERIC);
        } else if (!name.matches(ALLOWED_CHARACTERS_REGEX)) {
            errors.put(field, MSG_INVALID_CHARACTERS);
        }
    }

    private static void throwIfErrors(Map<ValidationException.Field, String> errors)
            throws ValidationException {
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}