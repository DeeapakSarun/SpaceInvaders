package org.SpaceInvaders.model;

import java.io.Serializable;

public class Ship extends GameObject implements Serializable {
    public Ship(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int dx) {
        x += dx * speed;
    }
}
