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

        if (entityA.Get_Type() == ) {

            // .
            // Deal damage to Entity B, based on Entity A
            if (entityA.Check_CanTake_CollideDamage() && entityB.Check_CanTake_Damaged()) {
                entityB.Take_Damage(entityA.Get_CollisionDamage());
            }

        }


        if ()
        {

            // .
            // Deal damage to Entity B, based on Entity A
            if ((!entityA.Check_CanTake_CollideDamage()) && entityB.Check_CanTake_Damaged()) {
                entityB.Take_Damage(entityA.Get_CollisionDamage());
            }

        }


        if ()
        {

            // .
            // Deal damage to Entity B, based on Entity A
            if (entityA.Check_CanTake_CollideDamage() && (!entityB.Check_CanTake_Damaged())) {
                entityB.Take_Damage(entityA.Get_CollisionDamage());
            }
        }

    }



    /**
     *
     * @param entityA
     * @param entityB
     * @return
     */
    private boolean resolve_collision_push  (Entity entityA, Entity entityB)
    {
        // This is for future improvements where we can make them get pushed around.
        return true;
    }
















}
