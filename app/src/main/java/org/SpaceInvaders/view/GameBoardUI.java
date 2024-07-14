package org.SpaceInvaders.view;

import org.SpaceInvaders.model.GameModelInterface;
import org.SpaceInvaders.model.Observer;
import org.SpaceInvaders.controller.GameControllerInterface;
import org.SpaceInvaders.model.Alien;
import org.SpaceInvaders.model.Ship;
import org.SpaceInvaders.model.Bullet;
import org.SpaceInvaders.model.AlienProjectile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameBoardUI extends JPanel implements Observer {
    private GameModelInterface model;
    private JFrame frame;
    private GameControllerInterface controller;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    
    private BufferedImage shipImage;
    private BufferedImage alienImage;

    public GameBoardUI(GameModelInterface model, GameControllerInterface controller) {
        this.model = model;
        this.controller = controller;
        model.addObserver(this);
        setPreferredSize(new Dimension(model.getX(), model.getY())); // Make sure these dimensions are correct
        setBackground(Color.BLACK);
        initializeUI();

        // Load images
        try {
            shipImage = ImageIO.read(getClass().getResourceAsStream("/ship.png"));
            alienImage = ImageIO.read(getClass().getResourceAsStream("/alien.jpeg"));
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    private void initializeUI() {
        frame = new JFrame("Space Invaders");
        frame.setLayout(new BorderLayout());
    
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        scorePanel.setBackground(Color.BLACK);
    
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.GREEN);
        scorePanel.add(scoreLabel);
    
        highScoreLabel = new JLabel("High Score: 0");
        highScoreLabel.setForeground(Color.GREEN);
        scorePanel.add(highScoreLabel);
    
        frame.add(scorePanel, BorderLayout.NORTH);
        frame.add(this, BorderLayout.CENTER);
    
        frame.pack(); // Adjust frame to fit the preferred size and layouts of its subcomponents
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT && model.getShip().getX() > 10) {
                    controller.moveShip(-10);
                } else if (key == KeyEvent.VK_RIGHT && model.getShip().getX() < model.getX() - 30) {
                    controller.moveShip(10);
                } else if (key == KeyEvent.VK_SPACE) {
                    controller.shoot();
                }
            }
        });
        setFocusable(true);  // Make this panel focusable to receive key inputs
        requestFocusInWindow();  // Request focus to ensure key inputs are directed to this panel
    }

    public JFrame getframe() {
        return frame;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawShip(g);
        drawAliens(g);
        drawBullets(g);
        drawAlienProjectile(g); 
    }


    private void drawShip(Graphics g) {
        Ship ship = model.getShip();
        g.drawImage(shipImage, ship.getX(), ship.getY(), 30, 30, null);
    }
    
    
    private void drawAliens(Graphics g) {
        for (Alien alien : model.getAliens()) {
            g.drawImage(alienImage, alien.getX(), alien.getY(), 30, 30, null);
        }
    }

    private void drawBullets(Graphics g) {
        g.setColor(Color.WHITE);
        for (Bullet bullet : model.getBullets()) {
            g.fillRect(bullet.getX(), bullet.getY(), 2, 10);
        }
    }

    private void drawAlienProjectile(Graphics g) {
        g.setColor(Color.RED);
        for (AlienProjectile projectile : model.getAlienProjectiles()) {
            g.fillRect(projectile.getX(), projectile.getY(), 2, 5);
        }
    }

    @Override
    public void update() {
        scoreLabel.setText("Score: " + model.getScore());
        highScoreLabel.setText("HighScore: "+ model.getHighScore());
        repaint();
    }

    public void updateHighScoreLabel(int highScore) {
        highScoreLabel.setText("High Score: " + highScore);
    }
}