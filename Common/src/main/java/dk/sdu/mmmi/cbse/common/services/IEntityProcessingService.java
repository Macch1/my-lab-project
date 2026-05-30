package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 *
 *
 * @author jcs
 */
public interface IEntityProcessingService {

    /**
     * Process handles updating changes to the game, in regards to the game-world, game-controls, and game-entities.
     *
     *
     *
     * Pre-Condition: gameData != null, world != null, called once per game tick.
     * Post-Condition: all entities managed by this service updated for the current tick.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    void process(GameData gameData, World world);
}
