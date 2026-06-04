package dk.sdu.se4.groupX.highscoresystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;



@SpringBootApplication
@RestController
public class HighScoreController
{

    // .
    public static void main(String[] args)
    {
        SpringApplication.run(HighScoreController.class, args);
    }




    // .
    @PostMapping("/score/submit")
    public ResponseEntity<String> submit_FinalScore(@RequestParam(value = "score") Long score)
    {
        try
        {
            String entry = LocalDateTime.now() + " - Final Score: " + score + "\n";

            Files.writeString(
                    Path.of("highscores.txt"),
                    entry,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            System.out.println("Highscore saved: " + score);
            return ResponseEntity.ok("Score saved: " + score);

        } catch (Exception e) {
            System.out.println("Failed to save score: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Failed to save score.");
        }
    }




    // .
    @GetMapping("/score/highscores")
    public ResponseEntity<String> get_Highscores()
    {
        try
        {
            // .
            Path path = Path.of("highscores.txt");

            // .
            if (!Files.exists(path))
            {
                return ResponseEntity.ok("No highscores yet.");
            }
            return ResponseEntity.ok(Files.readString(path));
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().body("Failed to read highscores.");
        }
    }


}