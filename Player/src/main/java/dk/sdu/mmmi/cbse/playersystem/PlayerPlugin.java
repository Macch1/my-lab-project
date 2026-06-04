package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import dk.sdu.se4.groupX.commonplayer.Player;


public class PlayerPlugin implements IGamePluginService
{



    public PlayerPlugin()
    {

    }




    @Override
    public void start(GameData gameData, World world)
    {
        // .
        System.out.println("Player plugin starting.");

        // Add Player to the world.
        world.addEntity(createPlayerShip(gameData));
    }



    /**
     *
     * @param gameData
     * @return
     */
    private Player createPlayerShip(GameData gameData)
    {
        // .
        Player playerShip = new Player();

        // .
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

        // .
        return playerShip;
    }



    @Override
    public void stop(GameData gameData, World world)
    {
        // .
        System.out.println("Player plugin stopping.");

        // Remove all player entities from the world.
        for (Entity player : world.getEntities(Player.class))
        {
            // .
            world.removeEntity(player);
        }
    }


}


