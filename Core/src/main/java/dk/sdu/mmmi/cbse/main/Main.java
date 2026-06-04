package dk.sdu.mmmi.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * Main is the entry point of the Asteroids game.
 * Extends JavaFX's Application class to initialise the game window.
 * Bootstraps the Spring ApplicationContext via ModuleConfig,
 * which discovers and wires all game components via ServiceLoader.
 */
public class Main extends Application
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Entry Point    ////////////////////
    ///


    /**
     * The main entry point of the application.
     * Launches the JavaFX application thread.
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args)
    {
        launch(Main.class);
    }




    ////////////////////////////////////////////////////////////////
    ////////////////////    JavaFX Methods    ////////////////////
    ///


    /**
     * Called by JavaFX after the application thread has been initialised.
     * Bootstraps the Spring ApplicationContext, retrieves the Game bean,
     * and starts the game loop.
     * @param window the primary JavaFX Stage provided by the application thread.
     * @throws Exception if the Spring ApplicationContext fails to initialise.
     */
    @Override
    public void start(Stage window) throws Exception
    {
        // Initialise the Spring ApplicationContext using ModuleConfig.
        // This discovers and wires all game components via ServiceLoader.
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ModuleConfig.class);

        // Print all registered Spring beans for debugging purposes.
        for (String beanName : ctx.getBeanDefinitionNames())
        {
            // Print the name of each registered Spring bean.
            System.out.println(beanName);
        }

        // Retrieve the Game bean from the Spring ApplicationContext.
        Game game = ctx.getBean(Game.class);

        // Start the game window and begin the game loop.
        game.start(window);
        game.render();

    }

}