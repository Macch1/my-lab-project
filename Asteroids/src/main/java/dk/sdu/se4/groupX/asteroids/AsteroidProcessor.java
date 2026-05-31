package dk.sdu.se4.groupX.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.se4.groupX.commonasteroids.Asteroid;
import dk.sdu.se4.groupX.commonasteroids.AsteroidSplitterSPI;

import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.ServiceLoader;
import static java.util.stream.Collectors.toList;

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
                continue; // skip dead entities
            }

            // .
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

            // .
            asteroid.setX(asteroid.getX() + changeX * 0.5);
            asteroid.setY(asteroid.getY() + changeY * 0.5);

            // .
            if (asteroid.getX() < 0) asteroid.setX(gameData.getDisplayWidth());
            if (asteroid.getX() > gameData.getDisplayWidth()) asteroid.setX(0);
            if (asteroid.getY() < 0) asteroid.setY(gameData.getDisplayHeight());
            if (asteroid.getY() > gameData.getDisplayHeight()) asteroid.setY(0);
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
        if (asteroid.getHealth() <= 0)
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
                if ( world.containsEntity(asteroid.getID()) )
                {
                    world.removeEntity(asteroid.getID());
                }
            }

            // Notify ScoringService.
            try
            {
                // Create a RestTemplate to make HTTP requests to the ScoringService.
                RestTemplate restTemplate = new RestTemplate();

                // Notify the ScoringService that one more Asteroid has been destroyed.
                restTemplate.put("http://localhost:8080/asteroids/add?point=1", null, Long.class);

                // Notify the ScoringService to add 10 points to the total score.
                restTemplate.put("http://localhost:8080/score/add?point=10", null);

            }
            catch (Exception e)
            {
                System.out.println("ScoringService not available: " + e.getMessage());
            }

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