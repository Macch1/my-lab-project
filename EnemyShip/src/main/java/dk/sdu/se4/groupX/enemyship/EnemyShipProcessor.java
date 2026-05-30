package dk.sdu.se4.groupX.enemyship;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.se4.groupX.commonenemy.Enemy;

import dk.sdu.se4.groupX.commonplayer.Player;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

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
            Enemy enemyShip = (Enemy) entity;

            // Step 1 - Move forward
            moveForward(enemyShip);

            // Step 2 - Calculate desired rotation towards player
            calculateAngleToPlayer(enemyShip, world);

            // Step 3 - Turn towards player
            turnTowardsPlayer(enemyShip);

            // Step 4 - Shoot if aimed
            shootIfAimed(enemyShip, gameData, world);

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
        enemyShip.setX(enemyShip.getX() + changeX * enemyShip.getEnemy_speedFactor());
        enemyShip.setY(enemyShip.getY() + changeY * enemyShip.getEnemy_speedFactor());
    }


    private void calculateAngleToPlayer(Enemy enemyShip, World world)
    {
        // Find the entity of type "Player".
        for (Entity player : world.getEntities(Player.class))
        {
            // Calculate the number of degrees the enemy ship needs to turn to look at the player.
            double angleToPlayer = Math.toDegrees(Math.atan2(
                    player.getY() - enemyShip.getY(),
                    player.getX() - enemyShip.getX()
            ));

            // Sets the desired rotation for the enemyship.
            enemyShip.setDesired_rotation(angleToPlayer);
        }
    }



    private void turnTowardsPlayer(Enemy enemyShip)
    {
        // Calculate how much we need to turn
        double orientation_change = (enemyShip.getRotation() - enemyShip.getDesired_rotation()) * enemyShip.getEnemy_turnFactor();

        // Clamp to max turn speed
        if (orientation_change > enemyShip.getEnemy_turnSpeed_max()) {
            orientation_change = enemyShip.getEnemy_turnSpeed_max();
        }
        if (orientation_change < -enemyShip.getEnemy_turnSpeed_max()) {
            orientation_change = -enemyShip.getEnemy_turnSpeed_max();
        }

        // Apply the rotation
        enemyShip.setRotation(enemyShip.getRotation() - orientation_change);
    }




    private void shootIfAimed(Enemy enemyShip, GameData gameData, World world)
    {
        // Calculate how far off we are from facing the player
        double angleDifference = Math.abs(enemyShip.getRotation() - enemyShip.getDesired_rotation());

        // Only shoot if facing close enough to the player and bullet is loaded
        if ((angleDifference < enemyShip.getEnemy_turnSpeed_min()) && enemyShip.isEnemy_Bullet_loaded())
        {
            // Shoot Bullet!
            getBulletSPIs().stream().findFirst().ifPresent(
                    spi -> { world.addEntity(spi.createBullet(enemyShip, gameData)); }
            );

            // Mark as unloaded, and start reload timer.
            enemyShip.setEnemy_Bullet_loaded(false);
            enemyShip.setEnemy_Reload_ticksLeft(enemyShip.getEnemy_Reload_time());
        }
        else if (!enemyShip.isEnemy_Bullet_loaded())
        {
            // Count down reload timer
            enemyShip.setEnemy_Reload_ticksLeft(enemyShip.getEnemy_Reload_ticksLeft() - 1);

            // Check if reload is complete.
            if (enemyShip.getEnemy_Reload_ticksLeft() <= 0)
            {
                // Reloaded!
                enemyShip.setEnemy_Bullet_loaded(true);
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs()
    {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
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
