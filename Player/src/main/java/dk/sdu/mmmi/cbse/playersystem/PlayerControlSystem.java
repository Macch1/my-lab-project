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


/**
 * PlayerControlSystem is responsible for processing the Player entity each frame.
 * Handles player input — rotation, movement and shooting —
 * boundary wrapping at the screen edges, and player health checks.
 *
 * Discovers BulletSPI implementations via ServiceLoader each time the player fires,
 * allowing the bullet creation behaviour to be swapped without modifying this class.
 */
public class PlayerControlSystem implements IEntityProcessingService
{


    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////    IEntityProcessingService Methods    ////////////////////
    ///


    /**
     * Processes the Player entity each frame.
     * Handles health checks, input, movement, shooting and boundary wrapping.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: player position, rotation and state updated for the current frame.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void EntityProcess(GameData gameData, World world)
    {
        // Process all Player entities currently in the world.
        for (Entity player : world.getEntities(Player.class))
        {
            // Check if the player is dead — remove it and skip to the next.
            if (this.handleHealth(player, world))
            {
                // Skip dead entities.
                continue;
            }

            // Rotate the player left if the LEFT key is held.
            if (gameData.getKeys().isDown(GameKeys.LEFT))
            {
                // Decrease the rotation by 5 degrees.
                player.Set_Rotation(player.Get_Rotation() - 5);
            }

            // Rotate the player right if the RIGHT key is held.
            if (gameData.getKeys().isDown(GameKeys.RIGHT))
            {
                // Increase the rotation by 5 degrees.
                player.Set_Rotation(player.Get_Rotation() + 5);
            }

            // Move the player forward if the UP key is held.
            if (gameData.getKeys().isDown(GameKeys.UP))
            {
                // Calculate the movement direction based on the player's rotation.
                double changeX = Math.cos(Math.toRadians(player.Get_Rotation()));
                double changeY = Math.sin(Math.toRadians(player.Get_Rotation()));

                // Move the player forward by 1 unit in its current direction.
                player.Set_X(player.Get_X() + changeX);
                player.Set_Y(player.Get_Y() + changeY);
            }

            // Fire a bullet if the SPACE key is held.
            if(gameData.getKeys().isDown(GameKeys.SPACE))
            {
                // Discover the first available BulletSPI implementation and use it to create a bullet.
                this.getBulletSPIs().stream().findFirst().ifPresent(spi -> {world.addEntity(spi.createBullet(player, gameData));});
            }

            // Wrap the player's position around the screen edges.

            // Wrap the player's X position — left edge.
            if (player.Get_X() < 0)
            {
                // Player has gone off the left edge — wrap to the right edge.
                player.Set_X(1);
            }

            // Wrap the player's X position — right edge.
            if (player.Get_X() > gameData.getDisplayWidth())
            {
                // Player has gone off the right edge — wrap to the left edge.
                player.Set_X(gameData.getDisplayWidth()-1);
            }

            // Wrap the player's Y position — top edge.
            if (player.Get_Y() < 0)
            {
                // Player has gone off the top edge — wrap to the bottom edge.
                player.Set_Y(1);
            }

            // Wrap the player's Y position — bottom edge.
            if (player.Get_Y() > gameData.getDisplayHeight())
            {
                // Player has gone off the bottom edge — wrap to the top edge.
                player.Set_Y(gameData.getDisplayHeight()-1);
            }
        }
    }




    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    /**
     * Discovers all available BulletSPI implementations via ServiceLoader.
     * Called each time the player fires to support live component swapping.
     *
     * "Collection<? extends BulletSPI>"
     * We use Collection so we can store implementations without fear of duplications.
     * We use Wildcard so we can store any implementation of BulletSPI.
     * Link = https://www.geeksforgeeks.org/java/wildcards-in-java/
     *
     * "ServiceLoader.load(Interface.class)"
     * Finds and loads all registered implementations of an Interface available.
     * Link = https://www.geeksforgeeks.org/java/java-mdoules-service-implementation-module/
     *
     * @return a collection of all discovered BulletSPI implementations.
     */
    private Collection<? extends BulletSPI> getBulletSPIs()
    {
        // Discover all registered BulletSPI implementations from the module path.
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }


    /**
     * Checks if the player's health has reached zero and removes it from the world if so.
     * Called at the start of each frame before any input processing.
     *
     * @param player the player entity to check.
     * @param world the game world to remove the player from if dead.
     * @return true if the player was removed, false otherwise.
     */
    private boolean handleHealth(Entity player, World world)
    {
        // If the health of the Player is zero or below, we define the Player as "Dead" / "Destroyed".
        if (player.Get_Health() <= 0)
        {
            // Remove the "Player" entity from the "world".
            world.removeEntity(player);

            // Return true to indicate the Player is "Dead" / "Destroyed".
            return true;
        }

        // Return false to indicate the Player is "Alive" / "Not destroyed".
        return false;
    }


}