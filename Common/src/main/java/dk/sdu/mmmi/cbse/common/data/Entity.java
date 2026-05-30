package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Entity defines an entity, by defining the position, form and size.
 */
public class Entity implements Serializable
{

    // .
    private final UUID ID = UUID.randomUUID();


    // .
    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;
    private float radius;


    // .
    private boolean canCollide = false;
    private boolean canDamage = false;
    private boolean canBeDamaged = false;

    // .
    private int health = 10;
    private int collisionDamage = 0;






    ////////////////////////////////////////////////////////////
    ////////////////////    Identity Methods    ////////////////////
    ///


    // .
    public String getID() {
        return ID.toString();
    }







    ////////////////////////////////////////////////////////////
    ////////////////////    Form Methods    ////////////////////
    ///


    /**
     *
     * @param coordinates
     */
    public void setPolygonCoordinates(double... coordinates )
    {
        this.polygonCoordinates = coordinates;
    }


    /**
     *
     * @return
     */
    public double[] getPolygonCoordinates()
    {
        return polygonCoordinates;
    }


    /**
     *
     * @param radius
     */
    public void setRadius(float radius)
    {
        this.radius = radius;
    }


    /**
     *
     * @return
     */
    public float getRadius()
    {
        return this.radius;
    }








    ////////////////////////////////////////////////////////////////
    ////////////////////    Position Methods    ////////////////////
    ///


    /**
     *
     * @param x
     */
    public void setX(double x)
    {
        this.x =x;
    }


    /**
     *
     * @return
     */
    public double getX()
    {
        return x;
    }


    /**
     *
     * @param y
     */
    public void setY(double y)
    {
        this.y = y;
    }


    /**
     *
     * @return
     */
    public double getY()
    {
        return y;
    }


    /**
     *
     * @param rotation
     */
    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }


    /**
     *
     * @return
     */
    public double getRotation()
    {
        return rotation;
    }










    /////////////////////////////////////////////////////////////////
    ////////////////////    Collision Methods    ////////////////////
    ///


    /**
     *
     * @return
     */
    public boolean isCanCollide()
    {
        return canCollide;
    }


    /**
     *
     * @return
     */
    public boolean isCanDamage()
    {
        return canDamage;
    }


    /**
     *
     * @return
     */
    public boolean isCanBeDamaged()
    {
        return canBeDamaged;
    }


    /**
     *
     * @param canCollide
     */
    public void setCanCollide(boolean canCollide)
    {
        this.canCollide = canCollide;
    }


    /**
     *
     * @param canDamage
     */
    public void setCanDamage(boolean canDamage)
    {
        this.canDamage = canDamage;
    }


    /**
     *
     * @param canBeDamaged
     */
    public void setCanBeDamaged(boolean canBeDamaged)
    {
        this.canBeDamaged = canBeDamaged;
    }







    ///////////////////////////////////////////////////////////////////////
    ////////////////////    Health & Damage Methods    ////////////////////
    ///


    /**
     *
     * @return
     */
    public int getHealth()
    {
        return health;
    }


    /**
     *
     * @param health
     */
    public void setHealth(int health)
    {
        this.health = health;
    }


    /**
     *
     * @return
     */
    public int getCollisionDamage()
    {
        return collisionDamage;
    }


    /**
     *
     * @param collisionDamage
     */
    public void setCollisionDamage(int collisionDamage)
    {
        this.collisionDamage = collisionDamage;
    }




}
