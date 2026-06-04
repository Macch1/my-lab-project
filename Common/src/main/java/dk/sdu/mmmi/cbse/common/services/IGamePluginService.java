package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;


/**
 * IGamePluginService is the service interface for all game plugins in the game.
 * Implementations are responsible for managing the lifecycle of their entities -
 * spawning them into the world on start, and removing them from the world on stop.
 *
 * Implementations are discovered and loaded by Core at startup via ServiceLoader.
 * All registered implementations have their start() method called once at game startup,
 * and their stop() method called once when the game is stopped or restarted.
 *
 * @author jcs
 */
public interface IGamePluginService {


    /**
     * Starts the plugin - spawns all entities managed by this plugin into the world.
     * Called once at game startup and once on each restart by Game.java's restart() method.
     *
     * Pre-Condition:  gameData != null, world != null, plugin has not already been started.
     * Post-Condition: all plugin entities added to the world.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    void start(GameData gameData, World world);


    /**
     * Stops the plugin - removes all entities managed by this plugin from the world.
     * Called once when the game is stopped or restarted by Game.java's restart() method.
     *
     * Pre-Condition:  gameData != null, world != null, plugin has been started.
     * Post-Condition: all plugin entities removed from the world.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    void stop(GameData gameData, World world);

}