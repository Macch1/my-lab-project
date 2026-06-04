package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import dk.sdu.se4.groupX.commonplayer.Player;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class PlayerControlSystem implements IEntityProcessingService
{

    @Override
    public void EntityProcess(GameData gameData, World world)
    {
            
        for (Entity player : world.getEntities(Player.class))
        {
            if (this.handleHealth(player, world))
            {
                continue; // skip dead entities
            }

            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.Set_Rotation(player.Get_Rotation() - 5);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.Set_Rotation(player.Get_Rotation() + 5);
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.Get_Rotation()));
                double changeY = Math.sin(Math.toRadians(player.Get_Rotation()));
                player.Set_X(player.Get_X() + changeX);
                player.Set_Y(player.Get_Y() + changeY);
            }
            if(gameData.getKeys().isDown(GameKeys.SPACE)) {                
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {world.addEntity(spi.createBullet(player, gameData));}
                );
            }
            
            if (player.Get_X() < 0) {
                player.Set_X(1);
            }

            if (player.Get_X() > gameData.getDisplayWidth()) {
                player.Set_X(gameData.getDisplayWidth()-1);
            }

            if (player.Get_Y() < 0) {
                player.Set_Y(1);
            }

            if (player.Get_Y() > gameData.getDisplayHeight()) {
                player.Set_Y(gameData.getDisplayHeight()-1);
            }

                                        
        }
    }



    /**
     *
     * @return
     */
    private Collection<? extends BulletSPI> getBulletSPIs()
    {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }



    /**
     *
     * @param player
     * @param world
     * @return
     */
    private boolean handleHealth(Entity player, World world)
    {
        // If the health of the Player is zero or below, we define the Player as "Dead" / "Destroyed".
        // If the Player is "Dead" / "Destroyed" it gets handled below.
        if (player.Get_Health() <= 0)
        {
            // Remove the "Player" entity from the "world".
            world.removeEntity(player);

            // returns "true" to indicate the Player is "Dead" / "Destroyed".
            return true;
        }

        // returns "false" to indicate the Player is "Alive" / "Not destroyed".
        return false;
    }





}
