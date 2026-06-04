package dk.sdu.se4.groupX.commonplayer;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;


/**
 * Player is the base entity type for the player entity in the game.
 * Extends Entity and sets the default configuration for player behaviour —
 * the player can move, can be pushed by the collision system, can collide,
 * can take collision damage, and deals damage on impact.
 *
 * The visual shape, position and rotation of the player are set by
 * PlayerPlugin at startup via createPlayerShip().
 */
public class Player extends Entity
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    /**
     * Default constructor for Player.
     * Initialises all player-specific flags and values.
     * Sets the entity type to EntityType.Player.
     */
    public Player ()
    {
        // Initialise the base Entity fields.
        super();

        // Set the entity type to Player.
        this.Set_Type(EntityType.Player);

        // Movement — the player can move freely and can be pushed by collisions.
        this.Set_Can_Move(true);
        this.Set_CanBe_Pushed(true);

        // Health — the player starts with full health and can be damaged.
        this.Set_Health(100);
        this.Set_CanTake_Damaged(true);

        // Collision — the player can collide, takes collision damage, and deals damage on impact.
        this.Set_Can_Collide(true);
        this.Set_CanTake_CollideDamage(true);
        this.Set_CollisionDamage(100);
    }


}