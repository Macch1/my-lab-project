package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 *
 *
 * @author jcs
 */
public interface IPostEntityProcessingService {

    /**
     * Performs post-processing on all entities after all IEntityProcessingService
     * implementations have been called. Used for cross-entity logic such as collision detection.
     *
     *
     * Pre-Condition: gameData != null, world != null, all IEntityProcessingService implementations have already been called this tick.
     * Post-Condition: post-processing logic applied to all relevant entities.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    void PostEntityProcess(GameData gameData, World world);
}
