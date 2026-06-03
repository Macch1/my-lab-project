package dk.sdu.se4.groupX.scoresystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class ScorePlugin implements IGamePluginService
{

    @Override
    public void start(GameData gameData, World world) {
        // Nothing to spawn — Score is a pure service component
        System.out.println("ScorePlugin started.");
        System.out.println("ScorePlugin.start() called");
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Nothing to remove — no entities were spawned
        System.out.println("ScorePlugin stopped.");
    }
}