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
 *
 * @author jcs
 */
class Game
{


    // .
    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();

    // .
    private final Pane gameWindow = new Pane();

    // .
    private final Text scoreText = new Text(10, 20, "Score: 0");
    private final Text healthText = new Text(10, 40, "Health: 100");
    private final Text gameOverText = new Text("GAME OVER\nPress ENTER to restart");

    // .
    // .
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServiceList;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;

    // .
    private IScoreTracker scoreTracker = null;

    // .
    private boolean gameOver = false;





    /**
     *
     * @param gamePluginServices
     * @param entityProcessingServiceList
     * @param postEntityProcessingServices
     */
    public Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServiceList, List<IPostEntityProcessingService> postEntityProcessingServices)
    {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServiceList = entityProcessingServiceList;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }







    /**
     *
     * @param window
     * @throws Exception
     */
    public void start(Stage window) throws Exception
    {
        // //

        // .
        this.gameWindow.setPrefSize(this.gameData.getDisplayWidth(), this.gameData.getDisplayHeight());

        // .
        this.gameWindow.getChildren().add(this.scoreText);
        this.gameWindow.getChildren().add(this.healthText);

        // .
        Scene scene = new Scene(this.gameWindow);

        // Setup gameOverText.
        this.gameOverText.setX(this.gameData.getDisplayWidth() / 2.0 - 100);
        this.gameOverText.setY(this.gameData.getDisplayHeight() / 2.0);
        this.gameOverText.setVisible(false);

        // .
        this.gameWindow.getChildren().add(this.gameOverText);



        // //

        // .
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT))
            {
                this.gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT))
            {
                this.gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP))
            {
                this.gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE))
            {
                this.gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
            if (event.getCode().equals(KeyCode.ENTER))
            {
                this.gameData.getKeys().setKey(GameKeys.ENTER, true);
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT))
            {
                this.gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT))
            {
                this.gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP))
            {
                this.gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE))
            {
                this.gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
            if (event.getCode().equals(KeyCode.ENTER))
            {
                this.gameData.getKeys().setKey(GameKeys.ENTER, false);
            }
        });



        // //

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getGamePluginServices())
        {
            iGamePlugin.start(gameData, world);
        }

        // .
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.Get_PolygonCoordinates());
            this.polygons.put(entity, polygon);
            this.gameWindow.getChildren().add(polygon);
        }

        // .
        this.scoreTracker = this.getScoreTracker();

        

        // //

        // .
        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
    }





    /**
     *
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





    /**
     *
     */
    private void update()
    {
        // If game is over, only listen for ENTER.
        if (this.gameOver)
        {
            if (this.gameData.getKeys().isPressed(GameKeys.ENTER))
            {
                this.restart();
            }

            return;  // stops all normal processing
        }

        // Check if player is alive before processing.
        boolean playerAliveBefore = this.isPlayerAlive();

        // .
        // System.out.println("Frame start — playerAliveBefore: " + playerAliveBefore);

        // .
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices())
        {
            entityProcessorService.process(this.gameData, this.world);
        }

        // .
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices())
        {
            postEntityProcessorService.process(this.gameData, this.world);
        }


        // If player was alive before but is gone now — game over.
        if (playerAliveBefore && (!this.isPlayerAlive()))
        {
            this.gameOver();
        }

        // .
        // System.out.println("Frame end — playerAliveAfter: " + isPlayerAlive());
    }






    /**
     * Handles the game over state — shows game over text and submits final score.
     * The game loop continues running but update() returns early until ENTER is pressed.
     */
    private void gameOver()
    {
        // Set game over flag — update() will now only listen for ENTER.
        this.gameOver = true;

        // Show game over text.
        this.gameOverText.setVisible(true);

        // Submit final score to HighScoreSystem.
        if (this.scoreTracker != null)
        {
            this.scoreTracker.submitFinalScore(world.Get_CurrentScore());
        }

    }




    /**
     * Resets the game world and restarts all plugins.
     */
    private void restart()
    {
        // Stop all plugins and clean up
        for (IGamePluginService iGamePlugin : getGamePluginServices())
        {
            iGamePlugin.stop(this.gameData, this.world);
        }

        // Clear all polygons from the screen
        for (Polygon polygon : this.polygons.values())
        {
            this.gameWindow.getChildren().remove(polygon);
        }

        // .
        this.polygons.clear();

        // Reset world state
        this.world.Set_CurrentScore(0);
        this.world.Set_CurrentEnemyCount(0);
        this.world.Set_CurrentAsteroidCount(0);
        this.world.Set_MaxEnemies(World.MAX_ENEMIES_DEFAULT);
        this.world.Set_MaxAsteroids(World.MAX_ASTEROIDS_DEFAULT);

        // Hide game over text
        this.gameOver = false;
        this.gameOverText.setVisible(false);

        // Restart all plugins
        for (IGamePluginService iGamePlugin : getGamePluginServices())
        {
            iGamePlugin.start(this.gameData, this.world);
        }
    }




    private void draw()
    {
        // .
        scoreText.setText("Score: " + world.Get_CurrentScore());

        // .
        for (Entity polygonEntity : polygons.keySet())
        {
            // .
            if (!world.getEntities().contains(polygonEntity))
            {
                Polygon removedPolygon = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                gameWindow.getChildren().remove(removedPolygon);
            }
        }

        for (Entity entity : world.getEntities())
        {
            // .
            Polygon polygon = polygons.get(entity);

            // .
            if (polygon == null)
            {
                polygon = new Polygon(entity.Get_PolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }

            // .
            polygon.setTranslateX(entity.Get_X());
            polygon.setTranslateY(entity.Get_Y());
            polygon.setRotate(entity.Get_Rotation());
        }

    }




    // .
    public List<IGamePluginService> getGamePluginServices()
    {
        return gamePluginServices;
    }



    // .
    public List<IEntityProcessingService> getEntityProcessingServices()
    {
        return entityProcessingServiceList;
    }




    // .
    public List<IPostEntityProcessingService> getPostEntityProcessingServices()
    {
        return postEntityProcessingServices;
    }






    /**
     *
     * @return
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
     *
     * @return
     */
    private boolean isPlayerAlive()
    {
        // .
        Entity player = null;

        // .
        for (Entity entity : world.getEntities())
        {
            // .
            if (entity.Get_Type() == EntityType.Player)
            {
                // .
                player = entity;
                break;
            }
        }


        // If player found, update health display.
        if (player != null)
        {
            // .
            Update_PlayerHealth_Display(player);

            // .
            return true;
        }
        else
        {
            // .
            this.healthText.setText("Health: 0");
        }

        return false;
    }




    /**
     * Updates the health display text with the player's current health.
     * @param player the player entity
     */
    private void Update_PlayerHealth_Display(Entity player)
    {
        // .
        this.healthText.setText("Health: " + (int) player.Get_Health());
    }




}
