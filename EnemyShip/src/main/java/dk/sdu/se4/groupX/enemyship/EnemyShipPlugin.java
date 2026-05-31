package dk.sdu.se4.groupX.enemyship;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.se4.groupX.commonplayer.Enemy;

import java.util.Random;


public class EnemyShipPlugin implements IGamePluginService {



    private final Random random = new Random();

    private Entity enemyShips;




    public EnemyShipPlugin()
    {

    }




    /////////////////////////////////////////////////////////////////////////
    ////////////////////    GamePluginService Methods    ////////////////////
    ///


    /**
     * Starts the game using the gameData and World.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void start(GameData gameData, World world)
    {
        // Add entities to the world
        enemyShips = createEnemyShip(gameData);
        world.addEntity(enemyShips);

    }




    /**
     * Stops the game using the gameData and World.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void stop(GameData gameData, World world)
    {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            // Remove entities
            world.removeEntity(enemy);
        }
    }









    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    /**
     *
     * @param gameData
     * @return
     */
    private Entity createEnemyShip(GameData gameData)
    {
        // Setup variables.
        double[] start_position;
        double start_rotation;
        Entity enemyShip = new Enemy();

        // Calculate.
        start_position = this.getSpawnPosition(gameData);
        start_rotation = this.getSpawnRotation(start_position[0], start_position[1], gameData);

        // Set entity values.
        enemyShip.setPolygonCoordinates(10, 0, 5, -7, -7, -5, -7, 5, 5, 7);
        enemyShip.setX(start_position[0]);
        enemyShip.setY(start_position[1]);
        enemyShip.setRadius(8);
        enemyShip.setRotation(start_rotation);

        // Health and collision logic.
        enemyShip.setCanCollide(true);
        enemyShip.setCanDamage(true);
        enemyShip.setCanBeDamaged(true);
        enemyShip.setCollisionDamage(100);
        enemyShip.setHealth(50); // easier to kill than player


        // Return Entity (Enemy).
        return enemyShip;
    }





    private double[] getSpawnPosition(GameData gameData)
    {
        // The return values.
        double[] coordinates = new double[2];

        // Rolling for which side to spawn on.
        int side_id = (int)(random.nextInt(100) % 4);

        // Logic to determine the coordinates based on the side.
        if (side_id == 0)
        {
            // Top
            coordinates[0] = (double) (gameData.getDisplayWidth() / 2.0);
            coordinates[1] = (double) 0.0;
            return coordinates;
        }
        else if (side_id == 1)
        {
            // Bottom
            coordinates[0] = (double) (gameData.getDisplayWidth() / 2.0);
            coordinates[1] = (double) gameData.getDisplayHeight();
            return coordinates;
        }
        else if (side_id == 2)
        {
            // Right
            coordinates[0] = (double) gameData.getDisplayWidth();
            coordinates[1] = (double) (gameData.getDisplayHeight() / 2.0);
            return coordinates;
        }
        else if (side_id == 3)
        {
            // Left
            coordinates[0] = (double) 0.0;
            coordinates[1] = (double) (gameData.getDisplayHeight() / 2.0);
            return coordinates;
        }

        // Standard error stream (best for warnings/errors)
        System.err.println("WARNING: Random number gave us this Side ID = " + side_id + ".");

        coordinates[0] = (double) 0.0;
        coordinates[1] = (double) 0.0;
        return coordinates;
    }


    private double getSpawnRotation(double spawnX, double spawnY, GameData gameData)
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
