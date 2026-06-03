package dk.sdu.se4.groupX.commonplayer;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;

public class Player extends Entity
{

    public Player ()
    {
        // .
        super();

        // .
        this.Set_Type(EntityType.Player);

        // Move.
        this.Set_Can_Move(true);
        this.Set_CanBe_Pushed(true);

        // Health.
        this.Set_Health(100);
        this.Set_CanTake_Damaged(true);

        // Collision.
        this.Set_Can_Collide(true);
        this.Set_CanTake_CollideDamage(true);
        this.Set_CollisionDamage(100);
    }
}