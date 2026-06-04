package dk.sdu.se4.groupX.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.se4.groupX.commonasteroids.Asteroid;
import java.util.Random;



/**
 * Helper class responsible for creating Asteroid entities.
 * Used by both AsteroidPlugin and AsteroidProcessor.
 */
public class AsteroidCreationHelper
{

    /**
     *
     */
    private static final Random random = new Random();

    /**
     *
     */
    private static final int MIN_SPAWN_DISTANCE = 100;


    /**
     * Creates a new Asteroid entity with a safe spawn position.
     * Spawns away from the center and away from the player.
     * @param gameData contains display dimensions
     * @param world the game world
     * @return configured Asteroid entity
     */
    public static Asteroid createAsteroid(GameData gameData, World world)
    {
        // .
        Asteroid asteroid = new Asteroid();

        // .
        int size = random.nextInt(20) + 10;

        // Get a safe spawn position away from player.
        double[] position = getSafeSpawnPosition(gameData, world);

        // .
        asteroid.Set_PolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.Set_X(position[0]);
        asteroid.Set_Y(position[1]);
        asteroid.Set_Radius((float)size);
        asteroid.Set_Rotation(random.nextInt(360));

        // Health and collision logic.
        asteroid.Set_Can_Collide(true);
        asteroid.Set_CanTake_CollideDamage(true);
        asteroid.Set_CanTake_Damaged(true);
        asteroid.Set_CollisionDamage(100); // destroys ships on contact
        asteroid.Set_Health(50);

        // .
        return asteroid;
    }


    /**
     *
     * @param gameData
     * @param world
     * @return
     */
    private static double[] getSafeSpawnPosition(GameData gameData, World world)
    {
        // .
        double[] position = new double[2];

        // Find player position.
        double playerX = gameData.getDisplayWidth() / 2.0;  // default to center
        double playerY = gameData.getDisplayHeight() / 2.0;

        // .
        for (Entity entity : world.getEntities())
        {

            // .
            if (entity.Get_Type() == EntityType.Player)
            {
                playerX = entity.Get_X();
                playerY = entity.Get_Y();
                break;
            }
        }

        // Keep trying until we find a safe position.
        int attempts = 0;

        do
        {
            position[0] = random.nextInt(gameData.getDisplayWidth());
            position[1] = random.nextInt(gameData.getDisplayHeight());
            attempts++;
        }
        while (isTooCloseToPlayer(position[0], position[1], playerX, playerY) && attempts < 50);

        return position;
    }



    /**
     *
     * @param x
     * @param y
     * @param playerX
     * @param playerY
     * @return
     */
    private static boolean isTooCloseToPlayer(double x, double y, double playerX, double playerY)
    {
        // .
        double dx = x - playerX;
        double dy = y - playerY;

        // .
        double distance = Math.sqrt(dx * dx + dy * dy);

        // .
        return distance < MIN_SPAWN_DISTANCE;
    }




}



