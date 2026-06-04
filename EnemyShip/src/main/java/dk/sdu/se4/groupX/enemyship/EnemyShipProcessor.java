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


/**
 * EnemyShipProcessor is responsible for processing all Enemy entities each frame.
 * Handles enemy movement, turning towards the player, shooting when aimed,
 * boundary wrapping, health checks, and respawning new enemies when the count
 * drops below the maximum allowed.
 *
 * Discovers BulletSPI implementations via ServiceLoader when an enemy fires,
 * allowing the bullet creation behaviour to be swapped without modifying this class.
 */
public class EnemyShipProcessor implements IEntityProcessingService
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    /**
     * Default constructor for EnemyShipProcessor.
     */
    public EnemyShipProcessor ()
    {

    }




    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////    IEntityProcessingService Methods    ////////////////////
    ///


    /**
     * Processes all Enemy entities each frame.
     * Moves all enemies forward, turns them towards the player, fires bullets when aimed,
     * wraps them around screen edges, handles health checks, and respawns new enemies
     * if the count drops below the maximum allowed.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: all enemies moved, turned, fired, wrapped and health checked.
     *                 Dead enemies removed and enemy count maintained at the maximum allowed.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void EntityProcess(GameData gameData, World world)
    {
        // Process all Enemy entities currently in the world.
        for (Entity entity : world.getEntities(Enemy.class))
        {
            // Cast the entity to Enemy to access enemy-specific attributes.
            Enemy enemyShip = (Enemy) entity;

            // Step 0 — Check health — remove dead enemies and skip to the next.
            if (this.handleHealth(enemyShip, world))
            {
                continue; // Skip dead entities.
            }

            // Step 1 — Move the enemy forward in its current direction.
            this.moveForward(enemyShip);

            // Step 2 — Calculate the desired rotation towards the player.
            this.calculateAngleToPlayer(enemyShip, world);

            // Step 3 — Turn the enemy towards the player.
            this.turnTowardsPlayer(enemyShip);

            // Step 4 — Fire a bullet if the enemy is aimed at the player.
            this.shootIfAimed(enemyShip, gameData, world);

            // Step 5 — Wrap the enemy around the screen edges.
            this.wrapAroundEdges(enemyShip, gameData);
        }

        // Respawn new enemies if the current count is below the maximum allowed.
        if (world.Get_CurrentEnemyCount() < world.Get_MaxEnemies())
        {
            // Log that new enemies are being spawned.
            System.out.println("Spawning new enemies...");

            // Keep spawning until the count reaches the maximum allowed.
            while(world.Get_CurrentEnemyCount() < world.Get_MaxEnemies())
            {
                // Create a new enemy entity at a random screen edge position.
                Entity enemy = EnemyCreationHelper.createEnemy(gameData, world);

                // Add the enemy to the world and increment the enemy count.
                world.addEntity(enemy);
                world.AddTo_CurrentEnemyCount(1);
            }

            // Log the updated enemy count.
            System.out.println("Enemy count now: " + world.Get_CurrentEnemyCount());
        }
    }




    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    /**
     * Checks if the enemy's health has reached zero and handles its destruction.
     * Awards 50 points to the score and decrements the enemy count on destruction.
     *
     * @param enemy the enemy entity to check.
     * @param world the game world to remove the enemy from if dead.
     * @return true if the enemy was destroyed, false otherwise.
     */
    private boolean handleHealth(Entity enemy, World world)
    {
        // If the health of the Enemy is zero or below, we define the Enemy as "Dead" / "Destroyed".
        if (enemy.Get_Health() <= 0)
        {
            // Remove the "Enemy" entity from the "world".
            world.removeEntity(enemy);

            // Award 50 points to the score for destroying an enemy.
            world.AddTo_CurrentScore(50);

            // Decrement the current enemy count by 1.
            world.AddTo_CurrentEnemyCount(-1);

            // Return true to indicate the Enemy is "Dead" / "Destroyed".
            return true;
        }

        // Return false to indicate the Enemy is "Alive" / "Not destroyed".
        return false;
    }


    /**
     * Moves the enemy ship forward by its speed factor in its current direction.
     *
     * @param enemyShip the enemy entity to move.
     */
    private void moveForward(Enemy enemyShip)
    {
        // Calculate the movement direction based on the enemy's current rotation.
        double changeX = Math.cos(Math.toRadians(enemyShip.Get_Rotation()));
        double changeY = Math.sin(Math.toRadians(enemyShip.Get_Rotation()));

        // Move the enemy forward by its speed factor in its current direction.
        enemyShip.Set_X(enemyShip.Get_X() + changeX * enemyShip.getEnemy_speedFactor());
        enemyShip.Set_Y(enemyShip.Get_Y() + changeY * enemyShip.getEnemy_speedFactor());
    }


    /**
     * Calculates the angle from the enemy ship to the player and sets it as the desired rotation.
     * Called each frame to update the enemy's target direction.
     *
     * @param enemyShip the enemy entity to update the desired rotation for.
     * @param world the game world — used to find the player's current position.
     */
    private void calculateAngleToPlayer(Enemy enemyShip, World world)
    {
        // Find the Player entity in the world.
        for (Entity player : world.getEntities(Player.class))
        {
            // Calculate the angle from the enemy to the player in degrees.
            double angleToPlayer = Math.toDegrees( Math.atan2((player.Get_Y() - enemyShip.Get_Y()), (player.Get_X() - enemyShip.Get_X())) );

            // Set the desired rotation for the enemy ship to face the player.
            enemyShip.setDesired_rotation(angleToPlayer);
        }
    }


    /**
     * Turns the enemy ship towards its desired rotation by its turn factor per frame.
     * Clamps the turn amount to the maximum turn speed to prevent instant rotation.
     *
     * @param enemyShip the enemy entity to turn.
     */
    private void turnTowardsPlayer(Enemy enemyShip)
    {
        // Calculate how much the enemy needs to turn this frame.
        double orientation_change = (enemyShip.Get_Rotation() - enemyShip.getDesired_rotation()) * enemyShip.getEnemy_turnFactor();

        // Clamp the turn amount to the maximum turn speed — upper bound.
        if (orientation_change > enemyShip.getEnemy_turnSpeed_max())
        {
            orientation_change = enemyShip.getEnemy_turnSpeed_max();
        }

        // Clamp the turn amount to the maximum turn speed — lower bound.
        if (orientation_change < -enemyShip.getEnemy_turnSpeed_max())
        {
            orientation_change = -enemyShip.getEnemy_turnSpeed_max();
        }

        // Apply the clamped rotation to the enemy ship.
        enemyShip.Set_Rotation(enemyShip.Get_Rotation() - orientation_change);
    }


    /**
     * Fires a bullet if the enemy ship is aimed close enough at the player and has a bullet loaded.
     * Manages the reload timer between shots.
     *
     * @param enemyShip the enemy entity attempting to fire.
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    private void shootIfAimed(Enemy enemyShip, GameData gameData, World world)
    {
        // Calculate the angular difference between the enemy's current and desired rotation.
        double angleDifference = Math.abs(enemyShip.Get_Rotation() - enemyShip.getDesired_rotation());

        // Only shoot if the enemy is aimed close enough at the player and has a bullet loaded.
        if ((angleDifference < enemyShip.getEnemy_turnSpeed_min()) && enemyShip.isEnemy_Bullet_loaded())
        {
            // Try to find an implementation of "BulletSPI", so we can call the method "createBullet()".
            try
            {
                // Get all available BulletSPI implementations.
                Collection<? extends BulletSPI> bulletSPIs = this.getBulletSPIs();

                // If a BulletSPI was found, use the first one to create and fire a bullet.
                if (!(bulletSPIs.isEmpty()))
                {
                    // Create a bullet and add it to the world.
                    world.addEntity(bulletSPIs.iterator().next().createBullet(enemyShip, gameData));
                }
            }
            catch (Exception e)
            {
                // Log that no BulletSPI implementation was available.
                System.out.println("bulletSPI implementation is not available: " + e.getMessage());
            }

            // Mark the bullet as fired and start the reload timer.
            enemyShip.setEnemy_Bullet_loaded(false);
            enemyShip.setEnemy_Reload_ticksLeft(enemyShip.getEnemy_Reload_time());
        }

        // If the enemy doesn't have a bullet loaded yet — count down the reload timer.
        else if (!enemyShip.isEnemy_Bullet_loaded())
        {
            // Decrement the reload timer by 1 frame.
            enemyShip.setEnemy_Reload_ticksLeft(enemyShip.getEnemy_Reload_ticksLeft() - 1);

            // Check if the reload timer has reached zero.
            if (enemyShip.getEnemy_Reload_ticksLeft() <= 0)
            {
                // Reload complete — mark the bullet as loaded.
                enemyShip.setEnemy_Bullet_loaded(true);
            }
        }
    }


    /**
     * Discovers all available BulletSPI implementations via ServiceLoader.
     * Called each time an enemy fires to support live component swapping.
     *
     * @return a collection of all discovered BulletSPI implementations.
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
     * Wraps the enemy ship's position around the screen edges.
     * Called each frame to keep the enemy within the game world boundaries.
     *
     * @param enemyShip the enemy entity to wrap.
     * @param gameData contains the display dimensions used for boundary checking.
     */
    private void wrapAroundEdges(Enemy enemyShip, GameData gameData)
    {
        // Wrap the enemy's X position — left edge.
        if (enemyShip.Get_X() < 0)
        {
            // Enemy has gone off the left edge — wrap to the right edge.
            enemyShip.Set_X(gameData.getDisplayWidth());
        }

        // Wrap the enemy's X position — right edge.
        if (enemyShip.Get_X() > gameData.getDisplayWidth())
        {
            // Enemy has gone off the right edge — wrap to the left edge.
            enemyShip.Set_X(0);
        }

        // Wrap the enemy's Y position — top edge.
        if (enemyShip.Get_Y() < 0)
        {
            // Enemy has gone off the top edge — wrap to the bottom edge.
            enemyShip.Set_Y(gameData.getDisplayHeight());
        }

        // Wrap the enemy's Y position — bottom edge.
        if (enemyShip.Get_Y() > gameData.getDisplayHeight())
        {
            // Enemy has gone off the bottom edge — wrap to the top edge.
            enemyShip.Set_Y(0);
        }
    }


}

