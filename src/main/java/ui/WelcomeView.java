package ui;

import domain.PlayerNameValidator;
import domain.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class WelcomeView extends JFrame {

    private JTextField player1NameField;
    private JTextField player2NameField;
    private JLabel player1ErrorLabel;
    private JLabel player2ErrorLabel;

    public WelcomeView() {
        setTitle("Welcome Screen!");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createWelcomeScreenUI();
    }

    private void createWelcomeScreenUI() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(new Color(104, 76, 150)); // NU Purple 80

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome to our Chess Game!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        welcomePanel.add(welcomeLabel, gbc);

        // Player 1 Name Input (row 1)
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel player1NameLabel = new JLabel("Player 1 Name:");
        player1NameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        player1NameLabel.setForeground(Color.WHITE);
        welcomePanel.add(player1NameLabel, gbc);
        player1NameField = new JTextField(20);
        player1NameField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        welcomePanel.add(player1NameField, gbc);

        // Fixed width for error labels = width of the text fields
        final int errorWidth = player1NameField.getPreferredSize().width;

        // Separate constraints for error rows so they don't alter gbc
        GridBagConstraints errorGbc = new GridBagConstraints();
        errorGbc.gridx = 1;
        errorGbc.gridwidth = 1;
        errorGbc.anchor = GridBagConstraints.NORTHWEST;
        errorGbc.fill = GridBagConstraints.NONE; // don't stretch to fit the text
        errorGbc.insets = new Insets(0, 10, 5, 10);

        // Player 1 error (row 2)
        player1ErrorLabel = createErrorLabel();
        errorGbc.gridy = 2;
        welcomePanel.add(player1ErrorLabel, errorGbc);

        // Player 2 Name Input (row 3)
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel player2NameLabel = new JLabel("Player 2 Name:");
        player2NameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        player2NameLabel.setForeground(Color.WHITE);
        welcomePanel.add(player2NameLabel, gbc);
        player2NameField = new JTextField(20);
        player2NameField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        welcomePanel.add(player2NameField, gbc);

        // Player 2 error (row 4)
        player2ErrorLabel = createErrorLabel();
        errorGbc.gridy = 4;
        welcomePanel.add(player2ErrorLabel, errorGbc);

        // Start Game Button (row 5)
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 20));
        startGameButton.setOpaque(true);
        startGameButton.setBackground(Color.WHITE);
        startGameButton.setForeground(Color.BLACK);

        startGameButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        startGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        startGameButton.addActionListener(e -> {
            // Get player names
            String player1Name = player1NameField.getText().trim();
            String player2Name = player2NameField.getText().trim();

            try {
                PlayerNameValidator.validateLength(player1Name, player2Name);
                PlayerNameValidator.validateCharacters(player1Name, player2Name);
                PlayerNameValidator.validateDuplicate(player1Name, player2Name);
            } catch (ValidationException exception) {
                Map<ValidationException.Field, String> errors = exception.getErrors();

                setError(player1ErrorLabel, errors.get(ValidationException.Field.PLAYER_1), errorWidth);
                setError(player2ErrorLabel, errors.get(ValidationException.Field.PLAYER_2), errorWidth);
                return;
            }

            // Validation passed: clear any old errors
            setError(player1ErrorLabel, null, errorWidth);
            setError(player2ErrorLabel, null, errorWidth);

            // Hide the welcome screen
            setVisible(false);
            dispose(); // Dispose of this frame to free up resources

            // Create and show the main game screen
            MainView mainScreen = new MainView(player1Name, player2Name);
            mainScreen.setVisible(true);
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Span two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        welcomePanel.add(startGameButton, gbc);

        add(welcomePanel);
    }

    private JLabel createErrorLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(new Color(255, 75, 75)); // light red, readable on purple
        label.setVisible(false);
        return label;
    }

    private void setError(JLabel label, String message, int width) {
        if (message == null || message.isBlank()) {
            label.setText("");
            label.setVisible(false);
        } else {
            // The fixed-width div makes the HTML label wrap instead of growing
            label.setText("<html><div style='width:" + width + "px'>"
                    + escapeHtml(message) + "</div></html>");
            label.setVisible(true);
        }
        label.revalidate();
        label.repaint();
    }

    private String escapeHtml(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}