package dk.sdu.se4.groupX.enemyship;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.se4.groupX.commonenemy.Enemy;

import dk.sdu.se4.groupX.commonplayer.Player;

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

        System.out.println("EnemyShipProcessor is running!");

        for (Entity entity : world.getEntities(Enemy.class))
        {
            // .
            Enemy enemyShip = (Enemy) entity;

            System.out.println("EnemyShipProcessor is running! 2");

            // Step 1 - Move forward in the direction the ship is facing
            double changeX = Math.cos(Math.toRadians(enemyShip.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemyShip.getRotation()));

            // .
            enemyShip.setX(enemyShip.getX() + changeX);
            enemyShip.setY(enemyShip.getY() + changeY);

            System.out.println("EnemyShipProcessor is running! 3");

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




    @Override
    public void process(GameData gameData, World world)
    {
        for (Entity entity : world.getEntities(Enemy.class))
        {
            Enemy enemyShip = (Enemy) entity;

            // Step 1 - Move forward
            moveForward(enemyShip);

            // Step 2 - Calculate desired rotation towards player
            calculateAngleToPlayer(enemyShip, world);

            // Step 3 - Turn towards player
            turnTowardsPlayer(enemyShip);

            // Step 4 - Shoot if aimed
            shootIfAimed(enemyShip, world, gameData);

            // Step 5 - Wrap around screen edges
            wrapAroundEdges(enemyShip, gameData);
        }
    }







    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    private void moveForward(Enemy enemyShip)
    {
        // .
        double changeX = Math.cos(Math.toRadians(enemyShip.getRotation()));
        double changeY = Math.sin(Math.toRadians(enemyShip.getRotation()));

        // .
        enemyShip.setX(enemyShip.getX() + changeX);
        enemyShip.setY(enemyShip.getY() + changeY);
    }


    private void calculateAngleToPlayer(Enemy enemyShip, World world)
    {
        for (Entity player : world.getEntities(Player.class))
        {
            double angleToPlayer = Math.toDegrees(Math.atan2(
                    player.getY() - enemyShip.getY(),
                    player.getX() - enemyShip.getX()
            ));
            enemyShip.setDesired_rotation(angleToPlayer);
        }
    }










    private void wrapAroundEdges(Enemy enemyShip, GameData gameData)
    {
        // .
        if (enemyShip.getX() < 0) {
            enemyShip.setX(gameData.getDisplayWidth());
        }

        // .
        if (enemyShip.getX() > gameData.getDisplayWidth()) {
            enemyShip.setX(0);
        }

        // .
        if (enemyShip.getY() < 0) {
            enemyShip.setY(gameData.getDisplayHeight());
        }

        // .
        if (enemyShip.getY() > gameData.getDisplayHeight()) {
            enemyShip.setY(0);
        }
    }









}
