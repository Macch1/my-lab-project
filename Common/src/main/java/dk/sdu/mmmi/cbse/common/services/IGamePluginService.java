package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 *
 *
 * @author jcs
 */
public interface IGamePluginService {

    /**
     * Starts the game using the gameData and World.
     *
     *
     *
     * Pre-Condition: gameData != null, world != null, plugin has not already been started.
     * Post-Condition: all plugin entities added to world.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    void start(GameData gameData, World world);


    /**
     * Stops the game using the gameData and World.
     *
     *
     *
     * Pre-Condition: gameData != null, world != null, plugin has been started.
     * Post-Condition: all plugin entities removed from world.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    void stop(GameData gameData, World world);
}
