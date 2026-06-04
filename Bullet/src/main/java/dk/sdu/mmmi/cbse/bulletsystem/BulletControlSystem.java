package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI
{



    @Override
    public void EntityProcess(GameData gameData, World world)
    {

        for (Entity bullet : world.getEntities(Bullet.class))
        {
            if (this.handleHealth(bullet, world))
            {
                continue; // skip dead entities
            }

            double changeX = Math.cos(Math.toRadians(bullet.Get_Rotation()));
            double changeY = Math.sin(Math.toRadians(bullet.Get_Rotation()));
            bullet.Set_X(bullet.Get_X() + changeX * 3);
            bullet.Set_Y(bullet.Get_Y() + changeY * 3);


            // Remove bullet if it has left the screen bounds.
            if ( (bullet.Get_X() < (- gameData.getDisplayWidth()))   ||   (bullet.Get_X() > (gameData.getDisplayWidth() * 2))   ||   (bullet.Get_Y() < (- gameData.getDisplayHeight()))   ||   (bullet.Get_Y() > (gameData.getDisplayHeight() * 2)) )
            {
                world.removeEntity(bullet);
            }
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData)
    {
        Entity bullet = new Bullet();
        bullet.Set_PolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);
        double changeX = Math.cos(Math.toRadians(shooter.Get_Rotation()));
        double changeY = Math.sin(Math.toRadians(shooter.Get_Rotation()));
        bullet.Set_X(shooter.Get_X() + changeX * 10);
        bullet.Set_Y(shooter.Get_Y() + changeY * 10);
        bullet.Set_Rotation(shooter.Get_Rotation());
        bullet.Set_Radius(1);

        // Health and collision logic.
        bullet.Set_Can_Collide(true);
        bullet.Set_CanTake_CollideDamage(true);
        bullet.Set_CanTake_Damaged(true);
        bullet.Set_CollisionDamage(10);
        bullet.Set_Health(1);

        return bullet;
    }



    private boolean handleHealth(Entity bullet, World world) {
        if (bullet.Get_Health() <= 0) {
            world.removeEntity(bullet);
            return true;
        }
        return false;
    }


}
