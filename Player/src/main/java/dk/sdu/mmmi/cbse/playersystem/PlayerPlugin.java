package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import dk.sdu.se4.groupX.commonplayer.Player;


public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }

    private Entity createPlayerShip(GameData gameData) {

        Entity playerShip = new Player();
        playerShip.Set_PolygonCoordinates(-5,-5,10,0,-5,5);
        playerShip.Set_X(gameData.getDisplayHeight()/2);
        playerShip.Set_Y(gameData.getDisplayWidth()/2);
        playerShip.Set_Radius(8);

        // Health and collision logic.
        playerShip.Set_Can_Collide(true);
        playerShip.Set_CanTake_CollideDamage(true);
        playerShip.Set_CanTake_Damaged(true);
        playerShip.Set_CollisionDamage(100);
        playerShip.Set_Health(100);

        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

}
