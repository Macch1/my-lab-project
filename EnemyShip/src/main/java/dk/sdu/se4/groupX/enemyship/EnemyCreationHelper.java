package dk.sdu.se4.groupX.enemyship;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.se4.groupX.commonenemy.Enemy;

import java.util.Random;


/**
 * EnemyCreationHelper is a static helper class responsible for creating Enemy entities.
 * Used by both EnemyShipPlugin and EnemyShipProcessor to ensure consistent enemy creation.
 *
 * Handles spawn position calculation - enemies always spawn at a random point along
 * a screen edge, aimed roughly towards the center of the map with a random offset.
 */
public class EnemyCreationHelper
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Attributes    ////////////////////
    ///


    /**
     * Shared random number generator for all enemy creation operations.
     */
    private static final Random random = new Random();




    ////////////////////////////////////////////////////////////////
    ////////////////////    Factory Methods    ////////////////////
    ///


    /**
     * Creates and returns a fully configured Enemy entity at a random screen edge position.
     * The enemy's spawn position is calculated along a random screen edge,
     * and its rotation is aimed roughly towards the center of the map.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: a fully configured Enemy entity is returned, ready to be added to the world.
     *
     * @param gameData contains the display dimensions used for spawn position calculation.
     * @param world the game world - reserved for future use in spawn logic.
     * @return a fully configured Enemy entity ready to be added to the world.
     */
    public static Enemy createEnemy(GameData gameData, World world)
    {
        // Declare variables for the spawn position and rotation.
        double[] start_position;
        double start_rotation;

        // Create a new Enemy entity - default flags and values set by Enemy constructor.
        Enemy enemy = new Enemy();

        // Calculate a random spawn position along a screen edge.
        start_position = getSpawnPosition(gameData);

        // Calculate the spawn rotation aimed roughly towards the center of the map.
        start_rotation = getSpawnRotation(start_position[0], start_position[1], gameData);

        // Set the visual shape of the enemy ship.
        enemy.Set_PolygonCoordinates(10, 0, 5, -7, -7, -5, -7, 5, 5, 7);

        // Set the spawn position of the enemy ship.
        enemy.Set_X(start_position[0]);
        enemy.Set_Y(start_position[1]);

        // Set the collision radius of the enemy ship.
        enemy.Set_Radius(8);

        // Set the spawn rotation of the enemy ship.
        enemy.Set_Rotation(start_rotation);

        // Override collision properties - enemies deal full damage on impact.
        enemy.Set_Can_Collide(true);
        enemy.Set_CanTake_CollideDamage(true);
        enemy.Set_CanTake_Damaged(true);
        enemy.Set_CollisionDamage(100);
        enemy.Set_Health(50); // Easier to kill than the player.

        // Return the fully configured enemy entity.
        return enemy;
    }




    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    /**
     * Calculates a random spawn position along a random screen edge.
     * Rolls a random number to determine which edge to spawn on,
     * then picks a random point along that edge.
     * Falls back to (0, 0) if an unexpected side ID is generated.
     *
     * @param gameData contains the display dimensions used for spawn position calculation.
     * @return a double array containing the spawn [x, y] position along a screen edge.
     */
    private static double[] getSpawnPosition(GameData gameData)
    {
        // Initialise the return coordinates array.
        double[] coordinates = new double[2];

        // Roll a random number to determine which screen edge to spawn on (0-3).
        int side_id = (int)(random.nextInt(100) % 4);

        // Determine the spawn coordinates based on the selected screen edge.

        // Top edge - random X position along the top of the screen.
        if (side_id == 0)
        {
            coordinates[0] = random.nextDouble() * gameData.getDisplayWidth();
            coordinates[1] = 0.0;
            return coordinates;
        }

        // Bottom edge - random X position along the bottom of the screen.
        else if (side_id == 1)
        {
            coordinates[0] = random.nextDouble() * gameData.getDisplayWidth();
            coordinates[1] = gameData.getDisplayHeight();
            return coordinates;
        }

        // Right edge - random Y position along the right of the screen.
        else if (side_id == 2)
        {
            coordinates[0] = gameData.getDisplayWidth();
            coordinates[1] = random.nextDouble() * gameData.getDisplayHeight();
            return coordinates;
        }

        // Left edge - random Y position along the left of the screen.
        else if (side_id == 3)
        {
            coordinates[0] = 0.0;
            coordinates[1] = random.nextDouble() * gameData.getDisplayHeight();
            return coordinates;
        }

        // Fallback - should never occur, but log a warning and return (0, 0) if it does.
        System.err.println("WARNING: Random number gave us this Side ID = " + side_id + ".");
        coordinates[0] = (double) 0.0;
        coordinates[1] = (double) 0.0;
        return coordinates;
    }


    /**
     * Calculates a spawn rotation aimed roughly towards the center of the map.
     * Adds a random offset of up to +/-60 degrees to vary the enemy's approach angle.
     *
     * @param spawnX the X coordinate of the enemy's spawn position.
     * @param spawnY the Y coordinate of the enemy's spawn position.
     * @param gameData contains the display dimensions used to find the map center.
     * @return the spawn rotation in degrees aimed roughly towards the map center.
     */
    private static double getSpawnRotation(double spawnX, double spawnY, GameData gameData)
    {
        // Find the center of the map.
        double center_X = gameData.getDisplayWidth() / 2.0;
        double center_Y = gameData.getDisplayHeight() / 2.0;

        // Calculate the angle from the spawn position towards the center of the map.
        double angle_to_Center = Math.toDegrees(Math.atan2(center_Y - spawnY, center_X - spawnX));

        // Add a random offset of up to +/-60 degrees to vary the approach angle.
        double offset = (random.nextDouble() * 120.0) - 60.0;

        // Return the final spawn rotation.
        return angle_to_Center + offset;
    }


}