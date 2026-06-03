module ScoreSystem
{
    requires Common;
    requires java.net.http;

    provides dk.sdu.mmmi.cbse.common.services.IGamePluginService with dk.sdu.se4.groupX.scoresystem.ScorePlugin;
    provides dk.sdu.mmmi.cbse.common.services.IScoreTracker with dk.sdu.se4.groupX.scoresystem.ScoreTracker;
}