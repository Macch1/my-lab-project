package dk.sdu.se4.groupX.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.se4.groupX.commonasteroids.Asteroid;


/**
 * Plugin responsible for managing the lifecycle of Asteroid entities in the game world.
 * Spawns the initial set of asteroids at game start and removes them when the game stops.
 */
public class AsteroidPlugin implements IGamePluginService
{




    @Override
    public void start(GameData gameData, World world)
    {
        // .
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




    @Override
    public void stop(GameData gameData, World world)
    {
        // .
        System.out.println("Asteroid plugin stopping.");

        // Remove all asteroid entities from the world.
        for (Entity asteroid : world.getEntities(Asteroid.class))
        {
            // Remove the asteroid entity from the world.
            world.removeEntity(asteroid);
        }

        // Reset the asteroid count to zero.
        world.Set_CurrentAsteroidCount(0);
    }



}






