package dk.sdu.se4.groupX.enemyship;

import dk.sdu.se4.groupX.commonenemy.Enemy;

/**
 *
 */
public class EnemyShip extends Enemy
{

    private static final double DEFAULT_DESIRED_ROTATION = 0.0;
    private static final double DEFAULT_TURN_SPEED_MAX = 3.0;
    private static final double DEFAULT_TURN_FACTOR = 0.1;
    private static final int DEFAULT_RELOAD_TIME = 60;
    private static final int DEFAULT_RELOAD_TICKS_LEFT = 0;
    private static final boolean DEFAULT_BULLET_LOADED = true;

    public double desired_rotation;
    public double ship_turnSpeed_max;
    public double ship_turnFactor;
    public int ship_Reload_time;
    public int ship_Reload_ticksLeft;
    public boolean ship_Bullet_loaded;



    public EnemyShip()
    {
        super();
        this.desired_rotation = DEFAULT_DESIRED_ROTATION;
        this.ship_turnSpeed_max = DEFAULT_TURN_SPEED_MAX;
        this.ship_turnFactor = DEFAULT_TURN_FACTOR;
        this.ship_Reload_time = DEFAULT_RELOAD_TIME;
        this.ship_Reload_ticksLeft = DEFAULT_RELOAD_TICKS_LEFT;
        this.ship_Bullet_loaded = DEFAULT_BULLET_LOADED;
    }


    public EnemyShip(double desired_rotation)
    {
        super();
        this.desired_rotation = desired_rotation;
        this.ship_turnSpeed_max = DEFAULT_TURN_SPEED_MAX;
        this.ship_turnFactor = DEFAULT_TURN_FACTOR;
        this.ship_Reload_time = DEFAULT_RELOAD_TIME;
        this.ship_Reload_ticksLeft = DEFAULT_RELOAD_TICKS_LEFT;
        this.ship_Bullet_loaded = DEFAULT_BULLET_LOADED;
    }


    public EnemyShip(double desired_rotation, double ship_turnSpeed_max, double ship_turnFactor)
    {
        super();
        this.desired_rotation = desired_rotation;
        this.ship_turnSpeed_max = ship_turnSpeed_max;
        this.ship_turnFactor = ship_turnFactor;
        this.ship_Reload_time = DEFAULT_RELOAD_TIME;
        this.ship_Reload_ticksLeft = DEFAULT_RELOAD_TICKS_LEFT;
        this.ship_Bullet_loaded = DEFAULT_BULLET_LOADED;
    }


    public EnemyShip(double desired_rotation, double ship_turnSpeed_max, double ship_turnFactor, boolean ship_Bullet_loaded, int ship_Reload_time, int ship_Reload_ticksLeft)
    {
        super();
        this.desired_rotation = desired_rotation;
        this.ship_turnSpeed_max = ship_turnSpeed_max;
        this.ship_turnFactor = ship_turnFactor;
        this.ship_Reload_time = ship_Reload_time;
        this.ship_Reload_ticksLeft = ship_Reload_ticksLeft;
        this.ship_Bullet_loaded = ship_Bullet_loaded;
    }




}
