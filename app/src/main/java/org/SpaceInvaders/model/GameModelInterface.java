package org.SpaceInvaders.model;
import java.util.List;

import org.SpaceInvaders.controller.GameControllerInterface;

public interface GameModelInterface {
    // Methods for manipulating the game state
    void moveShip(int dx);
    void shoot();
    void moveAliens();
    void addObserver(Observer observer);
    void increaseScore(int points);
    void notifyObservers();
    void increaseArmy(int size);
    void increaseArmyRows(int size);
    void increaseAlienSpeed( int speedIncrement);
    void createAlienArmy();
    void removeExpiredBullet();
    void updateGame();
    public String getDifficulty();
    void setGameStatus(boolean status);

    // Getters for the game state
    List<Alien> getAliens();
    Ship getShip();
    List<Bullet> getBullets();
    List<AlienProjectile> getAlienProjectiles();
    int getScore();
    int getHighScore();
    void setHighScore(int score);
    int getAlienArmy();
    int getAlienRows();
    void setDifficulty(String difficulty);
    void setController(GameControllerInterface controller);
    int getX();
    int getY();

}