package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public class CollisionDetector implements IPostEntityProcessingService {

    public CollisionDetector() {
    }

    @Override
    public void process(GameData gameData, World world)
    {

        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {

            // .
            for (Entity entity2 : world.getEntities())
            {

                // if the two entities are identical, skip the iteration
                if (entity1.getID().equals(entity2.getID()))
                {
                    continue;                    
                }

                // CollisionDetection
                if (this.collides(entity1, entity2))
                {
                    // Tries to resolve a potential collision.
                    this.resolve_collision(entity1, entity2);
                }
            }
        }

    }


    // .
    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }


    // .
    private boolean resolve_collision(Entity entityA, Entity entityB)
    {

        // Check if both Entity can collide.
        if ((!entityA.isCanCollide())  ||  (!entityB.isCanCollide()))
        {
            return false;
        }

        // Check if either Entity can take damage.
        if ((!entityA.isCanBeDamaged())  &&  (!entityB.isCanBeDamaged()))
        {
            return false;
        }


        // Deal damage to Entity B, based on Entity A
        if (entityA.isCanDamage() && entityB.isCanBeDamaged())
        {
            entityB.doDamage(entityA.getCollisionDamage());
        }

        // return true.
        return true;
    }


}
