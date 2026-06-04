package dk.sdu.se4.groupX.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.se4.groupX.commonasteroids.Asteroid;
import dk.sdu.se4.groupX.commonasteroids.AsteroidSplitterSPI;

import java.util.Collection;
import java.util.ServiceLoader;
import static java.util.stream.Collectors.toList;


/**
 *
 */
public class AsteroidProcessor implements IEntityProcessingService
{



    @Override
    public void process(GameData gameData, World world)
    {
        // .
        for (Entity asteroid : world.getEntities(Asteroid.class))
        {
            // Checks if the Asteroid is still "Alive" / "Not destroyed".
            if (this.handleHealth(asteroid, world))
            {
                // .
                continue;
            }

            // .
            double changeX = Math.cos(Math.toRadians(asteroid.Get_Rotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.Get_Rotation()));

            // .
            asteroid.Set_X(asteroid.Get_X() + changeX * 0.5);
            asteroid.Set_Y(asteroid.Get_Y() + changeY * 0.5);

            // .
            if (asteroid.Get_X() < 0)
            {
                asteroid.Set_X(gameData.getDisplayWidth());
            }
            if (asteroid.Get_X() > gameData.getDisplayWidth())
            {
                asteroid.Set_X(0);
            }

            // .
            if (asteroid.Get_Y() < 0)
            {
                asteroid.Set_Y(gameData.getDisplayHeight());
            }
            if (asteroid.Get_Y() > gameData.getDisplayHeight())
            {
                asteroid.Set_Y(0);
            }

        }


        // Spawn new asteroids if below max.
        if (world.Get_CurrentAsteroidCount() < world.Get_MaxAsteroids())
        {
            // .
            System.out.println("Spawning new asteroids...");

            // .
            while (world.Get_CurrentAsteroidCount() < world.Get_MaxAsteroids())
            {
                // .
                Entity asteroid = AsteroidCreationHelper.createAsteroid(gameData, world);

                // .
                world.addEntity(asteroid);
                world.AddTo_CurrentAsteroidCount(1);
            }

            // .
            System.out.println("Asteroid count now: " + world.Get_CurrentAsteroidCount());
        }

    }




    /**
     *
     * @param asteroid
     * @param world
     * @return
     */
    private boolean handleHealth(Entity asteroid, World world)
    {

        // If the health of the Asteroid is zero or below, we define the Asteroid as "Dead" / "Destroyed".
        // If the Asteroid is "Dead" / "Destroyed" it gets handled below.
        if (asteroid.Get_Health() <= 0)
        {
            // Try to find an implementation of "AsteroidSplitterSPI", so we can call the method "splitAsteroid()".
            try
            {
                // Get all available AsteroidSplitter implementations.
                Collection<? extends AsteroidSplitterSPI> AsteroidSplitters = getSplitters();

                // If a splitter was found, use the first one to split the asteroid.
                if (!(AsteroidSplitters.isEmpty()))
                {
                    // Calls the "splitAsteroid(asteroid, world)" method.
                    AsteroidSplitters.iterator().next().splitAsteroid(asteroid, world);
                }
            }
            catch (Exception e)
            {
                System.out.println("AsteroidSplitter implementation is not available: " + e.getMessage());

                // If the Asteroid wasn't removed from the "world", we remove it.
                if ( world.containsEntity(asteroid.Get_ID()) )
                {
                    world.removeEntity(asteroid.Get_ID());
                }
            }

            // Notify World that an asteroid was destroyed.
            world.AddTo_CurrentScore(10);
            world.AddTo_CurrentAsteroidCount(-1);

            // returns "true" to indicate the Asteroid is "Dead" / "Destroyed".
            return true;
        }

        // returns "false" to indicate the Asteroid is "Alive" / "Not destroyed".
        return false;
    }



    /**
     *
     * @return
     */
    private Collection<? extends AsteroidSplitterSPI> getSplitters()
    {
        // "Collection<? extends interface>"
        // We use Collection, so we can store the implementations of the interface, without fear of duplications.
        // We use Wildcard, so we can store any implementation of AsteroidSplitterSPI.
        // Link = https://www.geeksforgeeks.org/java/wildcards-in-java/

        // "ServiceLoader.load(Interface.class)"
        // Finds and loads all registered implementations of an Interface available.
        // Link = https://www.geeksforgeeks.org/java/java-mdoules-service-implementation-module/

        // We find, load and collect the implementations of the "AsteroidSplitterSPI" interface.
        Collection<? extends AsteroidSplitterSPI> splitterImplementation = ServiceLoader.load(AsteroidSplitterSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());

        // Returns all implementations of the interface "AsteroidSplitterSPI".
        return splitterImplementation;
    }

}