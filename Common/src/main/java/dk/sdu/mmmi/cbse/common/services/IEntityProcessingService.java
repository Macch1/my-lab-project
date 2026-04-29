package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 *
 * Pre-Condition:
 * Post-Condition:
 *
 */
public interface IEntityProcessingService {

    /**
     * Process handles updating changes to the game, in regards to the game-world, game-controls, and game-entities.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     * @throws
     */
    void process(GameData gameData, World world);
}
