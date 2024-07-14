package org.SpaceInvaders.model;

import java.io.Serializable;

public class Bullet extends GameObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private int dy;

    public Bullet(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int dy) {
        y -= 20; // Move bullet upwards
    }

    public void increaseSpeed(int speed) {
        this.dy += speed;
    }
}
