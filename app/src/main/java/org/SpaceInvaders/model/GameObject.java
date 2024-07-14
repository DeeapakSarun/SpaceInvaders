
package org.SpaceInvaders.model;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int speed = 1;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void move(int dx);

    // Getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}