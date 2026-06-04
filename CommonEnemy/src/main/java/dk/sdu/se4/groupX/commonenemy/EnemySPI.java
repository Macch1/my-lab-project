package dk.sdu.se4.groupX.commonenemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;


/**
 * EnemySPI is the service provider interface for creating enemy entities.
 * Implementations are responsible for creating and configuring a new Enemy entity
 * at the position and rotation of a given reference entity.
 *
 * Allows enemy creation behaviour to be swapped or extended without modifying
 * the modules that request enemy creation.
 */
public interface EnemySPI
{

    /**
     * Creates and returns a new Enemy entity based on the given reference entity.
     * The enemy's position, rotation and configuration are set by the implementation.
     *
     * Pre-Condition:  e != null, gameData != null, e has a valid position and rotation.
     * Post-Condition: a fully configured Enemy entity is returned, ready to be added to the world.
     *
     * @param e the reference entity used to determine the enemy's spawn position and rotation.
     * @param gameData contains the UserInterface and the play-area for the game.
     * @return a fully configured Enemy entity ready to be added to the world.
     */
    Entity createEnemy(Entity e, GameData gameData);

}