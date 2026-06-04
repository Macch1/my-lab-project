package dk.sdu.se4.groupX.enemyship;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.se4.groupX.commonenemy.Enemy;

import java.util.Random;


/**
 * Helper class for enemy spawn position and rotation calculations.
 * Used by both EnemyShipPlugin and EnemyShipProcessor.
 */
public class EnemyCreationHelper
{

    // .
    private static final Random random = new Random();


    /**
     *
     * @param gameData
     * @param world
     */
    public static Enemy createEnemy(GameData gameData, World world)
    {
        // Setup variables.
        double[] start_position;
        double start_rotation;
        Enemy enemy = new Enemy();

        // Calculate spawn position along a random screen edge.
        start_position = getSpawnPosition(gameData);
        start_rotation = getSpawnRotation(start_position[0], start_position[1], gameData);

        // Set entity values.
        enemy.Set_PolygonCoordinates(10, 0, 5, -7, -7, -5, -7, 5, 5, 7);
        enemy.Set_X(start_position[0]);
        enemy.Set_Y(start_position[1]);
        enemy.Set_Radius(8);
        enemy.Set_Rotation(start_rotation);

        // Health and collision logic.
        enemy.Set_Can_Collide(true);
        enemy.Set_CanTake_CollideDamage(true);
        enemy.Set_CanTake_Damaged(true);
        enemy.Set_CollisionDamage(100);
        enemy.Set_Health(50); // easier to kill than player


        // Return Enemy.
        return enemy;
    }


    /**
     *
     * @param gameData
     * @return
     */
    private static double[] getSpawnPosition(GameData gameData)
    {
        // The return values.
        double[] coordinates = new double[2];

        // Rolling for which side to spawn on.
        int side_id = (int)(random.nextInt(100) % 4);

        // Logic to determine the coordinates based on the side.
        if (side_id == 0)
        {
            // Top — random X position along top edge
            coordinates[0] = random.nextDouble() * gameData.getDisplayWidth();
            coordinates[1] = 0.0;
            return coordinates;
        }
        else if (side_id == 1)
        {
            // Bottom — random X position along bottom edge
            coordinates[0] = random.nextDouble() * gameData.getDisplayWidth();
            coordinates[1] = gameData.getDisplayHeight();
            return coordinates;
        }
        else if (side_id == 2)
        {
            // Right — random Y position along right edge
            coordinates[0] = gameData.getDisplayWidth();
            coordinates[1] = random.nextDouble() * gameData.getDisplayHeight();
            return coordinates;
        }
        else if (side_id == 3)
        {
            // Left — random Y position along left edge
            coordinates[0] = 0.0;
            coordinates[1] = random.nextDouble() * gameData.getDisplayHeight();
            return coordinates;
        }

        // Standard error stream (best for warnings/errors)
        System.err.println("WARNING: Random number gave us this Side ID = " + side_id + ".");

        coordinates[0] = (double) 0.0;
        coordinates[1] = (double) 0.0;
        return coordinates;
    }




    /**
     *
     * @param spawnX
     * @param spawnY
     * @param gameData
     * @return
     */
    private static double getSpawnRotation(double spawnX, double spawnY, GameData gameData)
    {
        // Find Center of Map.
        double center_X = gameData.getDisplayWidth() / 2.0;
        double center_Y = gameData.getDisplayHeight() / 2.0;

        // Calculate the direction of the center.
        double angle_to_Center = Math.toDegrees(Math.atan2(center_Y - spawnY, center_X - spawnX));

        // Calculate offset with a random chance.
        double offset = (double) ((random.nextDouble() * 120.0) - 60.0);

        // return the start rotation, for the enemy ship.
        return angle_to_Center + offset;
    }




}
