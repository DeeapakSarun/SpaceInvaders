package org.SpaceInvaders.view;

import javax.swing.*;
import org.SpaceInvaders.controller.GameControllerInterface;
import org.SpaceInvaders.model.GameModelInterface;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.io.Serializable;

public class HomePageUI implements Serializable {
    private JFrame frame;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JButton startGameButton;
    private GameControllerInterface controller;
    private GameModelInterface model;
    private String selectedDifficulty;

    public HomePageUI(GameModelInterface model, GameControllerInterface controller) {
        this.model = model;
        this.controller = controller;
        this.selectedDifficulty = "Easy"; // Default to Easy difficulty
    }

    public void initialize() {
        frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL imageURL = getClass().getResource("/background.jpeg");
                if (imageURL != null) {
                    Image image = new ImageIcon(imageURL).getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Space Invaders");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        easyButton = new JButton("Easy");
        easyButton.setFont(new Font("Arial", Font.PLAIN, 24));
        easyButton.setForeground(Color.WHITE);
        easyButton.setBackground(new Color(0, 144, 5));
        easyButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        easyButton.setFocusPainted(false);
        easyButton.setEnabled(false); // Disable the Easy button by default
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDifficulty = "Easy";
                easyButton.setEnabled(false); // Disable the Easy button
                mediumButton.setEnabled(true); // Enable the Medium button
                hardButton.setEnabled(true); // Enable the Hard button
            }
        });

        mediumButton = new JButton("Medium");
        mediumButton.setFont(new Font("Arial", Font.PLAIN, 24));
        mediumButton.setForeground(Color.WHITE);
        mediumButton.setBackground(new Color(255, 153, 0));
        mediumButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        mediumButton.setFocusPainted(false);
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDifficulty = "Medium";
                easyButton.setEnabled(true); // Enable the Easy button
                mediumButton.setEnabled(false); // Disable the Medium button
                hardButton.setEnabled(true); // Enable the Hard button
            }
        });

        hardButton = new JButton("Hard");
        hardButton.setFont(new Font("Arial", Font.PLAIN, 24));
        hardButton.setForeground(Color.WHITE);
        hardButton.setBackground(new Color(255, 51, 51));
        hardButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        hardButton.setFocusPainted(false);
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDifficulty = "Hard";
                easyButton.setEnabled(true); // Enable the Easy button
                mediumButton.setEnabled(true); // Enable the Medium button
                hardButton.setEnabled(false); // Disable the Hard button
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        // Create and configure the Start Game button
        startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Arial", Font.PLAIN, 24));
        startGameButton.setForeground(Color.WHITE);
        startGameButton.setBackground(Color.BLUE);
        startGameButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        startGameButton.setFocusPainted(false);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(selectedDifficulty); // Start the game with the selected difficulty
            }
        });

        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel2.setOpaque(false);
        buttonPanel2.add(startGameButton); // Add the Start Game button

        backgroundPanel.add(buttonPanel2, BorderLayout.SOUTH);

        frame.setContentPane(backgroundPanel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startGame(String difficulty) {
        // Notify the controller to start the game with the selected difficulty
        controller.startGame(difficulty);
        // Close the front page
        frame.dispose();
    }

    public void closeFrame() {
        frame.dispose();
    }
}
