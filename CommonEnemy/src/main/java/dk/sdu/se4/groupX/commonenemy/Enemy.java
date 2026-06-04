package dk.sdu.se4.groupX.commonenemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;


/**
 * Enemy is the base entity type for all enemy entities in the game.
 * Extends Entity and sets the default configuration for enemy behaviour -
 * enemies can move, can be pushed by the collision system, can collide,
 * can take collision damage, and deal damage on impact.
 *
 * Enemies have additional attributes for movement speed, turning behaviour,
 * and bullet reload management - used by EnemyShipProcessor each frame.
 *
 * The visual shape, position and rotation of the enemy are set by
 * EnemyCreationHelper at creation time.
 *
 * @author corfixen
 */
public class Enemy extends Entity
{


    /////////////////////////////////////////////////////////////////
    ////////////////////    Default Parameter    ////////////////////
    ///


    // The default movement speed factor of the enemy ship.
    private static final double DEFAULT_SPEED_FACTOR = 0.3;

    // The default desired rotation of the enemy ship - updated each frame to face the player.
    private static final double DEFAULT_DESIRED_ROTATION = 0.0;

    // The minimum angular difference in degrees before the enemy is considered aimed at the player.
    private static final double DEFAULT_TURN_SPEED_MIN = 1.0;

    // The maximum number of degrees the enemy can turn per frame.
    private static final double DEFAULT_TURN_SPEED_MAX = 2.0;

    // The factor applied to the angular difference when calculating the turn amount per frame.
    private static final double DEFAULT_TURN_FACTOR = 0.01;

    // The number of frames the enemy must wait between shots.
    private static final int DEFAULT_RELOAD_TIME = 120;

    // The number of frames remaining before the enemy can fire again.
    private static final int DEFAULT_RELOAD_TICKS_LEFT = 0;

    // Whether the enemy has a bullet loaded and ready to fire.
    private static final boolean DEFAULT_BULLET_LOADED = true;




    ////////////////////////////////////////////////////////////////
    ////////////////////    Enemy Attributes    ////////////////////
    ///


    // The current movement speed factor of the enemy ship.
    private double enemy_speedFactor;

    // The current desired rotation of the enemy ship - updated each frame to face the player.
    private double desired_rotation;

    // The minimum angular difference in degrees before the enemy is considered aimed at the player.
    private double enemy_turnSpeed_min;

    // The maximum number of degrees the enemy can turn per frame.
    private double enemy_turnSpeed_max;

    // The factor applied to the angular difference when calculating the turn amount per frame.
    private double enemy_turnFactor;

    // The number of frames the enemy must wait between shots.
    private int enemy_Reload_time;

    // The number of frames remaining before the enemy can fire again.
    private int enemy_Reload_ticksLeft;

    // Whether the enemy has a bullet loaded and ready to fire.
    private boolean enemy_Bullet_loaded;




    ////////////////////////////////////////////////////////////
    ////////////////////    Constructors    ////////////////////
    ///


    /**
     * Default constructor for Enemy.
     * Initialises all enemy-specific flags, values and combat attributes.
     * Sets the entity type to EntityType.Enemy.
     */
    public Enemy()
    {
        // Initialise the base Entity fields.
        super();

        // Set the entity type to Enemy.
        this.Set_Type(EntityType.Enemy);

        // Movement - enemies can move freely and can be pushed by collisions.
        this.Set_Can_Move(true);
        this.Set_CanBe_Pushed(true);

        // Health - enemies start with 50 health and can be destroyed.
        this.Set_Health(50);
        this.Set_CanTake_Damaged(true);

        // Collision - enemies can collide, take collision damage, and deal damage on impact.
        this.Set_Can_Collide(true);
        this.Set_CanTake_CollideDamage(true);
        this.Set_CollisionDamage(100);

        // Initialise enemy-specific combat attributes to their default values.
        this.enemy_speedFactor = DEFAULT_SPEED_FACTOR;
        this.desired_rotation = DEFAULT_DESIRED_ROTATION;
        this.enemy_turnSpeed_min = DEFAULT_TURN_SPEED_MIN;
        this.enemy_turnSpeed_max = DEFAULT_TURN_SPEED_MAX;
        this.enemy_turnFactor = DEFAULT_TURN_FACTOR;
        this.enemy_Reload_time = DEFAULT_RELOAD_TIME;
        this.enemy_Reload_ticksLeft = DEFAULT_RELOAD_TICKS_LEFT;
        this.enemy_Bullet_loaded = DEFAULT_BULLET_LOADED;
    }




    ///////////////////////////////////////////////////////////
    ////////////////////    Getters    ////////////////////
    ///


