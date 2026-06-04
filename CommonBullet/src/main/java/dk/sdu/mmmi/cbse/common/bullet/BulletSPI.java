package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;


/**
 * BulletSPI is the service provider interface for creating bullet entities.
 * Implementations are responsible for creating and configuring a new Bullet entity
 * at the position and rotation of the firing entity.
 *
 * Implementations are discovered via ServiceLoader by any module that needs to fire bullets,
 * such as PlayerControlSystem and EnemyShipProcessor.
 * This allows bullet creation behaviour to be swapped or extended without modifying the firing modules.
 *
 * @author corfixen
 */
public interface BulletSPI
{

    /**
     * Creates and returns a new Bullet entity fired by the given entity.
     * The bullet's position, rotation and visual shape are set based on the firing entity.
     *
     * Pre-Condition:  e != null, gameData != null, e has a valid position and rotation.
     * Post-Condition: a fully configured Bullet entity is returned, ready to be added to the world.
     *
     * @param e the entity that is firing the bullet.
     * @param gameData contains the UserInterface and the play-area for the game.
     * @return a fully configured Bullet entity fired by the given entity.
     */
    Entity createBullet(Entity e, GameData gameData);

}