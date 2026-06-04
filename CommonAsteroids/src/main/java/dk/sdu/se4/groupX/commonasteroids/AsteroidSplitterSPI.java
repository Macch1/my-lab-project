package dk.sdu.se4.groupX.commonasteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;


/**
 * AsteroidSplitterSPI is the service provider interface for splitting asteroid entities.
 * Implementations are responsible for removing the destroyed asteroid from the world
 * and spawning two smaller fragment asteroids in its place, if the asteroid is large enough.
 *
 * Implementations are discovered via ServiceLoader by AsteroidProcessor when an asteroid
 * is destroyed, allowing the splitting behaviour to be swapped or extended without
 * modifying the AsteroidProcessor.
 *
 * If the asteroid is too small to split, the implementation simply removes it from the world.
 */
public interface AsteroidSplitterSPI
{

    /**
     * Splits the given asteroid entity into two smaller fragments.
     * Removes the original asteroid from the world and spawns two smaller fragments in its place.
     * If the asteroid is too small to split, it is simply removed from the world.
     *
     * Pre-Condition:  e != null, world != null, e is present in the world.
     * Post-Condition: the original asteroid is removed from the world.
     *                 If large enough, two smaller fragment asteroids are added to the world.
     *
     * @param e the asteroid entity to split.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    void splitAsteroid(Entity e, World world);

}

