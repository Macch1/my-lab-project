package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 *
 * Pre-Condition:
 * Post-Condition:
 *
 * @author jcs
 */
public interface IPostEntityProcessingService {

    /**
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    void process(GameData gameData, World world);
}
