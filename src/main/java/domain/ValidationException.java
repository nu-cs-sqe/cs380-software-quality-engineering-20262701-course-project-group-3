package domain;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class ValidationException extends Exception {

    public enum Field { PLAYER_1, PLAYER_2 }

    private final Map<Field, String> errors;

    public ValidationException(Map<Field, String> errors) {
        super("Validation failed: " + errors);
        this.errors = Collections.unmodifiableMap(new EnumMap<>(errors));
    }

    public Map<Field, String> getErrors() {
        return errors;
    }
}