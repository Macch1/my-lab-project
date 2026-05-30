package dk.sdu.se4.groupX.commonenemy;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author corfixen
 */
public class Enemy extends Entity
{



    /////////////////////////////////////////////////////////////////
    ////////////////////    Default Parameter    ////////////////////
    ///

    // Just to make it simple and easy to change the default without having to search.
    private static final double DEFAULT_SPEED_FACTOR = 0.3;
    private static final double DEFAULT_DESIRED_ROTATION = 0.0;
    private static final double DEFAULT_TURN_SPEED_MIN = 1.0;
    private static final double DEFAULT_TURN_SPEED_MAX = 2.0;
    private static final double DEFAULT_TURN_FACTOR = 0.01;
    private static final int DEFAULT_RELOAD_TIME = 120;
    private static final int DEFAULT_RELOAD_TICKS_LEFT = 0;
    private static final boolean DEFAULT_BULLET_LOADED = true;






    ////////////////////////////////////////////////////////////////
    ////////////////////    Enemy Attributes    ////////////////////
    ///

    private double enemy_speedFactor;
    private double desired_rotation;
    private double enemy_turnSpeed_min;
    private double enemy_turnSpeed_max;
    private double enemy_turnFactor;
    private int enemy_Reload_time;
    private int enemy_Reload_ticksLeft;
    private boolean enemy_Bullet_loaded;




    ////////////////////////////////////////////////////////////
    ////////////////////    Constructors    ////////////////////
    ///

    public Enemy()
    {
        super();
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



    public double getEnemy_speedFactor() {
        return this.enemy_speedFactor;
    }

    public double getDesired_rotation() {
        return this.desired_rotation;
    }

    public double getEnemy_turnSpeed_min() {
        return this.enemy_turnSpeed_min;
    }

    public double getEnemy_turnSpeed_max() {
        return this.enemy_turnSpeed_max;
    }

    public double getEnemy_turnFactor() {
        return this.enemy_turnFactor;
    }

    public int getEnemy_Reload_time() {
        return this.enemy_Reload_time;
    }

    public int getEnemy_Reload_ticksLeft() {
        return this.enemy_Reload_ticksLeft;
    }

    public boolean isEnemy_Bullet_loaded() {
        return this.enemy_Bullet_loaded;
    }


    ///////////////////////////////////////////////////////////
    ////////////////////    Setters    ////////////////////
    ///


    public void setEnemy_speedFactor(double enemy_speedFactor) {
        this.enemy_speedFactor = enemy_speedFactor;
    }

    public void setDesired_rotation(double desired_rotation) {
        this.desired_rotation = desired_rotation;
    }

    public void setEnemy_turnSpeed_min(double enemy_turnSpeed_min) {
        this.enemy_turnSpeed_min = enemy_turnSpeed_min;
    }

    public void setEnemy_turnSpeed_max(double enemy_turnSpeed_max) {
        this.enemy_turnSpeed_max = enemy_turnSpeed_max;
    }

    public void setEnemy_turnFactor(double enemy_turnFactor) {
        this.enemy_turnFactor = enemy_turnFactor;
    }

    public void setEnemy_Reload_time(int enemy_Reload_time) {
        this.enemy_Reload_time = enemy_Reload_time;
    }

    public void setEnemy_Reload_ticksLeft(int enemy_Reload_ticksLeft) {
        this.enemy_Reload_ticksLeft = enemy_Reload_ticksLeft;
    }

    public void setEnemy_Bullet_loaded(boolean enemy_Bullet_loaded) {
        this.enemy_Bullet_loaded = enemy_Bullet_loaded;
    }





    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///










}
