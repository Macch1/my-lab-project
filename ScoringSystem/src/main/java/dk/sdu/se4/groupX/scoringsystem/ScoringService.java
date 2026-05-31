package dk.sdu.se4.groupX.scoringsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;



@SpringBootApplication
@RestController
public class ScoringSystem
{

    private Long totalScore = 0L;

    private Long totalAsteroids = 0L;

    private Long totalEnemyShips = 0L;

    private int currentHealth = 0;




    // .
    public static void main(String[] args) {
        SpringApplication.run(ScoringSystem.class, args);
    }




    // .
    @GetMapping("/score/total")
    public Long get_TotalScore() {
        return this.totalScore;
    }

    // .
    @PostMapping("/score/set")
    public Long set_TotalScore(@RequestParam(value = "point") Long point) {
        this.totalScore = point;
        return this.totalScore;
    }

    // .
    @PutMapping("/score/add")
    public Long addTo_TotalScore(@RequestParam(value = "point") Long point) {
        this.totalScore += point;
        return this.totalScore;
    }

    // .
    @PutMapping("/score/remove")
    public Long removeFrom_TotalScore(@RequestParam(value = "point") Long point) {
        this.totalScore -= point;
        return this.totalScore;
    }




    // .
    @GetMapping("/health/total")
    public int get_CurrentHealth() {
        return this.currentHealth;
    }

    // .
    @PostMapping("/health/set")
    public int set_CurrentHealth(@RequestParam(value = "point") int point) {
        this.currentHealth = point;
        return this.currentHealth;
    }

    // .
    @PutMapping("/health/add")
    public int addTo_CurrentHealth(@RequestParam(value = "point") int point) {
        this.currentHealth += point;
        return this.currentHealth;
    }

    // .
    @PutMapping("/health/remove")
    public int removeFrom_CurrentHealth(@RequestParam(value = "point") int point) {
        this.currentHealth -= point;
        return this.currentHealth;
    }




    // .
    @GetMapping("/asteroids/total")
    public Long get_TotalAsteroids() {
        return this.totalAsteroids;
    }

    // .
    @PostMapping("/asteroids/set")
    public Long set_TotalAsteroids(@RequestParam(value = "point") Long point) {
        this.totalAsteroids = point;
        return this.totalAsteroids;
    }

    // .
    @PutMapping("/asteroids/add")
    public Long addTo_TotalAsteroids(@RequestParam(value = "point") Long point) {
        this.totalAsteroids += point;
        return this.totalAsteroids;
    }

    // .
    @PutMapping("/asteroids/remove")
    public Long removeFrom_TotalAsteroids(@RequestParam(value = "point") Long point) {
        this.totalAsteroids -= point;
        return this.totalAsteroids;
    }




    // .
    @GetMapping("/enemyship/total")
    public Long get_TotalEnemyShips() {
        return this.totalEnemyShips;
    }

    // .
    @PostMapping("/enemyship/set")
    public Long set_TotalEnemyShips(@RequestParam(value = "point") Long point) {
        this.totalEnemyShips = point;
        return this.totalEnemyShips;
    }

    // .
    @PutMapping("/enemyship/add")
    public Long addTo_TotalEnemyShips(@RequestParam(value = "point") Long point) {
        this.totalEnemyShips += point;
        return this.totalEnemyShips;
    }

    // .
    @PutMapping("/enemyship/remove")
    public Long removeFrom_TotalEnemyShips(@RequestParam(value = "point") Long point) {
        this.totalEnemyShips -= point;
        return this.totalEnemyShips;
    }





}






