package dk.sdu.se4.groupX.commonasteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;


/**
 * Asteroid is the base entity type for all asteroid entities in the game.
 * Extends Entity and sets the default configuration for asteroid behaviour —
 * asteroids can move, can collide, can take collision damage, and deal damage on impact.
 * Asteroids cannot be pushed by the collision system.
 *
 * The visual shape, position, rotation and radius of the asteroid are set by
 * AsteroidCreationHelper at creation time, or by AsteroidSplitter when splitting.
 */
public class Asteroid extends Entity
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    /**
     * Default constructor for Asteroid.
     * Initialises all asteroid-specific flags and values.
     * Sets the entity type to EntityType.Obstacle.
     */
    public Asteroid ()
    {
        // Initialise the base Entity fields.
        super();

        // Set the entity type to Obstacle.
        this.Set_Type(EntityType.Obstacle);

        // Movement — asteroids can move freely but cannot be pushed by collisions.
        this.Set_Can_Move(true);
        this.Set_CanBe_Pushed(false);

        // Health — asteroids start with full health and can be destroyed.
        this.Set_Health(100);
        this.Set_CanTake_Damaged(true);

        // Collision — asteroids can collide, take collision damage, and deal damage on impact.
        this.Set_Can_Collide(true);
        this.Set_CanTake_CollideDamage(true);
        this.Set_CollisionDamage(100);
    }

}