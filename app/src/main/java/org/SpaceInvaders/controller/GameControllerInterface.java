package org.SpaceInvaders.controller;

public interface GameControllerInterface {

    void startGame(String difficulty);
    void stopGame();
    void gameOver();
    void moveShip(int dx);
    void shoot();

    String getSelectedDifficulty(); 
    boolean loadSavedGameState(); 
}
