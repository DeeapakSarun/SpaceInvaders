package org.SpaceInvaders;

import org.SpaceInvaders.model.GameModel;
import org.SpaceInvaders.controller.GameController;
import org.SpaceInvaders.controller.GameControllerInterface;

public class App {
    public static void main(String[] args) {
        App();
    }
    public static void App() {
        GameModel model = new GameModel();
        GameControllerInterface controller = new GameController(model);
    }
}
