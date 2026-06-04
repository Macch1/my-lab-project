package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;


/**
 * BulletPlugin is responsible for managing the lifecycle of the Bullet component.
 * Unlike other plugins, BulletPlugin does not spawn any entities at startup —
 * bullets are created dynamically by BulletControlSystem via BulletSPI.createBullet()
 * when a firing entity requests a bullet.
 *
 * On stop, all remaining bullet entities are removed from the world.
 */
public class BulletPlugin implements IGamePluginService
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    // Note: No explicit constructor needed — default constructor is sufficient
    // since BulletPlugin holds no state that requires initialisation.




    /////////////////////////////////////////////////////////////////////////
    ////////////////////    IGamePluginService Methods    ////////////////////
    ///


    /**
     * Starts the Bullet plugin.
     * No entities are spawned at startup — bullets are created dynamically during gameplay.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: Bullet plugin is active and ready to create bullets on demand.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void start(GameData gameData, World world)
    {
        // Log that the Bullet plugin is starting.
        System.out.println("Bullet plugin starting.");
    }


    /**
     * Stops the Bullet plugin.
     * Removes all remaining Bullet entities from the world on stop.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: all Bullet entities removed from the world.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void stop(GameData gameData, World world)
    {
        // Log that the Bullet plugin is stopping.
        System.out.println("Bullet plugin stopping.");

        // Remove all remaining Bullet entities from the world.
        for (Entity e : world.getEntities())
        {
            // Check if the entity is a Bullet and remove it.
            if (e.getClass() == Bullet.class)
            {
                world.removeEntity(e);
            }
        }
    }


}