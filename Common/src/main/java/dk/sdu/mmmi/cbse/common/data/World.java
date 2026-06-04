package dk.sdu.mmmi.cbse.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



/**
 * World contains and updates the Game-world, and all the entities inside it.
 *
 * @author jcs
 */
public class World
{

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    private int currentScore;

    private int currentEnemyCount;
    private int max_Enemies;

    private int currentAsteroidCount;
    private int max_Asteroids;


    public static final int MAX_ENEMIES_DEFAULT = 3;
    public static final int MAX_ASTEROIDS_DEFAULT = 10;




    public World ()
    {

        this.currentScore = 0;

        this.currentEnemyCount = 0;
        this.max_Enemies = MAX_ENEMIES_DEFAULT;

        this.currentAsteroidCount = 0;
        this.max_Asteroids = MAX_ASTEROIDS_DEFAULT;

    }




    public String addEntity(Entity entity)
    {
        entityMap.put(entity.Get_ID(), entity);
        return entity.Get_ID();
    }

    public void removeEntity(String entityID)
    {
        entityMap.remove(entityID);
    }

    public void removeEntity(Entity entity)
    {
        entityMap.remove(entity.Get_ID());
    }

    public Collection<Entity> getEntities()
    {
        return entityMap.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes)
    {
        List<Entity> r = new ArrayList<>();

        for (Entity e : getEntities())
        {
            for (Class<E> entityType : entityTypes)
            {
                if (entityType.equals(e.getClass()))
                {
                    r.add(e);
                }
            }
        }

        return r;
    }

    public Entity getEntity(String ID)
    {
        return entityMap.get(ID);
    }

    public boolean containsEntity(String ID)
    {
        return entityMap.containsKey(ID);
    }


    /**
     * Sets the current score.
     *
     * @param score the current score
     */
    public void Set_CurrentScore(int score)
    {
        this.currentScore = score;
    }

    /**
     * Returns the current score.
     * @return current score
     */
    public int Get_CurrentScore()
    {
        return currentScore;
    }


    /**
     *
     * @param points
     */
    public void AddTo_CurrentScore(int points)
    {
        this.currentScore += points;
    }








    /**
     * .
     * @return
     */
    public void Set_CurrentEnemyCount(int amount)
    {
        this.currentEnemyCount = amount;
    }


    /**
     * Returns the current number of enemies in the world.
     * @return current enemy count
     */
    public int Get_CurrentEnemyCount()
    {
        return currentEnemyCount;
    }


    /**
     * Adds to the current enemy count.
     * @param amount amount to add (use negative to decrement)
     */
    public void AddTo_CurrentEnemyCount(int amount)
    {
        this.currentEnemyCount += amount;
    }


    /**
     * Returns the maximum number of enemies allowed in the world.
     * @return max enemies
     */
    public int Get_MaxEnemies()
    {
        return max_Enemies;
    }


    /**
     * Sets the maximum number of enemies allowed in the world.
     * @param max max enemies
     */
    public void Set_MaxEnemies(int max)
    {
        this.max_Enemies = max;
    }






    /**
     * .
     * @return
     */
    public void Set_CurrentAsteroidCount(int amount)
    {
        this.currentAsteroidCount = amount;
    }


    /**
     * Returns the current number of asteroids in the world.
     * @return current asteroid count
     */
    public int Get_CurrentAsteroidCount()
    {
        return currentAsteroidCount;
    }


    /**
     * Adds to the current asteroid count.
     * @param amount amount to add (use negative to decrement)
     */
    public void AddTo_CurrentAsteroidCount(int amount)
    {
        this.currentAsteroidCount += amount;
    }


    /**
     * Returns the maximum number of asteroids allowed in the world.
     * @return max asteroids
     */
    public int Get_MaxAsteroids()
    {
        return max_Asteroids;
    }


    /**
     * Sets the maximum number of asteroids allowed in the world.
     * @param max max asteroids
     */
    public void Set_MaxAsteroids(int max)
    {
        this.max_Asteroids = max;
    }









}
