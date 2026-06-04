package dk.sdu.se4.groupX.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.se4.groupX.commonasteroids.Asteroid;


/**
 * AsteroidPlugin is responsible for managing the lifecycle of Asteroid entities in the game world.
 * Spawns the initial set of asteroids at game startup and on restart,
 * and removes all Asteroid entities from the world when the game stops or restarts.
 *
 * The position and configuration of each asteroid are handled by AsteroidCreationHelper,
 * which ensures asteroids spawn at a safe distance from the player.
 */
public class AsteroidPlugin implements IGamePluginService
{


    /////////////////////////////////////////////////////////////////////////
    ////////////////////    IGamePluginService Methods    ////////////////////
    ///


    /**
     * Starts the Asteroid plugin - spawns the initial set of asteroids into the world.
     * Called once at game startup and once on each restart by Game.java's restart() method.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: the initial set of asteroids has been added to the world,
     *                 and the asteroid count has been incremented accordingly.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void start(GameData gameData, World world)
    {
        // Log that the Asteroid plugin is starting.
        System.out.println("Asteroid plugin starting.");

        // Spawn the initial set of asteroids up to the maximum allowed count.
        for (int i = 0; i < world.Get_MaxAsteroids(); i++)
        {
            // Create a new asteroid entity at a safe spawn position.
            Asteroid asteroid = AsteroidCreationHelper.createAsteroid(gameData, world);

            // Add the asteroid to the world and increment the asteroid count.
            world.addEntity(asteroid);
            world.AddTo_CurrentAsteroidCount(1);
        }
    }


    /**
     * Stops the Asteroid plugin - removes all Asteroid entities from the world.
     * Called once when the game is stopped or restarted by Game.java's restart() method.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: all Asteroid entities removed from the world,
     *                 and the asteroid count reset to zero.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void stop(GameData gameData, World world)
    {
        // Log that the Asteroid plugin is stopping.
        System.out.println("Asteroid plugin stopping.");

        // Remove all Asteroid entities from the world.
        for (Entity asteroid : world.getEntities(Asteroid.class))
        {
            // Remove the asteroid entity from the world.
            world.removeEntity(asteroid);
        }

        // Reset the asteroid count to zero.
        world.Set_CurrentAsteroidCount(0);
    }


}