package org.SpaceInvaders.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AlienProjectile extends GameObject implements Serializable {
    //private static final long serialVersionUID = 1L;
    private static final long serialVersionUID = 3442814914794105162L;
    public AlienProjectile() {
        super(0, 0); // Call the superclass constructor with default values or adjust as needed
    }
    
    public AlienProjectile(int x, int y) {
        super(x, y);
    }

    // Manually serialize the object
    private void writeObject(ObjectOutputStream out) throws IOException {
        // Write the values of x and y to the stream
        out.writeInt(x);
        out.writeInt(y);
    }

    // Manually deserialize the object
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // Read the values of x and y from the stream
        x = in.readInt();
        y = in.readInt();
    }

    @Override
    public void move(int dx) {
        y += dx; // Move bullet upwards
    }
}

