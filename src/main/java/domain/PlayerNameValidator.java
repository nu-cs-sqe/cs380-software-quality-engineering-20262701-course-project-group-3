package domain;

import java.util.EnumMap;
import java.util.Map;

public class PlayerNameValidator {
    private static final int MAX_LENGTH = 26;

    public static void validateLength(String player1Name, String player2Name) throws ValidationException {
        Map<ValidationException.Field, String> errors = new EnumMap<>(ValidationException.Field.class);

        if (player1Name.isEmpty()) {
            errors.put(ValidationException.Field.PLAYER_1, "Player name cannot be empty.");
        } else if (player1Name.length() > MAX_LENGTH) {
            errors.put(ValidationException.Field.PLAYER_1, "Player name cannot contain more than 26 characters.");
        }

        if (player2Name.isEmpty()) {
            errors.put(ValidationException.Field.PLAYER_2, "Player name cannot be empty.");
        } else if (player2Name.length() > MAX_LENGTH) {
            errors.put(ValidationException.Field.PLAYER_2, "Player name cannot contain more than 26 characters.");
        }

        if (!errors.isEmpty()) throw new ValidationException(errors);
    }

    public static void validateCharacters(String player1Name, String player2Name) throws ValidationException {
        Map<ValidationException.Field, String> errors = new EnumMap<>(ValidationException.Field.class);

        if (!player1Name.matches("^[a-zA-Z0-9_. ]+$")) {
            errors.put(ValidationException.Field.PLAYER_1, "Player name must only contain alphanumeric characters, underscores, or periods.");
        }

        if (!player2Name.matches("^[a-zA-Z0-9_. ]+$")) {
            errors.put(ValidationException.Field.PLAYER_2, "Player name must only contain alphanumeric characters, underscores, or periods.");
        }

        if (!errors.isEmpty()) throw new ValidationException(errors);
    }

    public static void validateDuplicate(String player1Name, String player2Name) throws ValidationException {
        Map<ValidationException.Field, String> errors = new EnumMap<>(ValidationException.Field.class);

        if (player1Name.equalsIgnoreCase(player2Name)) {
            errors.put(ValidationException.Field.PLAYER_1, "Player names cannot match.");
            errors.put(ValidationException.Field.PLAYER_2, "Player names cannot match.");
        }

        if (!errors.isEmpty()) throw new ValidationException(errors);

    }
}
