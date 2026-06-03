package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;




/**
 * Entity defines an entity, by defining the position, form and size.
 */
public class Entity implements Serializable
{

    // .
    private final UUID entity_ID = UUID.randomUUID();
    private EntityType entity_Type;

    // .
    private double[] entity_polygonCoordinates;
    private double entity_Position_X;
    private double entity_Position_Y;
    private double entity_Rotation;
    private float entity_Radius;

    // .
    private boolean can_Move;
    private boolean canBe_Pushed;

    // .
    private boolean can_Collide;
    private boolean canTake_Damaged;
    private boolean canTake_CollideDamage;

    // .
    private int entity_Health;
    private int entity_CollisionDamage;





    public Entity ()
    {
        // Identity.
        this.entity_Type = EntityType.None;

        // Move.
        this.can_Move = false;
        this.canBe_Pushed = false;

        // Health.
        this.entity_Health = 0;
        this.canTake_Damaged = false;

        // Collision.
        this.can_Collide = false;
        this.canTake_CollideDamage = false;
        this.entity_CollisionDamage = 0;

    }






    ////////////////////////////////////////////////////////////////
    ////////////////////    Identity Methods    ////////////////////
    ///


    /**
     *
     * @return
     */
    public String Get_ID() {
        return this.entity_ID.toString();
    }


    /**
     *
     * @return
     */
    public EntityType Get_Type()
    {
        return this.entity_Type;
    }


    /**
     *
     * @param type
     */
    public void Set_Type(EntityType type)
    {
        this.entity_Type = type;
    }







    ////////////////////////////////////////////////////////////
    ////////////////////    Form Methods    ////////////////////
    ///


    /**
     *
     * @param coordinates
     */
    public void Set_PolygonCoordinates(double... coordinates )
    {
        this.entity_polygonCoordinates = coordinates;
    }


    /**
     *
     * @return
     */
    public double[] Get_PolygonCoordinates()
    {
        return this.entity_polygonCoordinates;
    }


    /**
     *
     * @param radius
     */
    public void Set_Radius(float radius)
    {
        this.entity_Radius = radius;
    }


    /**
     *
     * @return
     */
    public float Get_Radius()
    {
        return this.entity_Radius;
    }








    ////////////////////////////////////////////////////////////////
    ////////////////////    Position Methods    ////////////////////
    ///


    /**
     *
     * @param x
     */
    public void Set_X(double x)
    {
        this.entity_Position_X =x;
    }


    /**
     *
     * @return
     */
    public double Get_X()
    {
        return this.entity_Position_X;
    }


    /**
     *
     * @param y
     */
    public void Set_Y(double y)
    {
        this.entity_Position_Y = y;
    }


    /**
     *
     * @return
     */
    public double Get_Y()
    {
        return this.entity_Position_Y;
    }


    /**
     *
     * @param rotation
     */
    public void Set_Rotation(double rotation)
    {
        this.entity_Rotation = rotation;
    }


    /**
     *
     * @return
     */
    public double Get_Rotation()
    {
        return this.entity_Rotation;
    }









    ////////////////////////////////////////////////////////////
    ////////////////////    Push Methods    ////////////////////
    ///


    /**
     *
     * @param state
     * @return
     */
    public void Set_Can_Move(boolean state)
    {
        this.can_Move = state;
    }


    /**
     *
     * @param state
     * @return
     */
    public void Set_CanBe_Pushed(boolean state)
    {
        this.canBe_Pushed = state;
    }


    /**
     *
     * @return
     */
    public boolean Check_Can_Move()
    {
        return this.can_Move;
    }


    /**
     *
     * @return
     */
    public boolean Check_CanBe_Pushed()
    {
        return this.canBe_Pushed;
    }


    /**
     *
     * @param x_push
     * @param y_push
     * @return
     */
    public void Push_Entity(int x_push, int y_push)
    {
        this.entity_Position_X = this.entity_Position_X + x_push;
        this.entity_Position_Y = this.entity_Position_Y + y_push;
    }














    /////////////////////////////////////////////////////////////////
    ////////////////////    Collision Methods    ////////////////////
    ///


    /**
     *
     * @return
     */
    public boolean Check_Can_Collide()
    {
        return this.can_Collide;
    }


    /**
     *
     * @return
     */
    public boolean Check_CanTake_CollideDamage()
    {
        return this.canTake_CollideDamage;
    }


    /**
     *
     * @param canCollide
     */
    public void Set_Can_Collide(boolean canCollide)
    {
        this.can_Collide = canCollide;
    }


    /**
     *
     * @param canCollideDamage
     */
    public void Set_CanTake_CollideDamage(boolean canCollideDamage)
    {
        this.canTake_CollideDamage = canCollideDamage;
    }









    //////////////////////////////////////////////////////////////
    ////////////////////    Health Methods    ////////////////////
    ///


    /**
     *
     * @return
     */
    public int Get_Health()
    {
        return this.entity_Health;
    }


    /**
     *
     * @param health
     */
    public void Set_Health(int health)
    {
        this.entity_Health = health;
    }










    //////////////////////////////////////////////////////////////
    ////////////////////    Damage Methods    ////////////////////
    ///


    /**
     *
     * @return
     */
    public boolean Check_CanTake_Damaged()
    {
        return this.canTake_Damaged;
    }


    /**
     *
     * @param canBeDamaged
     */
    public void Set_CanTake_Damaged(boolean canBeDamaged)
    {
        this.canTake_Damaged = canBeDamaged;
    }


    /**
     *
     * @return
     */
    public int Get_CollisionDamage()
    {
        return this.entity_CollisionDamage;
    }


    /**
     *
     * @param collisionDamage
     */
    public void Set_CollisionDamage(int collisionDamage)
    {
        this.entity_CollisionDamage = collisionDamage;
    }


    /**
     *
     * @param damage
     */
    public void Take_Damage(int damage)
    {
        this.entity_Health = this.entity_Health - damage;
    }


    /**
     *
     * @param heal
     */
    public void Heal_Damage(int heal)
    {
        this.entity_Health = this.entity_Health + heal;
    }










}
