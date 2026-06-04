package dk.sdu.se4.groupX.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.se4.groupX.commonasteroids.Asteroid;
import dk.sdu.se4.groupX.commonasteroids.AsteroidSplitterSPI;
import java.util.Random;


/**
 * AsteroidSplitter is the implementation of AsteroidSplitterSPI.
 * Responsible for splitting a destroyed asteroid into two smaller fragment asteroids.
 * If the asteroid is too small to split (radius below MIN_SIZE), it is simply removed.
 *
 * Discovered and loaded by AsteroidProcessor via ServiceLoader when an asteroid is destroyed.
 */
public class AsteroidSplitter implements AsteroidSplitterSPI
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Attributes    ////////////////////
    ///


    /**
     * The minimum radius an asteroid fragment must have to be spawned.
     * Fragments smaller than this value are not spawned.
     */
    private static final float MIN_SIZE = 5f;

    /**
     * Shared random number generator for fragment position and rotation calculations.
     */
    private final Random random = new Random();




    /////////////////////////////////////////////////////////////////////////////
    ////////////////////    AsteroidSplitterSPI Methods    ////////////////////
    ///


    /**
     * Splits the given asteroid into two smaller fragment asteroids.
     * Removes the original asteroid from the world and spawns two fragments in its place.
     * If the asteroid is too small to split, it is simply removed from the world.
     *
     * Pre-Condition:  e != null, world != null, e is present in the world.
     * Post-Condition: the original asteroid is removed from the world.
     *                 If large enough, two smaller fragment asteroids are added to the world.
     *
     * @param e the asteroid entity to split.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void splitAsteroid(Entity e, World world)
    {
        // Remove the original asteroid from the world.
        world.removeEntity(e);

        // Calculate the size of each fragment — half the radius of the original asteroid.
        float newSize = e.Get_Radius() / 2f;

        // If the fragment size is below the minimum, the asteroid is too small to split.
        if (newSize < MIN_SIZE)
        {
            return;
        }

        // Spawn two smaller fragment asteroids in place of the original.
        for (int i = 0; i < 2; i++)
        {
            // Create a new Asteroid fragment entity.
            Entity fragment = new Asteroid();

            // Cast the fragment size to int for polygon coordinate calculation.
            int s = (int) newSize;

            // Set the visual shape of the fragment based on its size.
            fragment.Set_PolygonCoordinates(s, -s, -s, -s, -s, s, s, s);

            // Spawn the fragment slightly offset from the original asteroid's position.
            fragment.Set_X(e.Get_X() + random.nextInt(20) - 10);
            fragment.Set_Y(e.Get_Y() + random.nextInt(20) - 10);

            // Set the collision radius of the fragment.
            fragment.Set_Radius(newSize);

            // Set a random rotation direction for the fragment.
            fragment.Set_Rotation(random.nextInt(360));

            // Set the fragment's health — collision flags inherited from Asteroid constructor.
            fragment.Set_Health(50);

            // Add the fragment to the world and increment the asteroid count.
            world.addEntity(fragment);
            world.AddTo_CurrentAsteroidCount(1);
        }
    }


}