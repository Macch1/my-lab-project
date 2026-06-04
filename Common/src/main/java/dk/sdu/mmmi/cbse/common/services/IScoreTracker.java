package dk.sdu.mmmi.cbse.common.services;


/**
 * IScoreTracker is the service interface for communicating the final game score
 * to the HighScoreSystem microservice.
 * The score is tracked by the World data-type during gameplay, and submitted via HTTP
 * when the player dies.
 *
 * Implementations are discovered and loaded by Core at startup via ServiceLoader.
 * submitFinalScore() is called once per game session by Game.java when the player entity
 * is no longer present in the world.
 *
 * Pre-Condition:  world.Get_CurrentScore() contains the final score when submitFinalScore() is called.
 * Post-Condition: Final score is submitted to the HighScoreSystem microservice via HTTP.
 *                 Fails gracefully if HighScoreSystem is unreachable — game continues unaffected.
 */
public interface IScoreTracker
{

    /**
     * Submits the final score to the HighScoreSystem microservice via HTTP.
     * Called by Game.java when the player entity is no longer present in the world.
     * Fails gracefully if HighScoreSystem is unreachable.
     *
     * Pre-Condition:  score >= 0.
     * Post-Condition: Score submitted to HighScoreSystem, or failure logged if unreachable.
     *
     * @param score the final score to submit.
     */
    void submitFinalScore(int score);

}