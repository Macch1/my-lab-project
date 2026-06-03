package dk.sdu.se4.groupX.scoresystem;

import dk.sdu.mmmi.cbse.common.services.IScoreTracker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ScoreTracker implements IScoreTracker
{

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private static final String SCORING_URL = "http://localhost:8080/score";


    public ScoreTracker()
    {
        System.out.println("ScoreTracker instantiated");
    }



    @Override
    public int getScore() {
        return 0;
    }



    @Override
    public void submitFinalScore(int score)
    {
        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SCORING_URL + "/submit?score=" + score))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("Score submitted: " + score +
                    " | Response: " + response.statusCode());
        }
        catch (Exception e)
        {
            System.out.println("ScoringSystem unreachable. Score not submitted: " + e.getMessage());
        }
    }


}