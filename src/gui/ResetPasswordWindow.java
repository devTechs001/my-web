package gui;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class ResetPasswordWindow extends JFrame implements ActionListener {
    JPanel topPanel, midPanel, bottomPanel;
    JLabel emailLabel;
    JTextField emailField;
    JButton sendEmailButton;
    JLabel timeoutCounter;
    Timer timer;
    int countdown = 30; // Countdown timer in seconds

    public ResetPasswordWindow() {
        this.setSize(600, 450);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application on exit

        topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setPreferredSize(new Dimension(600, 50));
        topPanel.setBackground(new Color(24, 45, 67));
        this.add(topPanel, BorderLayout.NORTH);

        midPanel = new JPanel();
        midPanel.setPreferredSize(new Dimension(600, 200));
        this.add(midPanel, BorderLayout.CENTER);
        midPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        emailLabel = new JLabel("Enter your email:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        midPanel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        midPanel.add(emailField, gbc);

        sendEmailButton = new JButton("Send Reset Email");
        sendEmailButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 1;
        midPanel.add(sendEmailButton, gbc);

        timeoutCounter = new JLabel("Timeout: " + countdown + " seconds");
        gbc.gridx = 1;
        midPanel.add(timeoutCounter, gbc);

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(600, 50));
        bottomPanel.setBackground(new Color(35, 56, 45));
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendEmailButton) {
            String email = emailField.getText().trim();
            if (!email.isEmpty()) {
                sendResetEmail(email);
                startCountdown(); // Start the countdown timer
            } else {
                JOptionPane.showMessageDialog(this, "Please enter your email address.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sendResetEmail(String email) {
        // Set up the email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP server
        properties.put("mail.smtp.port", "587"); // Gmail SMTP port

        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your_email@gmail.com", "your_app_password"); // Replace with your email and App Password
            }
        });

        try {
            // Create a MimeMessage
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your_email@gmail.com")); // Replace with your email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Password Reset Request");
            message.setText("To reset your password, please click the link below:\n\n[Reset Link]");

            // Send the email
            Transport.send(message);
            JOptionPane.showMessageDialog(this, "Reset email sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (MessagingException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to send email. Please try again.", "Email Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startCountdown() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (countdown > 0) {
                    countdown--;
                    timeoutCounter.setText("Timeout: " + countdown + " seconds");
                } else {
                    timer.cancel();
                    timeoutCounter.setText("Timeout expired.");
                    sendEmailButton.setEnabled(true); // Re-enable the button after timeout
                }
            }
        }, 1000, 1000); // Update every second
        sendEmailButton.setEnabled(false); // Disable the button during countdown
    }
}
//
//### Key Changes Made
//
//1. **Timeout Counter**: A JLabel has been added to display the countdown timer, initialized to 30 seconds.
//2. **Timer Implementation**: A Timer is used to update the countdown every second, and the button is disabled during this period.
//3. **Sample SMTP Server**: The SMTP server is set to Gmail's with the appropriate port and authentication details. Replace `"your_email@gmail.com"` and `"your_app_password"` with your actual email and App Password.
//
//        ### Testing the Updated Application
//
//1. **Run the Application**: Compile and run the `ResetPasswordWindow` class.
//        2. **Enter Email**: Input a valid email address and click "Send Reset Email".
//        3. **Observe Countdown**: Ensure the countdown timer starts and updates correctly.
//        4. **Check Email Functionality**: Verify that the reset email is sent successfully.
//
//This implementation provides a user-friendly way to reset passwords while managing the email sending process effectively. If you need further adjustments or additional features, feel free to ask!