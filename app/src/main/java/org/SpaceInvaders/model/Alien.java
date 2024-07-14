package org.SpaceInvaders.model;

import java.io.Serializable;

public class Alien extends GameObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private int dx = 2;
    private int dy = 6;
    private int direction = 1;
    private int speed = 1; // Add speed field

    public Alien(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int dx) {
        x += dx * speed * direction;
    }

    public void moveDown() {
        y += dy * speed;
    }

    public void reverseDirection() {
        this.direction *= -1; // Reverse the horizontal movement direction
    }

    public void increaseSpeed(int speedIncrement) {
        this.dx += speedIncrement;
        this.dy += speedIncrement;
    }

    public int getXSpeed() {
        return this.dx;
    }
}
