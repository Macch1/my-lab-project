package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;


/**
 * IEntityProcessingService is the service interface for all entity processors in the game.
 * Implementations are responsible for updating the state of their managed entities each frame,
 * including movement, input handling, shooting, health checks, and boundary wrapping.
 *
 * Implementations are discovered and loaded by Core at startup via ServiceLoader.
 * All registered implementations are called once per frame by Game.java's update() method,
 * before IPostEntityProcessingService implementations are called.
 *
 * @author jcs
 */
public interface IEntityProcessingService
{

    /**
     * Updates the state of all entities managed by this service for the current frame.
     * Called once per frame by Game.java's update() method.
     *
     * Pre-Condition:  gameData != null, world != null, world contains at least one entity.
     * Post-Condition: all entities managed by this service updated for the current frame.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    void EntityProcess(GameData gameData, World world);

}