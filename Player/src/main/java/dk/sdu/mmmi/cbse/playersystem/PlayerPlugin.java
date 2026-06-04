package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import dk.sdu.se4.groupX.commonplayer.Player;


/**
 * PlayerPlugin is responsible for managing the lifecycle of the Player entity.
 * Spawns a single Player entity into the world at game startup and on restart,
 * and removes all Player entities from the world when the game stops or restarts.
 *
 * The Player entity's visual shape and position are configured by createPlayerShip(),
 * while the default flags and values are set by the Player constructor.
 */
public class PlayerPlugin implements IGamePluginService
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    /**
     * Default constructor for PlayerPlugin.
     */
    public PlayerPlugin()
    {

    }




    /////////////////////////////////////////////////////////////////////////
    ////////////////////    IGamePluginService Methods    ////////////////////
    ///


    /**
     * Starts the Player plugin — spawns a single Player entity into the world.
     * Called once at game startup and once on each restart by Game.java's restart() method.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: a single Player entity has been added to the world.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void start(GameData gameData, World world)
    {
        // Log that the Player plugin is starting.
        System.out.println("Player plugin starting.");

        // Create and add the Player entity to the world.
        world.addEntity(this.createPlayerShip(gameData));
    }


    /**
     * Stops the Player plugin — removes all Player entities from the world.
     * Called once when the game is stopped or restarted by Game.java's restart() method.
     * Uses a loop rather than a direct reference in case the player was already
     * removed from the world by handleHealth() before stop() was called.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: all Player entities removed from the world.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void stop(GameData gameData, World world)
    {
        // Log that the Player plugin is stopping.
        System.out.println("Player plugin stopping.");

        // Remove all Player entities from the world.
        for (Entity player : world.getEntities(Player.class))
        {
            // Remove the Player entity from the world.
            world.removeEntity(player);
        }
    }




    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    /**
     * Creates and configures a new Player entity ready to be added to the world.
     * Sets the visual shape, spawn position and collision radius of the player ship.
     * Default flags and values are set by the Player constructor.
     *
     * @param gameData contains the display dimensions used to calculate the spawn position.
     * @return a fully configured Player entity ready to be added to the world.
     */
    private Player createPlayerShip(GameData gameData)
    {
        // Create a new Player entity — default flags and values set by Player constructor.
        Player playerShip = new Player();

        // Set the visual shape of the player ship.
        playerShip.Set_PolygonCoordinates(-5,-5,10,0,-5,5);

        // Spawn the player ship in the center of the screen.
        playerShip.Set_X(gameData.getDisplayHeight()/2);
        playerShip.Set_Y(gameData.getDisplayWidth()/2);

        // Set the collision radius of the player ship.
        playerShip.Set_Radius(8);

        // Override collision properties — player deals full damage on impact.
        playerShip.Set_Can_Collide(true);
        playerShip.Set_CanTake_CollideDamage(true);
        playerShip.Set_CanTake_Damaged(true);
        playerShip.Set_CollisionDamage(100);
        playerShip.Set_Health(100);

        // Return the fully configured player ship entity.
        return playerShip;
    }


}