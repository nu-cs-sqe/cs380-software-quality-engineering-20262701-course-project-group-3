package domain;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class PlayerNameValidator {
    public static void validateLength(String player1Name, String player2Name) throws ValidationException {
        Map<ValidationException.Field, String> errors = new EnumMap<>(ValidationException.Field.class);

        if (player1Name.isEmpty()) {
            errors.put(ValidationException.Field.PLAYER_1, "Player name cannot be empty.");
        }

        if (player2Name.isEmpty()) {
            errors.put(ValidationException.Field.PLAYER_2, "Player name cannot be empty.");
        }

        if (!errors.isEmpty()) throw new ValidationException(errors);
    }
}
