package dk.sdu.se4.groupX.scoresystem;

import dk.sdu.mmmi.cbse.common.services.IScoreTracker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ScoreTracker implements IScoreTracker {

    private int currentScore = 0;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private static final String SCORING_URL = "http://localhost:8080/score";


    public ScoreTracker()
    {
        System.out.println("ScoreTracker instantiated");
    }


    @Override
    public void addScore(int points)
    {
        currentScore += points;
        System.out.println("Score updated: " + currentScore);
    }

    @Override
    public int getScore() {
        return currentScore;
    }

    @Override
    public void submitFinalScore()
    {
        // .
        try
        {
            System.out.println("submitFinalScore() called from: " + Thread.currentThread().getStackTrace()[2]);

            String json = "{\"score\": " + currentScore + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SCORING_URL + "/submit"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("Score submitted: " + currentScore +
                    " | Response: " + response.statusCode());

        }
        catch (Exception e)
        {
            // Graceful failure — game continues even if ScoringSystem is down
            System.out.println("ScoringSystem unreachable. Score not submitted: " + e.getMessage());
        }

        // Reset score for next game
        currentScore = 0;
    }
}