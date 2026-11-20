package ui;

import domain.locale.LocaleComboBoxItem;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LanguageSelectView extends JFrame {

    private final Consumer<Locale> onSelected;
    private JComboBox<LocaleComboBoxItem> localeBox;

    public LanguageSelectView(Consumer<Locale> onSelected) {
        this.onSelected = onSelected;

        setTitle("Language");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createLanguageSelectionUI();
    }

    private void createLanguageSelectionUI() {
        JPanel languagePanel = new JPanel(new GridBagLayout());
        languagePanel.setBackground(new Color(104, 76, 150)); // NU Purple 80

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Combobox (row 0)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel languageLabel = new JLabel("Select Language");
        languageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        languageLabel.setForeground(Color.WHITE);
        languagePanel.add(languageLabel, gbc);

        localeBox = new JComboBox<>(LocaleComboBoxItem.supported());
        localeBox.setFont(new Font("Arial", Font.PLAIN, 16));
        localeBox.setSelectedItem(new LocaleComboBoxItem(Locale.ENGLISH));
        gbc.gridx = 1;
        languagePanel.add(localeBox, gbc);

        // Continue button (row 1)
        JButton continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        continueButton.setOpaque(true);
        continueButton.setBackground(Color.WHITE);
        continueButton.setForeground(Color.BLACK);
        continueButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        continueButton.addActionListener(e -> {
            LocaleComboBoxItem item = (LocaleComboBoxItem) localeBox.getSelectedItem();
            if (item == null) {
                return;
            }
            setVisible(false);
            dispose();
            onSelected.accept(item.getLocale());
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        languagePanel.add(continueButton, gbc);

        add(languagePanel);
    }
}