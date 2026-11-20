package domain;

import domain.locale.I18n;

import java.util.EnumMap;
import java.util.Map;

public class PlayerNameValidator {
    private static final int MAX_LENGTH = 26;

    private static final String ALPHANUMERIC_REGEX = ".*[a-zA-Z0-9].*";
    private static final String ALLOWED_CHARACTERS_REGEX = "^[a-zA-Z0-9_. ]+$";

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
            errors.put(ValidationException.Field.PLAYER_1, I18n.get().t("welcome.errorDuplicate"));
            errors.put(ValidationException.Field.PLAYER_2, I18n.get().t("welcome.errorDuplicate"));
        }

        throwIfErrors(errors);
    }

    private static void checkLength(Map<ValidationException.Field, String> errors,
                                    ValidationException.Field field, String name) {
        if (name.isEmpty()) {
            errors.put(field, I18n.get().t("welcome.errorEmpty"));
        } else if (name.length() > MAX_LENGTH) {
            errors.put(field, I18n.get().t("welcome.errorTooLong", MAX_LENGTH));
        }
    }

    private static void checkCharacters(Map<ValidationException.Field, String> errors,
                                        ValidationException.Field field, String name) {
        if (!name.matches(ALPHANUMERIC_REGEX)) {
            errors.put(field, I18n.get().t("welcome.errorNoAlphanumeric"));
        } else if (!name.matches(ALLOWED_CHARACTERS_REGEX)) {
            errors.put(field, I18n.get().t("welcome.errorInvalidCharacters"));
        }
    }

    private static void throwIfErrors(Map<ValidationException.Field, String> errors)
            throws ValidationException {
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}