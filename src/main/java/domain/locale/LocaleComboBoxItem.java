package domain.locale;

import java.util.Locale;
import java.util.Objects;

public class LocaleComboBoxItem {
    private final Locale locale;

    public LocaleComboBoxItem(Locale locale) {
        this.locale = Objects.requireNonNull(locale);
    }

    public Locale getLocale() {
        return locale;
    }

    public static LocaleComboBoxItem[] supported() {
        return new LocaleComboBoxItem[] {
            new LocaleComboBoxItem(Locale.ENGLISH),
            new LocaleComboBoxItem(Locale.FRENCH)
        };
    }

    @Override
    public String toString() {
        return locale.getDisplayLanguage();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof LocaleComboBoxItem && ((LocaleComboBoxItem) o).locale.equals(locale);
    }

    @Override
    public int hashCode() {
        return locale.hashCode();
    }
}
