package dk.sdu.mmmi.cbse.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



/**
 * World contains and updates the Game-world, and all the entities inside it.
 * Acts as the shared data bus between all plugins and processors,
 * providing entity management, score tracking, and spawn count management.
 * Passed to all processors and plugins every frame alongside GameData.
 *
 * @author jcs
 */
public class World
{

    // The map of all entities currently in the game world, keyed by their unique ID.
    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    // The current score accumulated during this game session.
    private int currentScore;

    // The current and maximum number of enemy entities allowed in the world.
    private int currentEnemyCount;
    private int max_Enemies;

    // The current and maximum number of asteroid entities allowed in the world.
    private int currentAsteroidCount;
    private int max_Asteroids;

    // Default maximum values for enemies and asteroids - used on startup and restart.
    public static final int MAX_ENEMIES_DEFAULT = 3;
    public static final int MAX_ASTEROIDS_DEFAULT = 10;




    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    /**
     * Default constructor for World.
     * Initialises all counts to zero and sets the maximum spawn limits to their default values.
     */
    public World ()
    {
        // Initialise score to zero.
        this.currentScore = 0;

        // Initialise enemy count and maximum.
        this.currentEnemyCount = 0;
        this.max_Enemies = MAX_ENEMIES_DEFAULT;

        // Initialise asteroid count and maximum.
        this.currentAsteroidCount = 0;
        this.max_Asteroids = MAX_ASTEROIDS_DEFAULT;
    }




    ////////////////////////////////////////////////////////////////
    ////////////////////    Entity Methods    ////////////////////
    ///


    /**
     * Adds an entity to the game world.
     * @param entity the entity to add.
     * @return the unique ID of the added entity.
     */
    public String addEntity(Entity entity)
    {
        entityMap.put(entity.Get_ID(), entity);
        return entity.Get_ID();
    }


    /**
     * Removes an entity from the game world by its unique ID.
     * @param entityID the unique ID of the entity to remove.
     */
    public void removeEntity(String entityID)
    {
        entityMap.remove(entityID);
    }


    /**
     * Removes an entity from the game world by its entity reference.
     * @param entity the entity to remove.
     */
    public void removeEntity(Entity entity)
    {
        entityMap.remove(entity.Get_ID());
    }


    /**
     * Returns all entities currently in the game world.
     * @return a collection of all entities in the world.
     */
    public Collection<Entity> getEntities()
    {
        return entityMap.values();
    }


    /**
     * Returns all entities of the given type(s) currently in the game world.
     * @param entityTypes the entity class type(s) to filter by.
     * @return a list of all entities matching the given type(s).
     * @param <E> the entity type to filter by.
     */
    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes)
    {
        // Initialise the result list.
        List<Entity> r = new ArrayList<>();

        // Iterate over all entities in the world.
        for (Entity e : getEntities())
        {
            // Check if the entity matches any of the given types.
            for (Class<E> entityType : entityTypes)
            {
                // If the entity matches, add it to the result list.
                if (entityType.equals(e.getClass()))
                {
                    r.add(e);
                }
            }
        }

        return r;
    }


    /**
     * Returns the entity with the given unique ID, or null if not found.
     * @param ID the unique ID of the entity to retrieve.
     * @return the entity with the given ID, or null if not found.
     */
    public Entity getEntity(String ID)
    {
        return entityMap.get(ID);
    }


    /**
     * Returns whether an entity with the given unique ID exists in the world.
     * @param ID the unique ID to check.
     * @return true if the entity exists in the world, false otherwise.
     */
    public boolean containsEntity(String ID)
    {
        return entityMap.containsKey(ID);
    }







    ////////////////////////////////////////////////////////////////
    ////////////////////    Score Methods    ////////////////////
    ///


    /**
     * Sets the current score to the given value.
     * @param score the score value to set.
     */
    public void Set_CurrentScore(int score)
    {
        this.currentScore = score;
    }


    /**
     * Returns the current accumulated score.
     * @return the current score.
     */
    public int Get_CurrentScore()
    {
        return this.currentScore;
    }


    /**
     * Adds the given points to the current score.
     * Called by processors when an entity is destroyed.
     * @param points the points to add to the current score.
     */
    public void AddTo_CurrentScore(int points)
    {
        this.currentScore += points;
    }




    ////////////////////////////////////////////////////////////////
    ////////////////////    Enemy Count Methods    ////////////////////
    ///


    /**
     * Sets the current number of enemy entities in the world.
     * @param amount the enemy count to set.
     */
    public void Set_CurrentEnemyCount(int amount)
    {
        this.currentEnemyCount = amount;
    }


    /**
     * Returns the current number of enemy entities in the world.
     * @return the current enemy count.
     */
    public int Get_CurrentEnemyCount()
    {
        return this.currentEnemyCount;
    }


    /**
     * Adds the given amount to the current enemy count.
     * Use a negative value to decrement the count when an enemy is destroyed.
     * @param amount the amount to add to the current enemy count.
     */
    public void AddTo_CurrentEnemyCount(int amount)
    {
        this.currentEnemyCount += amount;
    }


    /**
     * Returns the maximum number of enemy entities allowed in the world at any one time.
     * @return the maximum enemy count.
     */
    public int Get_MaxEnemies()
    {
        return this.max_Enemies;
    }


    /**
     * Sets the maximum number of enemy entities allowed in the world at any one time.
     * @param max the maximum enemy count to set.
     */
    public void Set_MaxEnemies(int max)
    {
        this.max_Enemies = max;
    }




    ////////////////////////////////////////////////////////////////
    ////////////////////    Asteroid Count Methods    ////////////////////
    ///


    /**
     * Sets the current number of asteroid entities in the world.
     * @param amount the asteroid count to set.
     */
    public void Set_CurrentAsteroidCount(int amount)
    {
        this.currentAsteroidCount = amount;
    }


    /**
     * Returns the current number of asteroid entities in the world.
     * @return the current asteroid count.
     */
    public int Get_CurrentAsteroidCount()
    {
        return this.currentAsteroidCount;
    }


    /**
     * Adds the given amount to the current asteroid count.
     * Use a negative value to decrement the count when an asteroid is destroyed.
     * @param amount the amount to add to the current asteroid count.
     */
    public void AddTo_CurrentAsteroidCount(int amount)
    {
        this.currentAsteroidCount += amount;
    }


    /**
     * Returns the maximum number of asteroid entities allowed in the world at any one time.
     * @return the maximum asteroid count.
     */
    public int Get_MaxAsteroids()
    {
        return this.max_Asteroids;
    }


    /**
     * Sets the maximum number of asteroid entities allowed in the world at any one time.
     * @param max the maximum asteroid count to set.
     */
    public void Set_MaxAsteroids(int max)
    {
        this.max_Asteroids = max;
    }


}
