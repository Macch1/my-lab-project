package dk.sdu.se4.groupX.enemyship;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.se4.groupX.commonenemy.Enemy;


/**
 * EnemyShipPlugin is responsible for managing the lifecycle of Enemy entities in the game world.
 * Spawns the initial set of enemy ships at game startup and on restart,
 * and removes all Enemy entities from the world when the game stops or restarts.
 *
 * The position and configuration of each enemy are handled by EnemyCreationHelper,
 * which ensures enemies spawn at a random position along a screen edge aimed towards the center.
 */
public class EnemyShipPlugin implements IGamePluginService
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    /**
     * Default constructor for EnemyShipPlugin.
     */
    public EnemyShipPlugin()
    {

    }




    /////////////////////////////////////////////////////////////////////////
    ////////////////////    IGamePluginService Methods    ////////////////////
    ///


    /**
     * Starts the EnemyShip plugin — spawns the initial set of enemy ships into the world.
     * Called once at game startup and once on each restart by Game.java's restart() method.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: the initial set of enemy ships has been added to the world,
     *                 and the enemy count has been incremented accordingly.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void start(GameData gameData, World world)
    {
        // Log that the EnemyShip plugin is starting.
        System.out.println("EnemyShip plugin starting.");

        // Spawn the initial set of enemy ships up to the maximum allowed count.
        for (int i = 0; i < world.Get_MaxEnemies(); i++)
        {
            // Create a new enemy entity at a random screen edge position.
            Entity enemy = EnemyCreationHelper.createEnemy(gameData, world);

            // Add the enemy to the world and increment the enemy count.
            world.addEntity(enemy);
            world.AddTo_CurrentEnemyCount(1);
        }
    }


    /**
     * Stops the EnemyShip plugin — removes all Enemy entities from the world.
     * Called once when the game is stopped or restarted by Game.java's restart() method.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: all Enemy entities removed from the world,
     *                 and the enemy count reset to zero.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void stop(GameData gameData, World world)
    {
        // Log that the EnemyShip plugin is stopping.
        System.out.println("EnemyShip plugin stopping.");

        // Remove all Enemy entities from the world.
        for (Entity enemy : world.getEntities(Enemy.class))
        {
            // Remove the enemy entity from the world.
            world.removeEntity(enemy);
        }

        // Reset the enemy count to zero.
        world.Set_CurrentEnemyCount(0);
    }




    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    // Reserved for future use.


}