// Common/src/main/java/dk/sdu/mmmi/cbse/common/services/IScoreTracker.java
package dk.sdu.mmmi.cbse.common.services;

/**
 * Service interface for tracking and submitting game scores.
 *
 * PRE-CONDITION:  addScore() called with a positive point value when an entity is destroyed.
 * POST-CONDITION: Score total is updated and persisted to the ScoringSystem on submitFinalScore().
 */
public interface IScoreTracker
{

    /**
     * Adds points to the current running score.
     * Called by processors at the moment of entity destruction.
     * @param points the point value to add
     */
    void addScore(int points);

    /**
     * Returns the current accumulated score.
     * @return current score
     */
    int getScore();

    /**
     * Submits the final score to the ScoringSystem microservice via HTTP.
     * Called when the player dies. Fails gracefully if ScoringSystem is unreachable.
     */
    void submitFinalScore();
}

