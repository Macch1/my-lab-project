package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;


/**
 * Bullet is the base entity type for all bullet entities in the game.
 * Extends Entity and sets the default configuration for bullet behaviour -
 * bullets can move, can collide, can take collision damage, and deal damage on impact.
 * Bullets cannot be pushed by the collision system.
 *
 * The visual shape, position and rotation of the bullet are set by the
 * BulletControlSystem at creation time via BulletSPI.createBullet().
 *
 * @author corfixen
 */
public class Bullet extends Entity
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    /**
     * Default constructor for Bullet.
     * Initialises all bullet-specific flags and values.
     * Sets the entity type to EntityType.Bullet.
     */
    public Bullet ()
    {
        // Initialise the base Entity fields.
        super();

        // Set the entity type to Bullet.
        this.Set_Type(EntityType.Bullet);

        // Movement - bullets can move freely but cannot be pushed by collisions.
        this.Set_Can_Move(true);
        this.Set_CanBe_Pushed(false);

        // Health - bullets have low health and can be destroyed on impact.
        this.Set_Health(10);
        this.Set_CanTake_Damaged(true);

        // Collision - bullets can collide, take collision damage, and deal damage on impact.
        this.Set_Can_Collide(true);
        this.Set_CanTake_CollideDamage(true);
        this.Set_CollisionDamage(10);
    }

}