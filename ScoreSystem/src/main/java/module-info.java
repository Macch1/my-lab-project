import dk.sdu.mmmi.cbse.common.services.IScoreTracker;
import dk.sdu.se4.groupX.score.ScoreTracker;

module ScoreSystem
{
    requires Common;
    requires java.net.http;

    provides dk.sdu.mmmi.cbse.common.services.IGamePluginService with dk.sdu.se4.groupX.score.ScorePlugin;
    provides IScoreTracker with ScoreTracker;
}