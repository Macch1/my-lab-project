package dk.sdu.se4.groupX.enemyship;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.se4.groupX.commonenemy.Enemy;

public class EnemyShipPlugin implements IGamePluginService {


    private Entity enemyShips;

    public EnemyShipPlugin()
    {

    }


    /**
     * Starts the game using the gameData and World.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void start(GameData gameData, World world)
    {
        // Add entities to the world
        enemyShips = createEnemyShip(gameData);
        world.addEntity(enemyShips);

    }



    /**
     *
     * @param gameData
     * @return
     */
    private Entity createEnemyShip(GameData gameData)
    {

        Entity enemyShip = new EnemyShip();
        enemyShip.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemyShip.setX(gameData.getDisplayHeight()/2);
        enemyShip.setY(gameData.getDisplayWidth()/2);
        enemyShip.setRadius(8);
        enemyShip.setRotation();
        return enemyShip;
    }



    /**
     * Stops the game using the gameData and World.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void stop(GameData gameData, World world)
    {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            // Remove entities
            world.removeEntity(enemy);
        }
    }

}
