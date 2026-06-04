package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;




/**
 * Entity is the base class for all game entities.
 * Defines the identity, position, form, movement, collision, health and damage properties of an entity.
 * All specific entity types (Player, Enemy, Asteroid, Bullet, etc.) extend this class.
 */
public class Entity implements Serializable
{

    // Unique identifier and type of the entity.
    private final UUID entity_ID = UUID.randomUUID();
    private EntityType entity_Type;

    // Form and position of the entity in the game world.
    private double[] entity_polygonCoordinates;
    private double entity_Position_X;
    private double entity_Position_Y;
    private double entity_Rotation;
    private float entity_Radius;

    // Movement and push flags — determines if the entity can move or be pushed.
    private boolean can_Move;
    private boolean canBe_Pushed;

    // Collision flags — determines if the entity can collide and take collision damage.
    private boolean can_Collide;
    private boolean canTake_Damaged;
    private boolean canTake_CollideDamage;

    // Health and damage values — determines how much health the entity has and how much damage it deals.
    private int entity_Health;
    private int entity_CollisionDamage;




    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    /**
     * Default constructor for Entity.
     * Initialises all flags to false and all values to zero.
     * Entity type defaults to EntityType.None — must be set by the subclass constructor.
     */
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
     * Returns the unique ID of the entity as a String.
     * @return the entity's UUID as a String.
     */
    public String Get_ID() {
        return this.entity_ID.toString();
    }


    /**
     * Returns the EntityType of the entity.
     * @return the entity's EntityType.
     */
    public EntityType Get_Type()
    {
        return this.entity_Type;
    }


    /**
     * Sets the EntityType of the entity.
     * @param type the EntityType to assign to this entity.
     */
    public void Set_Type(EntityType type)
    {
        this.entity_Type = type;
    }




    ////////////////////////////////////////////////////////////
    ////////////////////    Form Methods    ////////////////////
    ///


    /**
     * Sets the polygon coordinates that define the visual shape of the entity.
     * @param coordinates the polygon coordinates as a varargs array of doubles.
     */
    public void Set_PolygonCoordinates(double... coordinates)
    {
        this.entity_polygonCoordinates = coordinates;
    }


    /**
     * Returns the polygon coordinates that define the visual shape of the entity.
     * @return the polygon coordinates as a double array.
     */
    public double[] Get_PolygonCoordinates()
    {
        return this.entity_polygonCoordinates;
    }


    /**
     * Sets the collision radius of the entity.
     * @param radius the radius to assign to this entity.
     */
    public void Set_Radius(float radius)
    {
        this.entity_Radius = radius;
    }


    /**
     * Returns the collision radius of the entity.
     * @return the entity's radius.
     */
    public float Get_Radius()
    {
        return this.entity_Radius;
    }




    ////////////////////////////////////////////////////////////////
    ////////////////////    Position Methods    ////////////////////
    ///


    /**
     * Sets the X position of the entity in the game world.
     * @param x the X coordinate to assign to this entity.
     */
    public void Set_X(double x)
    {
        this.entity_Position_X = x;
    }


    /**
     * Returns the X position of the entity in the game world.
     * @return the entity's X coordinate.
     */
    public double Get_X()
    {
        return this.entity_Position_X;
    }


    /**
     * Sets the Y position of the entity in the game world.
     * @param y the Y coordinate to assign to this entity.
     */
    public void Set_Y(double y)
    {
        this.entity_Position_Y = y;
    }


    /**
     * Returns the Y position of the entity in the game world.
     * @return the entity's Y coordinate.
     */
    public double Get_Y()
    {
        return this.entity_Position_Y;
    }


    /**
     * Sets the rotation of the entity in degrees.
     * @param rotation the rotation in degrees to assign to this entity.
     */
    public void Set_Rotation(double rotation)
    {
        this.entity_Rotation = rotation;
    }


    /**
     * Returns the rotation of the entity in degrees.
     * @return the entity's rotation in degrees.
     */
    public double Get_Rotation()
    {
        return this.entity_Rotation;
    }




    ////////////////////////////////////////////////////////////
    ////////////////////    Push Methods    ////////////////////
    ///


    /**
     * Sets whether the entity is allowed to move.
     * @param state true if the entity can move, false otherwise.
     */
    public void Set_Can_Move(boolean state)
    {
        this.can_Move = state;
    }


