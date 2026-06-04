package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;


/**
 * IPostEntityProcessingService is the service interface for all post-entity processors in the game.
 * Implementations are responsible for cross-entity logic that must run after all
 * IEntityProcessingService implementations have completed their updates for the current frame.
 *
 * The key distinction from IEntityProcessingService is the timing guarantee —
 * PostEntityProcess() is always called after all EntityProcess() calls have completed,
 * ensuring that all entity states are up to date before cross-entity logic is applied.
 *
 * The primary use case is collision detection and resolution.
 *
 * Implementations are discovered and loaded by Core at startup via ServiceLoader.
 * All registered implementations are called once per frame by Game.java's update() method,
 * after all IEntityProcessingService implementations have been called.
 *
 * @author jcs
 */
public interface IPostEntityProcessingService {

    /**
     * Performs post-processing on all entities after all IEntityProcessingService
     * implementations have been called for the current frame.
     * Used for cross-entity logic such as collision detection and resolution.
     *
     * Pre-Condition:  gameData != null, world != null, all IEntityProcessingService
     *                 implementations have already been called for the current frame.
     * Post-Condition: post-processing logic applied to all relevant entities in the world.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    void PostEntityProcess(GameData gameData, World world);

}