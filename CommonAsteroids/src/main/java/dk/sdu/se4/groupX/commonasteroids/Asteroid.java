package dk.sdu.se4.groupX.commonasteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;

public class Asteroid extends Entity
{

    public Asteroid ()
    {
        // .
        super();

        // .
        this.Set_Type(EntityType.Obstacle);

        // Move.
        this.Set_Can_Move(true);
        this.Set_CanBe_Pushed(false);

        // Health.
        this.Set_Health(10);
        this.Set_CanTake_Damaged(true);

        // Collision.
        this.Set_Can_Collide(true);
        this.Set_CanTake_CollideDamage(true);
        this.Set_CollisionDamage(100);
    }

}