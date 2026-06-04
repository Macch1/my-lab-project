package dk.sdu.se4.groupX.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.se4.groupX.commonasteroids.Asteroid;
import java.util.Random;


/**
 * AsteroidCreationHelper is a static helper class responsible for creating Asteroid entities.
 * Used by both AsteroidPlugin and AsteroidProcessor to ensure consistent asteroid creation.
 *
 * Handles spawn position calculation - ensuring asteroids always spawn at a safe distance
 * from the player's current position, with a maximum of 50 attempts before giving up.
 */
public class AsteroidCreationHelper
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Attributes    ////////////////////
    ///


    /**
     * Shared random number generator for all asteroid creation operations.
     */
    private static final Random random = new Random();

    /**
     * The minimum distance in pixels that an asteroid must spawn from the player.
     */
    private static final int MIN_SPAWN_DISTANCE = 100;




    ////////////////////////////////////////////////////////////////
    ////////////////////    Factory Methods    ////////////////////
    ///


    /**
     * Creates and returns a fully configured Asteroid entity at a safe spawn position.
     * The asteroid's size is randomly determined, and its position is guaranteed to be
     * at least MIN_SPAWN_DISTANCE pixels away from the player's current position.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: a fully configured Asteroid entity is returned, ready to be added to the world.
     *
     * @param gameData contains the display dimensions used for spawn position calculation.
     * @param world the game world - used to find the player's current position.
     * @return a fully configured Asteroid entity ready to be added to the world.
     */
    public static Asteroid createAsteroid(GameData gameData, World world)
    {
        // Create a new Asteroid entity - default flags and values set by Asteroid constructor.
        Asteroid asteroid = new Asteroid();

        // Randomly determine the size of the asteroid between 10 and 30 units.
        int size = random.nextInt(20) + 10;

        // Find a safe spawn position at least MIN_SPAWN_DISTANCE away from the player.
        double[] position = getSafeSpawnPosition(gameData, world);

        // Set the visual shape of the asteroid based on its size.
        asteroid.Set_PolygonCoordinates(size, -size, -size, -size, -size, size, size, size);

        // Set the spawn position of the asteroid.
        asteroid.Set_X(position[0]);
        asteroid.Set_Y(position[1]);

        // Set the collision radius to match the asteroid's size.
        asteroid.Set_Radius((float)size);

        // Set a random rotation direction for the asteroid.
        asteroid.Set_Rotation(random.nextInt(360));

        // Override collision properties - asteroids destroy ships on contact.
        asteroid.Set_Can_Collide(true);
        asteroid.Set_CanTake_CollideDamage(true);
        asteroid.Set_CanTake_Damaged(true);
        asteroid.Set_CollisionDamage(100);
        asteroid.Set_Health(50);

        // Return the fully configured asteroid entity.
        return asteroid;
    }




    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    /**
     * Finds a safe spawn position for an asteroid at least MIN_SPAWN_DISTANCE
     * pixels away from the player's current position.
     * Defaults to the center of the screen if no Player entity is found.
     * Makes up to 50 attempts before returning the last calculated position.
     *
     * @param gameData contains the display dimensions used to calculate spawn bounds.
     * @param world the game world - used to find the player's current position.
     * @return a double array containing the safe spawn [x, y] position.
     */
    private static double[] getSafeSpawnPosition(GameData gameData, World world)
    {
        // Initialise the position array.
        double[] position = new double[2];

        // Default player position to the center of the screen.
        double playerX = gameData.getDisplayWidth() / 2.0;
        double playerY = gameData.getDisplayHeight() / 2.0;

        // Search for the Player entity to get its current position.
        for (Entity entity : world.getEntities())
        {
            // Check if the entity is of type Player.
            if (entity.Get_Type() == EntityType.Player)
            {
                // Player found - use its current position.
                playerX = entity.Get_X();
                playerY = entity.Get_Y();

                // Stop searching once the player is found.
                break;
            }
        }

        // Keep trying random positions until a safe one is found, up to 50 attempts.
        int attempts = 0;

        do
        {
            // Generate a random position within the screen bounds.
            position[0] = random.nextInt(gameData.getDisplayWidth());
            position[1] = random.nextInt(gameData.getDisplayHeight());

            // Increment the attempt counter.
            attempts++;
        }
        while (isTooCloseToPlayer(position[0], position[1], playerX, playerY) && attempts < 50);
        // Stop when a safe position is found or 50 attempts have been made.

        // Return the safe spawn position.
        return position;
    }


    /**
     * Checks whether the given position is too close to the player's current position.
     * A position is considered too close if it is within MIN_SPAWN_DISTANCE pixels of the player.
     *
     * @param x the X coordinate of the position to check.
     * @param y the Y coordinate of the position to check.
     * @param playerX the X coordinate of the player's current position.
     * @param playerY the Y coordinate of the player's current position.
     * @return true if the position is too close to the player, false otherwise.
     */
    private static boolean isTooCloseToPlayer(double x, double y, double playerX, double playerY)
    {
        // Calculate the difference in X and Y between the position and the player.
        double dx = x - playerX;
        double dy = y - playerY;

        // Calculate the Euclidean distance between the position and the player.
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Return true if the distance is less than the minimum spawn distance.
        return distance < MIN_SPAWN_DISTANCE;
    }


}