    /**
     * Returns the movement speed factor of the enemy ship.
     * @return the enemy's speed factor.
     */
    public double getEnemy_speedFactor()
    {
        return this.enemy_speedFactor;
    }


    /**
     * Returns the current desired rotation of the enemy ship.
     * Updated each frame by EnemyShipProcessor to face the player.
     * @return the enemy's desired rotation in degrees.
     */
    public double getDesired_rotation()
    {
        return this.desired_rotation;
    }


    /**
     * Returns the minimum angular difference before the enemy is considered aimed at the player.
     * @return the enemy's minimum turn speed in degrees.
     */
    public double getEnemy_turnSpeed_min()
    {
        return this.enemy_turnSpeed_min;
    }


    /**
     * Returns the maximum number of degrees the enemy can turn per frame.
     * @return the enemy's maximum turn speed in degrees per frame.
     */
    public double getEnemy_turnSpeed_max()
    {
        return this.enemy_turnSpeed_max;
    }


    /**
     * Returns the turn factor applied when calculating the turn amount per frame.
     * @return the enemy's turn factor.
     */
    public double getEnemy_turnFactor()
    {
        return this.enemy_turnFactor;
    }


    /**
     * Returns the number of frames the enemy must wait between shots.
     * @return the enemy's reload time in frames.
     */
    public int getEnemy_Reload_time()
    {
        return this.enemy_Reload_time;
    }


    /**
     * Returns the number of frames remaining before the enemy can fire again.
     * @return the enemy's remaining reload ticks.
     */
    public int getEnemy_Reload_ticksLeft()
    {
        return this.enemy_Reload_ticksLeft;
    }


    /**
     * Returns whether the enemy has a bullet loaded and ready to fire.
     * @return true if the enemy has a bullet loaded, false otherwise.
     */
    public boolean isEnemy_Bullet_loaded()
    {
        return this.enemy_Bullet_loaded;
    }




    ///////////////////////////////////////////////////////////
    ////////////////////    Setters    ////////////////////
    ///


    /**
     * Sets the movement speed factor of the enemy ship.
     * @param enemy_speedFactor the speed factor to assign to this enemy.
     */
    public void setEnemy_speedFactor(double enemy_speedFactor)
    {
        this.enemy_speedFactor = enemy_speedFactor;
    }


    /**
     * Sets the desired rotation of the enemy ship.
     * Called by EnemyShipProcessor each frame to update the enemy's target direction.
     * @param desired_rotation the desired rotation in degrees.
     */
    public void setDesired_rotation(double desired_rotation)
    {
        this.desired_rotation = desired_rotation;
    }


    /**
     * Sets the minimum angular difference before the enemy is considered aimed at the player.
     * @param enemy_turnSpeed_min the minimum turn speed in degrees.
     */
    public void setEnemy_turnSpeed_min(double enemy_turnSpeed_min)
    {
        this.enemy_turnSpeed_min = enemy_turnSpeed_min;
    }


    /**
     * Sets the maximum number of degrees the enemy can turn per frame.
     * @param enemy_turnSpeed_max the maximum turn speed in degrees per frame.
     */
    public void setEnemy_turnSpeed_max(double enemy_turnSpeed_max)
    {
        this.enemy_turnSpeed_max = enemy_turnSpeed_max;
    }


    /**
     * Sets the turn factor applied when calculating the turn amount per frame.
     * @param enemy_turnFactor the turn factor to assign to this enemy.
     */
    public void setEnemy_turnFactor(double enemy_turnFactor)
    {
        this.enemy_turnFactor = enemy_turnFactor;
    }


    /**
     * Sets the number of frames the enemy must wait between shots.
     * @param enemy_Reload_time the reload time in frames.
     */
    public void setEnemy_Reload_time(int enemy_Reload_time)
    {
        this.enemy_Reload_time = enemy_Reload_time;
    }


    /**
     * Sets the number of frames remaining before the enemy can fire again.
     * Called by EnemyShipProcessor each frame to count down the reload timer.
     * @param enemy_Reload_ticksLeft the remaining reload ticks.
     */
    public void setEnemy_Reload_ticksLeft(int enemy_Reload_ticksLeft)
    {
        this.enemy_Reload_ticksLeft = enemy_Reload_ticksLeft;
    }


    /**
     * Sets whether the enemy has a bullet loaded and ready to fire.
     * @param enemy_Bullet_loaded true if the enemy has a bullet loaded, false otherwise.
     */
    public void setEnemy_Bullet_loaded(boolean enemy_Bullet_loaded)
    {
        this.enemy_Bullet_loaded = enemy_Bullet_loaded;
    }




    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    // Reserved for future use.


}