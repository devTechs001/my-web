package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_Dashboard extends JFrame implements ActionListener {

    private CardLayout cardLayout; // CardLayout for the right panel
    private JPanel rightPanel; // Right panel to hold cards

    public Main_Dashboard() {
        super("Dashboard");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application on exit

        // Create panels
        JPanel topPanel = createTopPanel();
        JPanel midPanel = createMidPanel();
        JPanel bottomPanel = createBottomPanel();

        // Add panels to the frame
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(midPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // Make the frame visible
        this.setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(42, 73, 23));
        panel.setPreferredSize(new Dimension(600, 50));

        JLabel titleLabel = new JLabel("Welcome to the Dashboard");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel);

        return panel;
    }

    private JPanel createMidPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(25, 42, 73));
        panel.setLayout(new BorderLayout(5, 5));

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200, 600));
        leftPanel.setBackground(new Color(34, 56, 67));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Vertical layout for buttons

        // Example buttons for the left panel
        JButton view1Button = new JButton("View 1");
        view1Button.addActionListener(e -> showCard("View1"));
        leftPanel.add(view1Button);

        JButton view2Button = new JButton("View 2");
        view2Button.addActionListener(e -> showCard("View2"));
        leftPanel.add(view2Button);

        panel.add(leftPanel, BorderLayout.WEST);

        // Right panel for card layout
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(400, 600));

        cardLayout = new CardLayout();
        rightPanel.setLayout(cardLayout);
        rightPanel.setBackground(new Color(42, 73, 23));

        // Create cards
        JPanel card1 = new JPanel();
        card1.setBackground(new Color(34,67,76));
        card1.add(new JLabel("This is View 1"));
        rightPanel.add(card1, "View1");

        JPanel card2 = new JPanel();
        card2.setBackground(new Color(54,78,23));
        card2.add(new JLabel("This is View 2"));
        rightPanel.add(card2, "View2");

        panel.add(rightPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(600, 50));
        panel.setBackground(new Color(26, 12, 53));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            // Handle logout action
            JOptionPane.showMessageDialog(this, "Logging out...");
            dispose(); // Close the dashboard
        });
        panel.add(logoutButton);

        return panel;
    }

    private void showCard(String cardName) {
        cardLayout.show(rightPanel, cardName); // Show the specified card
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button actions here
        // This method can be used for other actions if needed
    }
}