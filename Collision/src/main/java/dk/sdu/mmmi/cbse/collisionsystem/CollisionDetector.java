package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public class CollisionDetector implements IPostEntityProcessingService
{

    public CollisionDetector()
    {
    }


    @Override
    public void process(GameData gameData, World world)
    {

        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities())
        {

            // If either entity is of type "None" or "Background", skip the iteration.
            // An entity can't collide with nothing or the background.
            if (entity1.Get_Type() == EntityType.None || entity1.Get_Type() == EntityType.Background)
            {
                continue;
            }

            // .
            for (Entity entity2 : world.getEntities())
            {

                // If the two entities are identical, skip the iteration.
                // An entity can't collide with itself.
                if (entity1.Get_ID().equals(entity2.Get_ID()))
                {
                    continue;                    
                }

                // If either entity is of type "None" or "Background", skip the iteration.
                // An entity can't collide with nothing or the background.
                if (entity2.Get_Type() == EntityType.None || entity2.Get_Type() == EntityType.Background)
                {
                    continue;
                }

                // CollisionDetection
                if (this.within_collision_distance(entity1, entity2))
                {
                    // Tries to resolve a potential collision.
                    this.resolve_collision(entity1, entity2);
                }
            }
        }

    }



    /**
     *
     * @param entity1
     * @param entity2
     * @return
     */
    public Boolean within_collision_distance(Entity entity1, Entity entity2)
    {
        // Gets the difference in the 2 entities center position.
        float dx = (float) entity1.Get_X() - (float) entity2.Get_X();
        float dy = (float) entity1.Get_Y() - (float) entity2.Get_Y();

        // Gets the distance between the 2 entities centers.
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Check if the distance between them are less then the combined radius of the 2 entities.
        return distance < (entity1.Get_Radius() + entity2.Get_Radius());
    }



    /**
     *
     * @param entityA
     * @param entityB
     * @return
     */
    private boolean resolve_collision(Entity entityA, Entity entityB)
    {

        // Check if the Entities are of the same type.
        if ( entityA.Get_Type() == entityB.Get_Type() )
        {
            // Entities of the same type can't collide.
            return false;
        }

        // Check if both Entity can collide.
        if ((!entityA.Check_Can_Collide())  ||  (!entityB.Check_Can_Collide()))
        {
            // If they
            return true;
        }

        // Check if either Entity can take damage.
        if ((!entityA.Check_CanTake_Damaged())  &&  (!entityB.Check_CanTake_Damaged()))
        {
            return true;
        }

        // NPC is neutral — outside the combat system entirely.
        // Nothing damages an NPC, and an NPC damages nothing.
        if ((entityA.Get_Type() == EntityType.Player)  &&  (entityB.Get_Type() == EntityType.NPC))
        {
            return false;
        }

        // PowerUps are Player-exclusive.
        // Enemies should not interact with items meant for the player.
        if ((entityA.Get_Type() == EntityType.PowerUp)  &&  (entityB.Get_Type() != EntityType.Player))
        {
            return false;
        }

        // Resolve the damage between the 2 entities.
        this.resolve_collision_damage(entityA, entityB);

        // Resolve the push between the 2 entities.
        this.resolve_collision_push(entityA, entityB);

        // return true.
        return true;
    }



    /**
     *
     * @param entityA
     * @param entityB
     * @return
     */
    private boolean resolve_collision_damage  (Entity entityA, Entity entityB)
    {

        // IF Entity A can Take Collision Damage, it takes Collision Damage.
        if (entityA.Check_CanTake_Damaged()  &&  entityA.Check_CanTake_CollideDamage())
        {
            entityA.Take_Damage(entityB.Get_CollisionDamage());
        }

        // IF Entity B can Take Collision Damage, it takes Collision Damage.
        if (entityB.Check_CanTake_Damaged()  &&  entityB.Check_CanTake_CollideDamage())
        {
            entityB.Take_Damage(entityA.Get_CollisionDamage());
        }

        return true;
    }



    /**
     *
     * @param entityA
     * @param entityB
     * @return
     */
    private boolean resolve_collision_push  (Entity entityA, Entity entityB)
    {
        // Get the vector from B to A (the direction A should be pushed).
        double dx = entityA.Get_X() - entityB.Get_X();
        double dy = entityA.Get_Y() - entityB.Get_Y();

        // Get the distance between the two entity centers.
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Calculate how much the two entities are overlapping.
        // This is our priority — we MUST push at least this far.
        double overlap = (entityA.Get_Radius() + entityB.Get_Radius()) - distance;

        // Normalise to a unit vector (the collision normal).
        double nx;
        double ny;

        if (distance == 0)
        {
            // Entities are exactly on top of each other — no direction can be calculated.
            // Use a fallback direction, but still push by the full overlap distance.
            nx = 1;
            ny = 1;
        }
        else
        {
            nx = dx / distance;
            ny = dy / distance;
        }

        // Push each entity by the FULL overlap in opposite directions.
        // This guarantees they are always fully separated after the push,
        // even in edge cases where positions are near zero.
        if (entityA.Check_CanBe_Pushed())
        {
            entityA.Push_Entity((int)(nx * overlap), (int)(ny * overlap));
        }
        if (entityB.Check_CanBe_Pushed())
        {
            entityB.Push_Entity((int)(-nx * overlap), (int)(-ny * overlap));
        }

        return true;
    }
















}
