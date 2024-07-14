package org.SpaceInvaders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.SpaceInvaders.controller.GameControllerInterface;

import java.util.Iterator;
import java.io.*;

public class GameModel implements GameModelInterface,Serializable{
    private List<Observer> observers;
    private List<Alien> aliens;
    private List<Bullet> bulletsToRemove;
    private List<AlienProjectile> alienProjectiles;
    private GameControllerInterface controller;
    private Ship ship;
    private List<Bullet> bullets;
    private int score;
    private int bulletLimit;
    public int x;
    public int y;
    private String difficulty;
    private int alienArmy;
    private int alienRows;
    private Boolean gameOver;
    private Timer alienAttackTimer;
    private Random random;
    private int highScore;

    public GameModel() {
        this.score = 0;
        this.x=600;
        this.y=600;
        this.gameOver = false;
        this.random = new Random();
        this.observers = new ArrayList<>();
        this.aliens = new ArrayList<>();
        this.bulletsToRemove = new ArrayList<>();
        this.alienProjectiles = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.highScore = 0;
        ship = new Ship((this.x /2)-10, this.y - 40); // Starting position of the ship at the bottom center

    }

    @Override
    public void createAlienArmy() {
        aliens.clear();
        for (int i = 0; i < alienArmy; i++) {
            for (int j = 0; j < alienRows; j++) {
                aliens.add(new Alien(35 + i * 35, 40 + j * 40));
            }
            
        }
    }


