package dk.sdu.se4.groupX.scoresystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;


/**
 * ScorePlugin is responsible for managing the lifecycle of the ScoreSystem component.
 * Unlike other plugins, ScorePlugin does not spawn any entities into the world.
 * It exists solely to register the ScoreSystem as an active game component,
 * allowing IScoreTracker to be discovered via ServiceLoader at startup.
 */
public class ScorePlugin implements IGamePluginService
{


    /////////////////////////////////////////////////////////////////////////
    ////////////////////    IGamePluginService Methods    ////////////////////
    ///


    /**
     * Starts the ScorePlugin.
     * No entities are spawned - the ScoreSystem is a pure service component.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: ScorePlugin is active and IScoreTracker is available for discovery.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void start(GameData gameData, World world)
    {
        // Nothing to spawn - ScoreSystem is a pure service component.
        System.out.println("ScorePlugin starting.");
    }



    /**
     * Stops the ScorePlugin.
     * No entities to remove - the ScoreSystem spawned nothing into the world.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: ScorePlugin is inactive.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void stop(GameData gameData, World world)
    {
        // Nothing to remove - no entities were spawned.
        System.out.println("ScorePlugin stopping.");
    }


}