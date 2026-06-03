package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;

/**
 *
 * @author corfixen
 */
public class Bullet extends Entity
{

    public Bullet ()
    {
        // .
        super();

        // .
        this.Set_Type(EntityType.Bullet);

        // Move.
        this.Set_Can_Move(true);
        this.Set_CanBe_Pushed(false);

        // Health.
        this.Set_Health(10);
        this.Set_CanTake_Damaged(true);

        // Collision.
        this.Set_Can_Collide(true);
        this.Set_CanTake_CollideDamage(true);
        this.Set_CollisionDamage(10);
    }

}
