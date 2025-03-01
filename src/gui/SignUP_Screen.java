package gui;

import dbConnection.DatabaseConnection; // Import your DatabaseConnection class
import org.mindrot.jbcrypt.BCrypt; // Import BCrypt for password hashing

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUP_Screen extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton loginButton; // Changed to JButton

    public SignUP_Screen() {
        // GUI setup code
        this.setSize(600, 450);
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(600, 50));
        topPanel.setBackground(new Color(42, 73, 23));
        this.add(topPanel, BorderLayout.NORTH);

        // Main Panel
        JPanel hPanel = new JPanel();
        hPanel.setBackground(new Color(25, 42, 73));
        hPanel.setLayout(new GridBagLayout());
        this.add(hPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Label and Text Field
        JLabel usernameLabel = new JLabel("Username");
        gbc.gridx = 0;
        gbc.gridy = 0;
        hPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        hPanel.add(usernameField, gbc);

        // Email Label and Text Field
        JLabel emailLabel = new JLabel("Email");
        gbc.gridx = 0;
        gbc.gridy = 1;
        hPanel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        hPanel.add(emailField, gbc);

        // Password Label and Text Field
        JLabel passwordLabel = new JLabel("Password");
        gbc.gridx = 0;
        gbc.gridy = 2;
        hPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        hPanel.add(passwordField, gbc);

        // Confirm Password Label and Text Field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        gbc.gridx = 0;
        gbc.gridy = 3;
        hPanel.add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        hPanel.add(confirmPasswordField, gbc);

        // Sign Up Button
        JButton signUpButton = new JButton("Sign Up");
        ImageIcon iconSign = new ImageIcon("resources/icons/login_24dp_E8EAED.png");
        signUpButton.setIcon(iconSign);
        signUpButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        hPanel.add(signUpButton, gbc);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridx = 1;
        hPanel.add(exitButton, gbc);

        // Login Button
        loginButton = new JButton("Already a member? Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setIcon(new ImageIcon("resources/icons/login_24dp_E8EAED.png")); // Set your login icon path
        loginButton.setContentAreaFilled(false); // Make the button look like a label
        loginButton.setBorderPainted(false); // Remove border
        loginButton.setFocusPainted(false); // Remove focus border
        loginButton.addActionListener(e -> {
            this.dispose(); // Close the signup screen
            new Login_Screen(); // Open the login screen
        });
        gbc.gridx = 1;
        gbc.gridy = 5;
        hPanel.add(loginButton, gbc);

        // Bottom Panel
        JPanel bPanel = new JPanel();
        bPanel.setPreferredSize(new Dimension(600, 50));
        bPanel.setBackground(new Color(26, 12, 53));
        this.add(bPanel, BorderLayout.SOUTH);

        // Make the frame visible
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Sign Up")) {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Validate input fields
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Hash the password for security
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, email);
                pstmt.setString(3, hashedPassword);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "User  registered successfully! Please login.");
                this.dispose();
                new Login_Screen(); // Open the login screen
                clearFields(); // Clear fields after successful registration
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "User  already exists or an error occurred.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to clear input fields
    private void clearFields() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }
}