package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;


/**
 * BulletControlSystem is responsible for managing the lifecycle of all Bullet entities.
 * Implements both IEntityProcessingService and BulletSPI —
 * acting as both the bullet processor and the bullet factory.
 *
 * As IEntityProcessingService — moves all bullets forward each frame,
 * removes bullets that have left the screen bounds, and handles bullet health.
 *
 * As BulletSPI — creates and configures new Bullet entities at the position
 * and rotation of the firing entity, ready to be added to the world.
 */
public class BulletControlSystem implements IEntityProcessingService, BulletSPI
{


    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////    IEntityProcessingService Methods    ////////////////////
    ///


    /**
     * Moves all bullets forward each frame and removes bullets that have
     * left the screen bounds or have zero health.
     *
     * Pre-Condition:  gameData != null, world != null.
     * Post-Condition: all bullets moved forward, out-of-bounds and dead bullets removed from world.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void EntityProcess(GameData gameData, World world)
    {
        // Process all bullet entities currently in the world.
        for (Entity bullet : world.getEntities(Bullet.class))
        {
            // Check if the bullet is dead — remove it and skip to the next.
            if (this.handleHealth(bullet, world))
            {
                continue; // Skip dead entities.
            }

            // Calculate the movement direction based on the bullet's rotation.
            double changeX = Math.cos(Math.toRadians(bullet.Get_Rotation()));
            double changeY = Math.sin(Math.toRadians(bullet.Get_Rotation()));

            // Move the bullet forward by 3 units in its current direction.
            bullet.Set_X(bullet.Get_X() + changeX * 3);
            bullet.Set_Y(bullet.Get_Y() + changeY * 3);

            // Remove the bullet if it has left the screen bounds.
            if ( (bullet.Get_X() < (- gameData.getDisplayWidth()))   ||   (bullet.Get_X() > (gameData.getDisplayWidth() * 2))   ||   (bullet.Get_Y() < (- gameData.getDisplayHeight()))   ||   (bullet.Get_Y() > (gameData.getDisplayHeight() * 2)) )
            {
                // Bullet has left the screen bounds — remove it from the world.
                world.removeEntity(bullet);
            }
        }
    }




    ////////////////////////////////////////////////////////////////////
    ////////////////////    BulletSPI Methods    ////////////////////
    ///


    /**
     * Creates and returns a new Bullet entity fired by the given shooter entity.
     * The bullet is spawned slightly ahead of the shooter in its current direction,
     * inheriting the shooter's rotation.
     *
     * Pre-Condition:  shooter != null, gameData != null, shooter has a valid position and rotation.
     * Post-Condition: a fully configured Bullet entity is returned, ready to be added to the world.
     *
     * @param shooter the entity that is firing the bullet.
     * @param gameData contains the UserInterface and the play-area for the game.
     * @return a fully configured Bullet entity fired by the shooter.
     */
    @Override
    public Entity createBullet(Entity shooter, GameData gameData)
    {
        // Create a new Bullet entity.
        Entity bullet = new Bullet();

        // Set the bullet's visual shape.
        bullet.Set_PolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);

        // Calculate the spawn position slightly ahead of the shooter.
        double changeX = Math.cos(Math.toRadians(shooter.Get_Rotation()));
        double changeY = Math.sin(Math.toRadians(shooter.Get_Rotation()));

        // Spawn the bullet 10 units ahead of the shooter in its current direction.
        bullet.Set_X(shooter.Get_X() + changeX * 10);
        bullet.Set_Y(shooter.Get_Y() + changeY * 10);

        // Inherit the shooter's rotation so the bullet travels in the same direction.
        bullet.Set_Rotation(shooter.Get_Rotation());

        // Set the bullet's collision radius.
        bullet.Set_Radius(1);

        // Configure collision properties — bullets can collide and deal damage on impact.
        bullet.Set_Can_Collide(true);
        bullet.Set_CanTake_CollideDamage(true);
        bullet.Set_CanTake_Damaged(true);
        bullet.Set_CollisionDamage(10);
        bullet.Set_Health(1);

        // Return the fully configured bullet entity.
        return bullet;
    }




    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    /**
     * Checks if the bullet's health has reached zero and removes it from the world if so.
     * @param bullet the bullet entity to check.
     * @param world the game world to remove the bullet from.
     * @return true if the bullet was removed, false otherwise.
     */
    private boolean handleHealth(Entity bullet, World world)
    {
        // If the bullet's health is zero or below, remove it from the world.
        if (bullet.Get_Health() <= 0)
        {
            // Remove the dead bullet from the world.
            world.removeEntity(bullet);
            return true;
        }

        // Bullet is still alive.
        return false;
    }


}


