package org.SpaceInvaders;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.SpaceInvaders.model.Alien;
import org.SpaceInvaders.model.GameModel;

public class AppTest {
    private GameModel gameModel;

    @Before
    public void setUp() {
        gameModel = new GameModel();

    }

    @Test
    public void testSetDifficultyEasy() {
        gameModel.setDifficulty("Easy");
        assertEquals("Difficulty should be set to Easy", "Easy", gameModel.getDifficulty());
    }

    @Test
    public void testSetDifficultyMedium() {
        gameModel.setDifficulty("Medium");
        assertEquals("Difficulty should be set to Medium", "Medium", gameModel.getDifficulty());
    }

    @Test
    public void testSetDifficultyHard() {
        gameModel.setDifficulty("Hard");
        assertEquals("Difficulty should be set to Hard", "Hard", gameModel.getDifficulty());
    }

    @Test
    public void testIncreaseScore() {
        
        assertEquals("Initial score should be 0", 0, gameModel.getScore());
        
        gameModel.increaseScore(10);
        assertEquals("Score should increase by 10", 10, gameModel.getScore());
        
        gameModel.increaseScore(-5);
        assertEquals("Score should remain unchanged for negative increment", 10, gameModel.getScore());
        
        gameModel.increaseScore(0);
        assertEquals("Score should remain unchanged for increment of 0", 10, gameModel.getScore());
    }

    @Test
    public void testMoveShip() {
        int initialX = gameModel.getShip().getX();
        int dx = 10;
        
        gameModel.moveShip(dx);
        
        assertEquals("Ship's x-coordinate should be updated correctly", initialX + dx, gameModel.getShip().getX());
    }

    @Test
    public void testCreateAlienArmy() {
        gameModel.setDifficulty("Easy");
        gameModel.createAlienArmy();
        assertEquals("Alien army size should match the specified difficulty", 8, gameModel.getAliens().size());

        gameModel.setDifficulty("Medium");
        gameModel.createAlienArmy();
        assertEquals("Alien army size should match the specified difficulty", 20, gameModel.getAliens().size());

        gameModel.setDifficulty("Hard");
        gameModel.createAlienArmy();
        assertEquals("Alien army size should match the specified difficulty", 30, gameModel.getAliens().size());
    }

    @Test
    public void testIncreaseArmy() {
        assertEquals("Initial alien army size should be 0", 0, gameModel.getAlienArmy());

        gameModel.increaseArmy(5);
        assertEquals("Alien army size should increase by 5", 5, gameModel.getAlienArmy());

        gameModel.increaseArmy(-3);
        assertEquals("Alien army size should remain unchanged for negative increment", 5, gameModel.getAlienArmy());

        gameModel.increaseArmy(0);
        assertEquals("Alien army size should remain unchanged for increment of 0", 5, gameModel.getAlienArmy());
    }


    
}
