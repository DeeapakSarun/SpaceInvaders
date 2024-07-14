package org.SpaceInvaders.controller;

import org.SpaceInvaders.App;
import org.SpaceInvaders.model.GameModel;
import org.SpaceInvaders.model.GameModelInterface;
import org.SpaceInvaders.view.FileSelector;
import org.SpaceInvaders.view.GameBoardUI;
import org.SpaceInvaders.view.GameOverScreen;
import org.SpaceInvaders.view.HomePageUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;

public class GameController implements GameControllerInterface {
    private GameModelInterface model;
    private GameBoardUI gameBoardUI;
    private HomePageUI homePageUI;
    private Timer gameTimer;
    private String selectedDifficulty;

    public GameController(GameModelInterface model) {
        this.model = model;
        this.model.setController(this);
        this.homePageUI = new HomePageUI(model, this);
        homePageUI.initialize();
        this.selectedDifficulty = "Easy";
        model.setDifficulty(selectedDifficulty);
    }

    private void initGameLoop() {
        gameTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateGame();
            }
        });
        gameTimer.start();
    }

    @Override
    public void startGame(String difficulty) {
        model.setDifficulty(difficulty);

        if (gameBoardUI == null) {
            gameBoardUI = new GameBoardUI(model, this); // Initialize the game board UI
        }

        loadHighScore();
        initGameLoop();
    }

    private void loadHighScore() {
        String filePath = "/SaveState.dat";
        try (InputStream inputStream = getClass().getResourceAsStream(filePath) ;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = reader.readLine();
            if (line != null) {
                int highScore = Integer.parseInt(line.trim());
                model.setHighScore(highScore);
                gameBoardUI.updateHighScoreLabel(highScore); // Update the high score label in the UI
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the error (e.g., display an error message)
        }
    }

    public void startNewGame() {
        App app = new App();
        app.App();
    }

    @Override
    public void stopGame() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    public void gameOver() {
        // Display "Game Over" message and stop the game
        JOptionPane.showMessageDialog(null, "Game Over", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        if (gameBoardUI != null) {
            gameBoardUI.getframe().dispose(); // Close the GameBoardUI
        }
        new GameOverScreen(this);
        stopGame();
    }

    // Method to handle ship movement
    @Override
    public void moveShip(int dx) {
        model.moveShip(dx);
    }

    // Method to handle shooting
    @Override
    public void shoot() {
        model.shoot();
    }

    public String getSelectedDifficulty() {
        return selectedDifficulty;
    }

    public void saveHighScore() {
        String filePath = "/SaveState.dat"; // Full path

        try {

            File file = new File(getClass().getResource(filePath).toURI());

            try (OutputStream outputStream = new FileOutputStream(file);
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream))) {
                writer.println(model.getHighScore());
                JOptionPane.showMessageDialog(null, "High score saved successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving high score!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public boolean loadSavedGameState() {
        // Load the high score instead of the entire game state
        return loadSavedHighScore();
    }

    public boolean loadSavedHighScore() {
        String fileName = FileSelector.selectFile();
        if (fileName != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line = reader.readLine();
                if (line != null) {
                    int highScore = Integer.parseInt(line.trim());
                    model.setHighScore(highScore);
                    return true;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading high score: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    public void setModel(GameModelInterface model) {
        this.model = model;
    }

    public GameModelInterface getModel() {
        return model;
    }
}
