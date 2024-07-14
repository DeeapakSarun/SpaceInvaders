package org.SpaceInvaders.view;

import org.SpaceInvaders.controller.GameController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {
    private JFrame frame;
    private GameController controller;

    public GameOverScreen(GameController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Game Over");
        frame.setLayout(new BorderLayout());

        JPanel gameOverPanel = new JPanel(new GridLayout(2, 1));
        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverPanel.add(gameOverLabel);

        JButton newGameButton = new JButton("Start New Game");
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the game over screen
                //String difficulty = controller.getSelectedDifficulty();
                controller.startNewGame();
            }
        });

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to save the high score?", "Quit", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    controller.saveHighScore();
                    System.exit(0); // Quit the game after saving
                } else if (option == JOptionPane.NO_OPTION) {
                    System.exit(0); // Quit the game without saving
                }
                // If the user selects cancel, do nothing
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(newGameButton);
        buttonPanel.add(quitButton);

        gameOverPanel.add(buttonPanel);

        frame.add(gameOverPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose the frame when closed
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
