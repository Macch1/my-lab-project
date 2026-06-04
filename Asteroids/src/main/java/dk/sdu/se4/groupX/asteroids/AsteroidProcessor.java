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
 * AsteroidProcessor is responsible for processing all Asteroid entities each frame.
 * Handles asteroid movement, boundary wrapping, health checks, splitting on destruction,
 * and respawning new asteroids when the count drops below the maximum allowed.
 *
 * Discovers AsteroidSplitterSPI implementations via ServiceLoader when an asteroid is destroyed,
 * allowing the splitting behaviour to be swapped without modifying this class.
 */
public class AsteroidProcessor implements IEntityProcessingService
{


    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////    IEntityProcessingService Methods    ////////////////////
    ///


    /**
     * Processes all Asteroid entities each frame.
     * Moves all asteroids forward, wraps them around screen edges,
     * handles health checks, and respawns new asteroids if below the maximum count.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: all asteroids moved, dead asteroids removed or split,
     *                 and asteroid count maintained at the maximum allowed.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void EntityProcess(GameData gameData, World world)
    {
        // Process all Asteroid entities currently in the world.
        for (Entity asteroid : world.getEntities(Asteroid.class))
        {
            // Check if the asteroid is dead - handle destruction and skip to the next.
            if (this.handleHealth(asteroid, world))
            {
                continue; // Skip dead entities.
            }

            // Calculate the movement direction based on the asteroid's rotation.
            double changeX = Math.cos(Math.toRadians(asteroid.Get_Rotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.Get_Rotation()));

            // Move the asteroid forward by 0.5 units in its current direction.
            asteroid.Set_X(asteroid.Get_X() + changeX * 0.5);
            asteroid.Set_Y(asteroid.Get_Y() + changeY * 0.5);

            // Wrap the asteroid's X position - left edge.
            if (asteroid.Get_X() < 0)
            {
                // Asteroid has gone off the left edge - wrap to the right edge.
                asteroid.Set_X(gameData.getDisplayWidth());
            }

            // Wrap the asteroid's X position - right edge.
            if (asteroid.Get_X() > gameData.getDisplayWidth())
            {
                // Asteroid has gone off the right edge - wrap to the left edge.
                asteroid.Set_X(0);
            }

            // Wrap the asteroid's Y position - top edge.
            if (asteroid.Get_Y() < 0)
            {
                // Asteroid has gone off the top edge - wrap to the bottom edge.
                asteroid.Set_Y(gameData.getDisplayHeight());
            }

            // Wrap the asteroid's Y position - bottom edge.
            if (asteroid.Get_Y() > gameData.getDisplayHeight())
            {
                // Asteroid has gone off the bottom edge - wrap to the top edge.
                asteroid.Set_Y(0);
            }
        }

        // Respawn new asteroids if the current count is below the maximum allowed.
        if (world.Get_CurrentAsteroidCount() < world.Get_MaxAsteroids())
        {
            // Log that new asteroids are being spawned.
            System.out.println("Spawning new asteroids...");

            // Keep spawning until the count reaches the maximum allowed.
            while (world.Get_CurrentAsteroidCount() < world.Get_MaxAsteroids())
            {
                // Create a new asteroid entity at a safe spawn position.
                Entity asteroid = AsteroidCreationHelper.createAsteroid(gameData, world);

                // Add the asteroid to the world and increment the asteroid count.
                world.addEntity(asteroid);
                world.AddTo_CurrentAsteroidCount(1);
            }

            // Log the updated asteroid count.
            System.out.println("Asteroid count now: " + world.Get_CurrentAsteroidCount());
        }
    }




    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    /**
     * Checks if the asteroid's health has reached zero and handles its destruction.
     * Attempts to split the asteroid via AsteroidSplitterSPI - if no splitter is available,
     * or if the asteroid is too small to split, it is simply removed from the world.
     * Awards 10 points to the score and decrements the asteroid count on destruction.
     *
     * @param asteroid the asteroid entity to check.
     * @param world the game world to remove or split the asteroid in.
     * @return true if the asteroid was destroyed, false otherwise.
     */
    private boolean handleHealth(Entity asteroid, World world)
    {
        // If the health of the Asteroid is zero or below, we define the Asteroid as "Dead" / "Destroyed".
        if (asteroid.Get_Health() <= 0)
        {
            // Try to find an implementation of "AsteroidSplitterSPI", so we can call the method "splitAsteroid()".
            try
            {
                // Get all available AsteroidSplitter implementations.
                Collection<? extends AsteroidSplitterSPI> AsteroidSplitters = this.getSplitters();

                // If a splitter was found, use the first one to split the asteroid.
                if (!(AsteroidSplitters.isEmpty()))
                {
                    // Call the "splitAsteroid(asteroid, world)" method on the first available splitter.
                    AsteroidSplitters.iterator().next().splitAsteroid(asteroid, world);
                }
            }
            catch (Exception e)
            {
                // Log that no AsteroidSplitter implementation was available.
                System.out.println("AsteroidSplitter implementation is not available: " + e.getMessage());

                // If the Asteroid wasn't removed from the "world" by the splitter, we remove it manually.
                if ( world.containsEntity(asteroid.Get_ID()) )
                {
                    // Remove the asteroid from the world manually.
                    world.removeEntity(asteroid.Get_ID());
                }
            }

            // Award 10 points to the score and decrement the asteroid count.
            world.AddTo_CurrentScore(10);
            world.AddTo_CurrentAsteroidCount(-1);

            // Return true to indicate the Asteroid is "Dead" / "Destroyed".
            return true;
        }

        // Return false to indicate the Asteroid is "Alive" / "Not destroyed".
        return false;
    }


    /**
     * Discovers all available AsteroidSplitterSPI implementations via ServiceLoader.
     * Called each time an asteroid is destroyed to support live component swapping.
     *
     * @return a collection of all discovered AsteroidSplitterSPI implementations.
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















