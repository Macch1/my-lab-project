/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import dk.sdu.mmmi.cbse.common.services.IScoreTracker;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


/**
 * Game is the central game loop controller.
 * Responsible for initialising the game window, managing the animation timer,
 * coordinating all plugin and processor updates each frame,
 * and handling game over and restart logic.
 *
 * Receives all discovered plugins and processors from ModuleConfig via Spring injection.
 * Communicates with the HighScoreSystem microservice via IScoreTracker on game over.
 *
 * @author jcs
 */
class Game
{

    // The core game data and world state - passed to all plugins and processors every frame.
    private final GameData gameData = new GameData();
    private final World world = new World();

    // Map of entities to their corresponding JavaFX polygons for rendering.
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();

    // The JavaFX pane that serves as the game window container.
    private final Pane gameWindow = new Pane();

    // HUD text elements displayed in the game window.
    private final Text scoreText = new Text(10, 20, "Score: 0");
    private final Text healthText = new Text(10, 40, "Health: 100");
    private final Text gameOverText = new Text("GAME OVER\nPress ENTER to restart");

    // Lists of all discovered plugins and processors - injected by Spring via ModuleConfig.
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServiceList;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;

    // The IScoreTracker implementation - discovered via ServiceLoader at startup.
    private IScoreTracker scoreTracker = null;

