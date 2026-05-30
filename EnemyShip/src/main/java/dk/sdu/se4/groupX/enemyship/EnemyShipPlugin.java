package dk.sdu.se4.groupX.enemyship;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.se4.groupX.commonenemy.Enemy;

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

        Entity enemyShip = new EnemyShip();
        enemyShip.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemyShip.setX(gameData.getDisplayHeight()/2);
        enemyShip.setY(gameData.getDisplayWidth()/2);
        enemyShip.setRadius(8);
        enemyShip.setRotation();
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







}