    /**
     * Sets whether the entity can be pushed by collision resolution.
     * @param state true if the entity can be pushed, false otherwise.
     */
    public void Set_CanBe_Pushed(boolean state)
    {
        this.canBe_Pushed = state;
    }


    /**
     * Returns whether the entity is allowed to move.
     * @return true if the entity can move, false otherwise.
     */
    public boolean Check_Can_Move()
    {
        return this.can_Move;
    }


    /**
     * Returns whether the entity can be pushed by collision resolution.
     * @return true if the entity can be pushed, false otherwise.
     */
    public boolean Check_CanBe_Pushed()
    {
        return this.canBe_Pushed;
    }


    /**
     * Pushes the entity by the given X and Y values.
     * Used by the collision system to separate overlapping entities.
     * @param x_push the amount to push the entity along the X axis.
     * @param y_push the amount to push the entity along the Y axis.
     */
    public void Push_Entity(double x_push, double y_push)
    {
        this.entity_Position_X = this.entity_Position_X + x_push;
        this.entity_Position_Y = this.entity_Position_Y + y_push;
    }




    /////////////////////////////////////////////////////////////////
    ////////////////////    Collision Methods    ////////////////////
    ///


    /**
     * Returns whether the entity is allowed to participate in collision detection.
     * @return true if the entity can collide, false otherwise.
     */
    public boolean Check_Can_Collide()
    {
        return this.can_Collide;
    }


    /**
     * Returns whether the entity takes damage when it collides with another entity.
     * @return true if the entity takes collision damage, false otherwise.
     */
    public boolean Check_CanTake_CollideDamage()
    {
        return this.canTake_CollideDamage;
    }


    /**
     * Sets whether the entity is allowed to participate in collision detection.
     * @param canCollide true if the entity can collide, false otherwise.
     */
    public void Set_Can_Collide(boolean canCollide)
    {
        this.can_Collide = canCollide;
    }


    /**
     * Sets whether the entity takes damage when it collides with another entity.
     * @param canCollideDamage true if the entity takes collision damage, false otherwise.
     */
    public void Set_CanTake_CollideDamage(boolean canCollideDamage)
    {
        this.canTake_CollideDamage = canCollideDamage;
    }




    //////////////////////////////////////////////////////////////
    ////////////////////    Health Methods    ////////////////////
    ///


    /**
     * Returns the current health of the entity.
     * @return the entity's current health value.
     */
    public int Get_Health()
    {
        return this.entity_Health;
    }


    /**
     * Sets the current health of the entity.
     * @param health the health value to assign to this entity.
     */
    public void Set_Health(int health)
    {
        this.entity_Health = health;
    }




    //////////////////////////////////////////////////////////////
    ////////////////////    Damage Methods    ////////////////////
    ///


    /**
     * Returns whether the entity can take damage.
     * @return true if the entity can be damaged, false otherwise.
     */
    public boolean Check_CanTake_Damaged()
    {
        return this.canTake_Damaged;
    }


    /**
     * Sets whether the entity can take damage.
     * @param canBeDamaged true if the entity can be damaged, false otherwise.
     */
    public void Set_CanTake_Damaged(boolean canBeDamaged)
    {
        this.canTake_Damaged = canBeDamaged;
    }


    /**
     * Returns the amount of damage this entity deals on collision.
     * @return the entity's collision damage value.
     */
    public int Get_CollisionDamage()
    {
        return this.entity_CollisionDamage;
    }


    /**
     * Sets the amount of damage this entity deals on collision.
     * @param collisionDamage the collision damage value to assign to this entity.
     */
    public void Set_CollisionDamage(int collisionDamage)
    {
        this.entity_CollisionDamage = collisionDamage;
    }


    /**
     * Reduces the entity's health by the given damage amount.
     * @param damage the amount of damage to apply to this entity.
     */
    public void Take_Damage(int damage)
    {
        this.entity_Health = this.entity_Health - damage;
    }


    /**
     * Increases the entity's health by the given heal amount.
     * Used for PowerUp interactions such as health packs.
     * @param heal the amount of health to restore to this entity.
     */
    public void Heal_Damage(int heal)
    {
        this.entity_Health = this.entity_Health + heal;
    }


}