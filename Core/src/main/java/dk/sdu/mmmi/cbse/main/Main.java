package dk.sdu.mmmi.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {



    public static void main(String[] args)
    {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception
    {

        // Start the HighScoreSystem as a separate process before the game starts.
        try
        {
            new ProcessBuilder("java", "-jar", "HighScoreSystem/target/HighScoreSystem-0.0.1-SNAPSHOT.jar").inheritIO().start();

            // Give HighScoreSystem time to start up before the game begins.
            Thread.sleep(1500);

            System.out.println("HighScoreSystem started.");
        }
        catch (Exception e)
        {
            // Game continues even if HighScoreSystem fails to start.
            System.out.println("HighScoreSystem could not be started: " + e.getMessage());
        }

        // .
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ModuleConfig.class);

        // .
        for (String beanName : ctx.getBeanDefinitionNames())
        {
            System.out.println(beanName);
        }

        // .
        Game game = ctx.getBean(Game.class);
        game.start(window);
        game.render();        

    }

}
