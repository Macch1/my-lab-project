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
            Enemy enemyShip = (Enemy) entity;

            // Step 0 - Check Health
            if (this.handleHealth(enemyShip, world))
            {
                continue; // skip dead entities
            }

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


    /**
     *
     * @param enemy
     * @param world
     * @return
     */
    private boolean handleHealth(Entity enemy, World world)
    {
        // If the health of the Enemy is zero or below, we define the Enemy as "Dead" / "Destroyed".
        // If the v is "Dead" / "Destroyed" it gets handled below.
        if (enemy.Get_Health() <= 0)
        {
            // Remove the "Enemy" entity from the "world".
            world.removeEntity(enemy);

            // Notify ScoringService.
            try
            {
                /**
                // Create a RestTemplate to make HTTP requests to the ScoringService.
                RestTemplate restTemplate = new RestTemplate();

                // Notify the ScoringService that one more EnemyShip has been destroyed.
                restTemplate.put("http://localhost:8080/enemyship/add?point=1", null);

                // Notify the ScoringService to add 50 points to the total score.
                restTemplate.put("http://localhost:8080/score/add?point=50", null);
                 **/
            }
            catch (Exception e)
            {
                System.out.println("ScoringService not available: " + e.getMessage());
            }

            // returns "true" to indicate the Enemy is "Dead" / "Destroyed".
            return true;
        }

        // returns "false" to indicate the Enemy is "Alive" / "Not destroyed".
        return false;
    }



    /**
     *
     * @param enemyShip
     */
    private void moveForward(Enemy enemyShip)
    {
        // .
        double changeX = Math.cos(Math.toRadians(enemyShip.Get_Rotation()));
        double changeY = Math.sin(Math.toRadians(enemyShip.Get_Rotation()));

        // .
        enemyShip.Set_X(enemyShip.Get_X() + changeX * enemyShip.getEnemy_speedFactor());
        enemyShip.Set_Y(enemyShip.Get_Y() + changeY * enemyShip.getEnemy_speedFactor());
    }



    /**
     *
     * @param enemyShip
     * @param world
     */
    private void calculateAngleToPlayer(Enemy enemyShip, World world)
    {
        // Find the entity of type "Player".
        for (Entity player : world.getEntities(Player.class))
        {
            // Calculate the number of degrees the enemy ship needs to turn to look at the player.
            double angleToPlayer = Math.toDegrees( Math.atan2((player.Get_Y() - enemyShip.Get_Y()), (player.Get_X() - enemyShip.Get_X())) );

            // Sets the desired rotation for the enemyship.
            enemyShip.setDesired_rotation(angleToPlayer);
        }
    }



    /**
     *
     * @param enemyShip
     */
    private void turnTowardsPlayer(Enemy enemyShip)
    {
        // Calculate how much we need to turn
        double orientation_change = (enemyShip.Get_Rotation() - enemyShip.getDesired_rotation()) * enemyShip.getEnemy_turnFactor();

        // Clamp to max turn speed
        if (orientation_change > enemyShip.getEnemy_turnSpeed_max())
        {
            orientation_change = enemyShip.getEnemy_turnSpeed_max();
        }
        if (orientation_change < -enemyShip.getEnemy_turnSpeed_max())
        {
            orientation_change = -enemyShip.getEnemy_turnSpeed_max();
        }

        // Apply the rotation
        enemyShip.Set_Rotation(enemyShip.Get_Rotation() - orientation_change);
    }



    /**
     *
     * @param enemyShip
     * @param gameData
     * @param world
     */
    private void shootIfAimed(Enemy enemyShip, GameData gameData, World world)
    {
        // Calculate how far off we are from facing the player
        double angleDifference = Math.abs(enemyShip.Get_Rotation() - enemyShip.getDesired_rotation());


        // Only shoot if facing close enough to the player and bullet is loaded.
        if ((angleDifference < enemyShip.getEnemy_turnSpeed_min()) && enemyShip.isEnemy_Bullet_loaded())
        {

            // Try to find an implementation of "BulletSPI", so we can call the method "createBullet()".
            try
            {
                // Get all available BulletSPI implementations.
                Collection<? extends BulletSPI> bulletSPIs = getBulletSPIs();

                // If a splitter was found, use the first one to split the asteroid.
                if (!(bulletSPIs.isEmpty()))
                {
                    // Calls the "createBullet(enemyShip, gameData)" method, and adds the bullet to the World.
                    world.addEntity(bulletSPIs.iterator().next().createBullet(enemyShip, gameData));
                }
            }
            catch (Exception e)
            {
                System.out.println("bulletSPI implementation is not available: " + e.getMessage());
            }

            // Mark as unloaded, and start reload timer.
            enemyShip.setEnemy_Bullet_loaded(false);
            enemyShip.setEnemy_Reload_ticksLeft(enemyShip.getEnemy_Reload_time());
        }

        // Else-If the enemy doesn't have their bullets loaded yet.
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




    /**
     *
     * @return
     */
    private Collection<? extends BulletSPI> getBulletSPIs()
    {
        // "Collection<? extends interface>"
        // We use Collection, so we can store the implementations of the interface, without fear of duplications.
        // We use Wildcard, so we can store any implementation of BulletSPI.
        // Link = https://www.geeksforgeeks.org/java/wildcards-in-java/

        // "ServiceLoader.load(Interface.class)"
        // Finds and loads all registered implementations of an Interface available.
        // Link = https://www.geeksforgeeks.org/java/java-mdoules-service-implementation-module/

        // We find, load and collect the implementations of the "BulletSPI" interface.
        Collection<? extends BulletSPI> bulletImplementation = ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());

        // Returns all implementations of the interface "BulletSPI".
        return bulletImplementation;
    }




    /**
     *
     * @param enemyShip
     * @param gameData
     */
    private void wrapAroundEdges(Enemy enemyShip, GameData gameData)
    {
        // .
        if (enemyShip.Get_X() < 0)
        {
            enemyShip.Set_X(gameData.getDisplayWidth());
        }

        // .
        if (enemyShip.Get_X() > gameData.getDisplayWidth())
        {
            enemyShip.Set_X(0);
        }

        // .
        if (enemyShip.Get_Y() < 0)
        {
            enemyShip.Set_Y(gameData.getDisplayHeight());
        }

        // .
        if (enemyShip.Get_Y() > gameData.getDisplayHeight())
        {
            enemyShip.Set_Y(0);
        }
    }










}
