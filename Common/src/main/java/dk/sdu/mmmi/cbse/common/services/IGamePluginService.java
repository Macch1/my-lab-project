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
public interface IGamePluginService {

    /**
     * Starts the game using the gameData and World.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    void start(GameData gameData, World world);


    /**
     * Stops the game using the gameData and World.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    void stop(GameData gameData, World world);
}
