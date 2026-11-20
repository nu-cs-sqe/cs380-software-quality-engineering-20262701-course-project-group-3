package domain.locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class I18nTest {
    @AfterEach
    void ResetLocale() {
        I18n.get().setLocale(Locale.ENGLISH);
    }

    @Test
    public void SetLocale_English() {
        assertEquals(Locale.ENGLISH, I18n.get().getLocale());

        I18n.get().setLocale(Locale.ENGLISH);

        assertEquals(Locale.ENGLISH, I18n.get().getLocale());
    }

    @Test
    public void SetLocale_French() {
        assertEquals(Locale.ENGLISH, I18n.get().getLocale());

        I18n.get().setLocale(Locale.FRENCH);

        assertEquals(Locale.FRENCH, I18n.get().getLocale());
    }

    @Test
    public void SetLocale_German() {
        assertEquals(Locale.ENGLISH, I18n.get().getLocale());

        I18n.get().setLocale(Locale.GERMAN);

        assertEquals(Locale.GERMAN, I18n.get().getLocale());
    }

    @Test
    public void T_UnformattedText() {
        assertEquals("Welcome to our Chess Game!", I18n.get().t("welcome.welcomeLabel"));
    }

    @Test
    public void T_NonExistent_UnformattedText() {
        assertEquals("!welcome.randomKey!", I18n.get().t("welcome.randomKey"));
    }

    @Test
    public void T_Translated_UnformattedText() {
        I18n.get().setLocale(Locale.FRENCH);
        assertEquals("Bienvenue dans notre jeu d'échecs !", I18n.get().t("welcome.welcomeLabel"));
    }

    @Test
    public void T_NonExistent_Translated_UnformattedText() {
        I18n.get().setLocale(Locale.FRENCH);
        assertEquals("!welcome.randomKey!", I18n.get().t("welcome.randomKey"));
    }

    @Test
    public void T_Unsupported_UnformattedText() {
        I18n.get().setLocale(Locale.GERMAN);
        assertEquals("Welcome to our Chess Game!", I18n.get().t("welcome.welcomeLabel"));
    }

    @Test
    public void T_Unsupported_NonExistent_UnformattedText() {
        I18n.get().setLocale(Locale.GERMAN);
        assertEquals("!welcome.randomKey!", I18n.get().t("welcome.randomKey"));
    }

    @Test
    public void T_FormattedText_OnePlaceholder_OneArgument() {
        assertEquals("Player 1: Test Player", I18n.get().t("gameStatsView.player1Name", "Test Player"));
    }

    @Test
    public void T_FormattedText_OnePlaceholder_ZeroArguments() {
        assertEquals("Player 1: {0}", I18n.get().t("gameStatsView.player1Name"));
    }

    @Test
    public void T_FormattedText_OnePlaceholder_TwoArguments() {
        assertEquals("Player 1: Test Player 1", I18n.get().t("gameStatsView.player1Name", "Test Player 1", "Test Player 2"));
    }
}
