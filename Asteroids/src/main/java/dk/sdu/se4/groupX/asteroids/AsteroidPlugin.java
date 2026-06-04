package dk.sdu.se4.groupX.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.se4.groupX.commonasteroids.Asteroid;
import java.util.Random;


/**
 *
 */
public class AsteroidPlugin implements IGamePluginService
{


    private final Random random = new Random();






    @Override
    public void start(GameData gameData, World world)
    {
        // .
        for (int i = 0; i < world.Get_MaxAsteroids(); i++)
        {
            // .
            Asteroid asteroid = AsteroidCreationHelper.createAsteroid(gameData, world);

            // .
            world.addEntity(asteroid);
            world.AddTo_CurrentAsteroidCount(1);
        }

    }





    @Override
    public void stop(GameData gameData, World world)
    {
        // .
        for (Entity asteroid : world.getEntities(Asteroid.class))
        {
            // .
            world.removeEntity(asteroid);
        }

        // .
        world.Set_CurrentAsteroidCount(0);
    }



}