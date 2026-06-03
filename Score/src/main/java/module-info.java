module Score
{
    requires Common;
    requires java.net.http;

    provides dk.sdu.mmmi.cbse.common.services.IGamePluginService with dk.sdu.se4.groupX.score.ScorePlugin;
    provides dk.sdu.mmmi.cbse.common.services.IScoreService with dk.sdu.se4.groupX.score.ScoreService;
}