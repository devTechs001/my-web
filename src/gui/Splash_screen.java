package gui;

import javax.swing.*;
import java.awt.*;

public class Splash_screen extends JWindow {

    public Splash_screen() {
        // Set the size and location of the splash screen
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);

        // Create a label for the splash screen
        JLabel label = new JLabel("Welcome to My Application", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(label, BorderLayout.CENTER);

        // Create a timer to control the splash screen duration
        Timer timer = new Timer(3000, e -> {
            // Dispose the splash screen and show the loading screen
            Splash_screen.this.dispose();
            loadingScreen();
        });
        timer.setRepeats(false); // Only run once
        timer.start();

        // Make the splash screen visible
        this.setVisible(true);
    }

    private void loadingScreen() {
        JWindow loadingWindow = new JWindow();
        loadingWindow.setSize(600, 400);
        loadingWindow.setLayout(new BorderLayout());
        loadingWindow.setLocationRelativeTo(null);

        // Top Panel
        JPanel topPnl = new JPanel();
        topPnl.setPreferredSize(new Dimension(600, 50));
        topPnl.add(new JLabel("Loading..."));
        topPnl.setBackground(new Color(0X3d3f00));
        loadingWindow.add(topPnl, BorderLayout.NORTH);

        // Middle Panel
        JPanel midPanel = new JPanel();
        midPanel.setLayout(null);
        midPanel.setPreferredSize(new Dimension(600, 300));
        loadingWindow.add(midPanel, BorderLayout.CENTER);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(20, 150, 560, 30);
        progressBar.setStringPainted(true);
        midPanel.add(progressBar);

        // Bottom Panel
        JPanel btmPanel = new JPanel();
        btmPanel.setPreferredSize(new Dimension(600, 50));
        btmPanel.setBackground(new Color(23, 56, 54));
        loadingWindow.add(btmPanel, BorderLayout.SOUTH);

        loadingWindow.setVisible(true); // Make the loading window visible

        // Use a SwingWorker to handle the loading process in the background
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50); // Simulate time-consuming task
                    publish(i); // Send the current progress to the process method
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                for (int value : chunks) {
                    progressBar.setValue(value); // Update the progress bar
                }
            }

            @Override
            protected void done() {
                loadingWindow.dispose(); // Close the loading window
                new SignUP_Screen(); // Launch the main application
            }
        };

        worker.execute(); // Start the background task
    }
}