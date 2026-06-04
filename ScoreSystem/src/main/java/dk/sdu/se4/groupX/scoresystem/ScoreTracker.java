package dk.sdu.se4.groupX.scoresystem;

import dk.sdu.mmmi.cbse.common.services.IScoreTracker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 * Implementation of IScoreTracker.
 * Responsible for communicating the final game score to the HighScoreSystem microservice via HTTP.
 * Fails gracefully if the HighScoreSystem is unreachable.
 */
public class ScoreTracker implements IScoreTracker
{

    /**
     * The HTTP client used to send requests to the HighScoreSystem microservice.
     */
    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * The base URL of the HighScoreSystem microservice.
     */
    private static final String SCORING_URL = "http://localhost:8080/score";


    /**
     * Constructor for ScoreTracker.
     * Logs a system message to confirm the component was successfully instantiated.
     */
    public ScoreTracker()
    {
        System.out.println("ScoreTracker instantiated");
    }




    @Override
    public void submitFinalScore(int score)
    {
        // Attempt to submit the final score to the HighScoreSystem microservice.
        try
        {
            // Build the HTTP POST request with the score as a request parameter.
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(SCORING_URL + "/submit?score=" + score)).POST(HttpRequest.BodyPublishers.noBody()).build();

            // Send the request and capture the response.
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Log the result.
            System.out.println("Score submitted: " + score + " | Response: " + response.statusCode());

        }
        catch (Exception e)
        {
            System.out.println("ScoringSystem unreachable. Score not submitted: " + e.getMessage());
        }
    }


}