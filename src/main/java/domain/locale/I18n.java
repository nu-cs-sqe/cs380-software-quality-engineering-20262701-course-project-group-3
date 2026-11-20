package domain.locale;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class I18n {
    private static final String BASE_NAME = "i18n.messages";
    private static final I18n INSTANCE = new I18n();

    private Locale locale = Locale.ENGLISH;
    private ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale);

    private I18n() {
    }

    public static I18n get() {
        return INSTANCE;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale newLocale) {
        this.locale = newLocale;
        this.bundle = ResourceBundle.getBundle(BASE_NAME, newLocale);
    }

    public String t(String key, Object... args) {
        String pattern;
        try {
            pattern = bundle.getString(key);
        } catch (MissingResourceException e) {
            return "!" + key + "!";
        }
        return args.length == 0 ? pattern : new MessageFormat(pattern, locale).format(args);
    }
}