    // Flag indicating whether the game is currently in a game over state.
    private boolean gameOver = false;




    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    /**
     * Constructor for Game.
     * Receives all discovered plugins and processors from ModuleConfig via Spring injection.
     * @param gamePluginServices the list of all discovered IGamePluginService implementations.
     * @param entityProcessingServiceList the list of all discovered IEntityProcessingService implementations.
     * @param postEntityProcessingServices the list of all discovered IPostEntityProcessingService implementations.
     */
    public Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServiceList, List<IPostEntityProcessingService> postEntityProcessingServices)
    {
        // Store the injected plugin and processor lists.
        this.gamePluginServices = gamePluginServices;

        // Store the injected entity processor list.
        this.entityProcessingServiceList = entityProcessingServiceList;

        // Store the injected post-entity processor list.
        this.postEntityProcessingServices = postEntityProcessingServices;
    }




    ////////////////////////////////////////////////////////////////
    ////////////////////    Lifecycle Methods    ////////////////////
    ///


    /**
     * Initialises the game window, key bindings, plugins and score tracker.
     * Called once by Main.java after the Spring ApplicationContext has been initialised.
     * @param window the primary JavaFX Stage to render the game into.
     * @throws Exception if the game window fails to initialise.
     */
    public void start(Stage window) throws Exception
    {
        // Set the size of the game window to match the display dimensions.
        this.gameWindow.setPrefSize(this.gameData.getDisplayWidth(), this.gameData.getDisplayHeight());

        // Add the HUD text elements to the game window.
        this.gameWindow.getChildren().add(this.scoreText);
        this.gameWindow.getChildren().add(this.healthText);

        // Create the JavaFX scene with the game window as the root.
        Scene scene = new Scene(this.gameWindow);

        // Position the game over text in the center of the screen and hide it initially.
        this.gameOverText.setX(this.gameData.getDisplayWidth() / 2.0 - 100);
        this.gameOverText.setY(this.gameData.getDisplayHeight() / 2.0);
        this.gameOverText.setVisible(false);
        this.gameWindow.getChildren().add(this.gameOverText);

        // Register key pressed event handlers for all supported keys.
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT))
            {
                // Register LEFT key as pressed.
                this.gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT))
            {
                // Register RIGHT key as pressed.
                this.gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP))
            {
                // Register UP key as pressed.
                this.gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE))
            {
                // Register SPACE key as pressed.
                this.gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
            if (event.getCode().equals(KeyCode.ENTER))
            {
                // Register ENTER key as pressed.
                this.gameData.getKeys().setKey(GameKeys.ENTER, true);
            }
        });

        // Register key released event handlers for all supported keys.
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT))
            {
                // Register LEFT key as released.
                this.gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT))
            {
                // Register RIGHT key as released.
                this.gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP))
            {
                // Register UP key as released.
                this.gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE))
            {
                // Register SPACE key as released.
                this.gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
            if (event.getCode().equals(KeyCode.ENTER))
            {
                // Register ENTER key as released.
                this.gameData.getKeys().setKey(GameKeys.ENTER, false);
            }
        });

        // Start all registered game plugins - spawns initial entities into the world.
        for (IGamePluginService iGamePlugin : getGamePluginServices())
        {
            iGamePlugin.start(gameData, world);
        }

        // Create polygons for all entities spawned by the plugins and add them to the game window.
        for (Entity entity : world.getEntities())
        {
            Polygon polygon = new Polygon(entity.Get_PolygonCoordinates());
            this.polygons.put(entity, polygon);
            this.gameWindow.getChildren().add(polygon);
        }

        // Discover the IScoreTracker implementation via ServiceLoader.
        this.scoreTracker = this.getScoreTracker();

        // Set the scene on the window and display it.
        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
    }


    /**
     * Starts the JavaFX AnimationTimer that drives the game loop.
     * Called once by Main.java after start() has completed.
     * The game loop calls update(), draw() and key state update once per frame.
     */
    public void render()
    {
        new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                update();
                draw();
                gameData.getKeys().update();
            }

        }.start();
    }




    ////////////////////////////////////////////////////////////////
    ////////////////////    Game Loop Methods    ////////////////////
    ///


    /**
     * Called once per frame by the AnimationTimer.
     * If the game is over, listens only for ENTER to restart.
     * Otherwise processes all entity and post-entity processors,
     * and checks if the player has died.
     */
    private void update()
    {
        // If game is over, only listen for ENTER to restart.
        if (this.gameOver)
        {
            if (this.gameData.getKeys().isPressed(GameKeys.ENTER))
            {
                this.restart();
            }

            return;  // Skip all normal processing until restarted.
        }

        // Check if the player is alive before processing - used to detect death this frame.
        boolean playerAliveBefore = this.isPlayerAlive();

        // Call all IEntityProcessingService implementations for this frame.
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices())
        {
            entityProcessorService.EntityProcess(this.gameData, this.world);
        }

        // Call all IPostEntityProcessingService implementations for this frame.
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices())
        {
            postEntityProcessorService.PostEntityProcess(this.gameData, this.world);
        }

        // If the player was alive before processing but is gone now - trigger game over.
        if (playerAliveBefore && (!this.isPlayerAlive()))
        {
            this.gameOver();
        }
    }


    /**
     * Handles the game over state.
     * Sets the game over flag, shows the game over text,
     * and submits the final score to the HighScoreSystem microservice.
     * The game loop continues running but update() returns early until ENTER is pressed.
     */
    private void gameOver()
    {
        // Set the game over flag - update() will now only listen for ENTER.
        this.gameOver = true;

        // Show the game over text in the center of the screen.
        this.gameOverText.setVisible(true);

        // Submit the final score to the HighScoreSystem microservice.
        if (this.scoreTracker != null)
        {
            System.out.println("Game over - Final score: " + world.Get_CurrentScore());
            this.scoreTracker.submitFinalScore(world.Get_CurrentScore());
        }
    }


    /**
     * Resets the game world and restarts all plugins.
     * Stops all plugins, clears all polygons, resets world state,
     * hides the game over text, and restarts all plugins.
     */
    private void restart()
    {
        System.out.println("Game restarting...");

        // Stop all plugins and remove their entities from the world.
        for (IGamePluginService iGamePlugin : getGamePluginServices())
        {
            iGamePlugin.stop(this.gameData, this.world);
        }

        // Remove all polygons from the game window and clear the polygon map.
        for (Polygon polygon : this.polygons.values())
        {
            this.gameWindow.getChildren().remove(polygon);
        }
        this.polygons.clear();

        // Reset the world state - score, entity counts and spawn limits.
        this.world.Set_CurrentScore(0);
        this.world.Set_CurrentEnemyCount(0);
        this.world.Set_CurrentAsteroidCount(0);
        this.world.Set_MaxEnemies(World.MAX_ENEMIES_DEFAULT);
        this.world.Set_MaxAsteroids(World.MAX_ASTEROIDS_DEFAULT);

        // Hide the game over text and clear the game over flag.
        this.gameOver = false;
        this.gameOverText.setVisible(false);

        // Restart all plugins - respawns all initial entities into the world.
        for (IGamePluginService iGamePlugin : getGamePluginServices())
        {
            iGamePlugin.start(this.gameData, this.world);
        }
    }


    /**
     * Called once per frame by the AnimationTimer after update().
     * Updates the HUD text, removes polygons for destroyed entities,
     * and adds polygons for newly spawned entities.
     */
    private void draw()
    {
        // Update the score display with the current world score.
        scoreText.setText("Score: " + world.Get_CurrentScore());

        // Remove polygons for entities that are no longer in the world.
        for (Entity polygonEntity : polygons.keySet())
        {
            if (!world.getEntities().contains(polygonEntity))
            {
                // Remove the polygon from the map and the game window.
                Polygon removedPolygon = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                gameWindow.getChildren().remove(removedPolygon);
            }
        }

        // Add and update polygons for all entities currently in the world.
        for (Entity entity : world.getEntities())
        {
            // Get the existing polygon for this entity, or null if it is newly spawned.
            Polygon polygon = polygons.get(entity);

            // If the entity is newly spawned, create a new polygon and add it to the game window.
            if (polygon == null)
            {
                polygon = new Polygon(entity.Get_PolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }

            // Update the polygon's position and rotation to match the entity.
            polygon.setTranslateX(entity.Get_X());
            polygon.setTranslateY(entity.Get_Y());
            polygon.setRotate(entity.Get_Rotation());
        }
    }




    ////////////////////////////////////////////////////////////////
    ////////////////////    Getter Methods    ////////////////////
    ///


    /**
     * Returns the list of all discovered IGamePluginService implementations.
     * @return the list of game plugin services.
     */
    public List<IGamePluginService> getGamePluginServices()
    {
        return gamePluginServices;
    }


    /**
     * Returns the list of all discovered IEntityProcessingService implementations.
     * @return the list of entity processing services.
     */
    public List<IEntityProcessingService> getEntityProcessingServices()
    {
        return entityProcessingServiceList;
    }


    /**
     * Returns the list of all discovered IPostEntityProcessingService implementations.
     * @return the list of post-entity processing services.
     */
    public List<IPostEntityProcessingService> getPostEntityProcessingServices()
    {
        return postEntityProcessingServices;
    }




    ////////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    /**
     * Discovers the IScoreTracker implementation via ServiceLoader.
     * Called once at startup in start().
     * Returns null gracefully if no IScoreTracker implementation is available.
     * @return the first discovered IScoreTracker implementation, or null if none found.
     */
    private IScoreTracker getScoreTracker()
    {
        // "ServiceLoader.load(Interface.class)"
        // Finds and loads all registered implementations of an Interface available.
        // Link = https://www.geeksforgeeks.org/java/java-mdoules-service-implementation-module/

        // We find, load and collect the implementations of the "IScoreTracker" interface.
        Collection<? extends IScoreTracker> scoreTrackerImplementation = ServiceLoader.load(IScoreTracker.class).stream().map(ServiceLoader.Provider::get).collect(toList());

        // Returns the first implementation of the interface "IScoreTracker", or null if none is found.
        return scoreTrackerImplementation.stream().findFirst().orElse(null);
    }


    /**
     * Checks whether a Player entity is currently present in the world.
     * Also updates the health display if the player is found.
     * @return true if a Player entity exists in the world, false otherwise.
     */
    private boolean isPlayerAlive()
    {
        // Search for a Player entity in the world.
        Entity player = null;

        // Iterate over all entities in the world.
        for (Entity entity : world.getEntities())
        {
            // Check if the entity is of type Player.
            if (entity.Get_Type() == EntityType.Player)
            {
                // Player found - store the reference and stop searching.
                player = entity;
                break;
            }
        }

        // If the player was found, update the health display and return true.
        if (player != null)
        {
            Update_PlayerHealth_Display(player);
            return true;
        }
        else
        {
            // Player not found - set health display to zero.
            this.healthText.setText("Health: 0");
        }

        return false;
    }


    /**
     * Updates the health display text with the player's current health.
     * Called by isPlayerAlive() each frame when the player is alive.
     * @param player the player entity to read the health value from.
     */
    private void Update_PlayerHealth_Display(Entity player)
    {
        // Update the health text with the player's current health value.
        this.healthText.setText("Health: " + (int) player.Get_Health());
    }


}