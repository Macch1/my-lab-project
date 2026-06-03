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
class Game {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private final Text scoreText = new Text(10, 20, "Score: 0");
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServiceList;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;
    private IScoreTracker scoreTracker = null;


    /**
     *
     * @param gamePluginServices
     * @param entityProcessingServiceList
     * @param postEntityProcessingServices
     */
    Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServiceList, List<IPostEntityProcessingService> postEntityProcessingServices)
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
        // .
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(scoreText);

        // .
        Scene scene = new Scene(gameWindow);

        // .
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT))
            {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT))
            {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP))
            {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE))
            {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT))
            {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT))
            {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP))
            {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE))
            {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }

        });

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getGamePluginServices()) {
            iGamePlugin.start(gameData, world);
        }
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.Get_PolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }

        // .
        this.scoreTracker = getScoreTracker();

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
        // Check if player is alive before processing.
        boolean playerAliveBefore = isPlayerAlive();
        System.out.println("Frame start — playerAliveBefore: " + playerAliveBefore);

        // .
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices())
        {
            entityProcessorService.process(gameData, world);
        }

        // .
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices())
        {
            postEntityProcessorService.process(gameData, world);
        }


        // If player was alive before but is gone now — game over, submit final score.
        if ((playerAliveBefore)   &&   (!isPlayerAlive())   &&   (scoreTracker != null))
        {
            scoreTracker.submitFinalScore();
        }

        // .
        System.out.println("Frame end — playerAliveAfter: " + isPlayerAlive());
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
        boolean alive = world.getEntities().stream().anyMatch(e -> e.Get_Type() == EntityType.Player);
        System.out.println("isPlayerAlive: " + alive + " | Entities in world: " + world.getEntities().size());
        return alive;
    }




}