    @Override
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        adjustGameParameters(); // Adjust game parameters based on the selected difficulty
        createAlienArmy();
    }

    public String getDifficulty(){
        return this.difficulty;
    }

    private void adjustGameParameters() {
        switch (difficulty) {
            case "Easy":
                ship.setSpeed(1);
                alienArmy = 8;
                bulletLimit = 10;
                this.alienRows = 1;
                increaseAlienSpeed(1);
                initAlienAttackTimer(5);
                break;
            case "Medium":
                ship.setSpeed(2);
                this.alienArmy = 10;
                bulletLimit = 8;
                this.alienRows = 2;
                increaseAlienSpeed(2);
                initAlienAttackTimer(3);
                break;
            case "Hard":
                ship.setSpeed(3);
                alienArmy = 10;
                bulletLimit = 5;
                this.alienRows = 3;
                increaseAlienSpeed(3);
                initAlienAttackTimer(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void moveShip(int dx) {
        ship.move(dx);
        notifyObservers();
    }

    
    @Override
    public void shoot() {
        if(bullets.size() < bulletLimit) {
            bullets.add(new Bullet(ship.getX()+14, ship.getY() - 15)); // Bullet starts above the ship
        }
        notifyObservers();
    }

    private void initAlienAttackTimer(int interval) {
        alienAttackTimer = new Timer(interval * 1000, e -> {
            int randomIndex = random.nextInt(aliens.size());
            Alien randomAlien = aliens.get(randomIndex);
            randomAlienAttack(randomAlien);
        });
        alienAttackTimer.start();
    } 

    private void randomAlienAttack(Alien alien) {
        alienAttack(alien); // Perform the alien attack
        notifyObservers(); // Notify observers of the attack
    }

    public void alienAttack(Alien alien) {

        int projectileX = alien.getX() + 10; // Assuming the projectile starts from the middle of the alien horizontally
        int projectileY = alien.getY() + 10; // Assuming the projectile starts just below the bottom edge of the alien
        //System.out.println("alien " + projectileX + "," +  projectileY);
        AlienProjectile projectile = new AlienProjectile(projectileX, projectileY); // Creating the projectile
        alienProjectiles.add(projectile);

    }
    public void moveProjectile() {
        for (AlienProjectile projectile : alienProjectiles) {
            projectile.move(20); // Assuming speed 1 for now, adjust as needed
        }
    }
    
    public void checkCollision() {
        List <Bullet> bullets = getBullets();
        List <Alien> aliens = getAliens();
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            for (int j = aliens.size() - 1; j >= 0; j--) {
                Alien alien = aliens.get(j);
                if (bullet.getX() >= alien.getX()-2 && bullet.getX() <= alien.getX() + 22 &&
                    bullet.getY() >= alien.getY()-2 && bullet.getY() <= alien.getY() + 22) {
                        increaseScore(1); // Increase score by 1 when an alien is hit
                        bullets.remove(i); // Remove the bullet
                        aliens.remove(j); // Remove the alien
                        break; // Exit inner loop once a collision is detected
                }
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }    

    public void checkProjectileCollision() {
        List <AlienProjectile> projectiles = getAlienProjectiles();
        Ship ship = getShip();

        for (int i = projectiles.size() - 1; i >= 0; i--) {
            if (!projectiles.isEmpty() && !gameOver){

                AlienProjectile projectile = projectiles.get(i);
                    if (projectile.getX() >= ship.getX() && projectile.getX() <= ship.getX() + 20 &&
                        projectile.getY() >= ship.getY() && projectile.getY() <= ship.getY() + 20) {
                            //decreaseHealth(1);
                            gameOver = true;
                            break; // Exit inner loop once a collision is detected
                    }
            }
        }
    }

    public void updateGame() {
        moveAliens();
        moveProjectile();
        getBullets();
        removeExpiredBullet();
        checkCollision();
        checkProjectileCollision();
        checkGameStatus();
        notifyObservers();

        if (score > highScore) {
            highScore = score;
        }
    }




    @Override
    public void moveAliens() {
        boolean moveDown = false; 

        for (Alien alien : aliens) {
            if(alien.getX() >= this.x - 20 || alien.getX() <= 20){
                moveDown = true;
                break;
            }
        }
        notifyObservers();

        if (moveDown) {

            for (Alien alien : aliens) {
                alien.moveDown();
            }
            notifyObservers();

            for (Alien alien : aliens) {
                alien.reverseDirection();
                alien.move(alienArmy);
            }
            notifyObservers();
        } else {
            for (Alien alien : aliens) {
                alien.move(alienArmy); 
            }
            notifyObservers();
        }

        for(Bullet bullet : bullets){
            bullet.move(ship.getX()+10);
        }

    }

    public void checkGameStatus() {
        for (Alien alien : getAliens()) {
            if (alien.getY() + 20 >= getShip().getY() || gameOver) { // If any alien reaches the ship
                gameOver = true;
                controller.gameOver();
                break;
            }
        }if (getAliens().isEmpty()) { // If all aliens are destroyed
            increaseArmy(1);
            if(getAlienArmy() > 14){
                increaseArmyRows(1);
                bulletLimit += 2;
            }
            if(getAlienArmy() > 14 && getAlienRows() > 5) {
                increaseAlienSpeed(1);
            }
            createAlienArmy();
        }
    }

    @Override
    public void removeExpiredBullet() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            if (bullet.getY() < 0) { // If bullet goes above the game board
                bulletsToRemove.add(bullet);
                iterator.remove();
                break; // Break after removing one bullet to ensure only one bullet is removed at a time
            }
        }
        notifyObservers();
    }

    @Override
    public int getScore(){
        return score;
    }
    

    public void increaseScore(int points) {
        if (points > 0) {
            this.score += points;
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int score) {
        this.highScore = score;
    }

    @Override
    public void increaseArmy(int size) {
        if (size > 0) {
            this.alienArmy += size;
        }
    }

    @Override
    public int getAlienArmy(){
        return alienArmy;
    }

    @Override
    public int getAlienRows(){
        return alienArmy;
    } 

    @Override
    public void increaseAlienSpeed(int speedIncrement){
        for (Alien alien : aliens) {
            // System.out.println(alien.getSpeed());
            alien.increaseSpeed(speedIncrement);
            System.out.println("after increase " + alien.getXSpeed());
        }
    }

    @Override
    public void increaseArmyRows(int size) {
        this.alienRows += size;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void setController(GameControllerInterface controller) {
        this.controller = controller;
    }

    @Override
    public int getX(){
        return x;
    }

    @Override
    public int getY(){
        return y;
    }

    // Getters for aliens, ship, bullets...
    @Override
    public List<Alien> getAliens() {
        return aliens;
    }

    @Override
    public Ship getShip() {
        return ship;
    }

    @Override
    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<AlienProjectile> getAlienProjectiles() {
        return alienProjectiles;
    }

    public void setGameStatus(boolean status){
        this.gameOver = status;
    }
}