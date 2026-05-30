package dk.sdu.se4.groupX.enemyship;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.se4.groupX.commonenemy.Enemy;

import java.util.Random;


public class EnemyShipProcessor implements IEntityProcessingService
{








    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////    IEntityProcessingService Methods    ////////////////////
    ///


    /**
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void process(GameData gameData, World world)
    {

        for (Entity entity : world.getEntities(Enemy.class))
        {
            // .
            EnemyShip enemyShip = (EnemyShip) entity;

            // Step 1 - Move forward in the direction the ship is facing
            double changeX = Math.cos(Math.toRadians(enemyShip.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemyShip.getRotation()));

            // .
            enemyShip.setX(enemyShip.getX() + changeX);
            enemyShip.setY(enemyShip.getY() + changeY);

            // Step 5 - Wrap around screen edges
            if (enemyShip.getX() < 0) {
                enemyShip.setX(gameData.getDisplayWidth());
            }
            if (enemyShip.getX() > gameData.getDisplayWidth()) {
                enemyShip.setX(0);
            }
            if (enemyShip.getY() < 0) {
                enemyShip.setY(gameData.getDisplayHeight());
            }
            if (enemyShip.getY() > gameData.getDisplayHeight()) {
                enemyShip.setY(0);
            }
        }

    }










    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///





}
