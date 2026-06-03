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

        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
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

}
