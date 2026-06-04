package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;


/**
 * CollisionDetector is the implementation of IPostEntityProcessingService
 * responsible for detecting and resolving collisions between all entities in the world.
 *
 * Runs after all IEntityProcessingService implementations have completed each frame,
 * ensuring all entity positions are up to date before collision detection begins.
 *
 * Handles three stages of collision resolution:
 * 1. Distance check - determines if two entities are close enough to collide.
 * 2. Damage resolution - applies damage to entities based on their collision flags.
 * 3. Push resolution - physically separates overlapping entities to prevent repeated collisions.
 */
public class CollisionDetector implements IPostEntityProcessingService
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    /**
     * Default constructor for CollisionDetector.
     */
    public CollisionDetector()
    {
    }




    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////    IPostEntityProcessingService Methods    ////////////////////
    ///


    /**
     * Checks all entity pairs in the world for collisions and resolves them.
     * Skips entities of type None and Background - they cannot participate in collisions.
     * Called once per frame by Game.java after all IEntityProcessingService calls.
     *
     * Pre-Condition:  gameData != null, world != null, all IEntityProcessingService
     *                 implementations have already been called for the current frame.
     * Post-Condition: all collisions detected and resolved for the current frame.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void PostEntityProcess(GameData gameData, World world)
    {
        // Two for loops for all entities in the world.
        for (Entity entity1 : world.getEntities())
        {
            // If either entity is of type "None" or "Background", skip the iteration.
            // An entity can't collide with nothing or the background.
            if (entity1.Get_Type() == EntityType.None || entity1.Get_Type() == EntityType.Background)
            {
                // Skip to the next entity.
                continue;
            }

            // Check entity1 against all other entities in the world.
            for (Entity entity2 : world.getEntities())
            {
                // If the two entities are identical, skip the iteration.
                // An entity can't collide with itself.
                if (entity1.Get_ID().equals(entity2.Get_ID()))
                {
                    // Skip - an entity cannot collide with itself.
                    continue;
                }

                // If either entity is of type "None" or "Background", skip the iteration.
                // An entity can't collide with nothing or the background.
                if (entity2.Get_Type() == EntityType.None || entity2.Get_Type() == EntityType.Background)
                {
                    // Skip to the next entity.
                    continue;
                }

                // CollisionDetection - check if the two entities are within collision distance.
                if (this.within_collision_distance(entity1, entity2))
                {
                    // Tries to resolve a potential collision.
                    this.resolve_collision(entity1, entity2);
                }
            }
        }
    }




    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///


    /**
     * Checks if two entities are within collision distance of each other.
     * Uses the sum of their radii as the collision threshold.
     *
     * @param entity1 the first entity to check.
     * @param entity2 the second entity to check.
     * @return true if the entities are within collision distance, false otherwise.
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
     * Determines whether two entities should interact and resolves the collision if so.
     * Applies type-pair immunity rules, then resolves damage and push if applicable.
     *
     * @param entityA the first entity in the collision.
     * @param entityB the second entity in the collision.
     * @return true if the collision was processed, false if it was ignored.
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
            // If they can't collide, skip the collision.
            return true;
        }

        // Check if either Entity can take damage.
        if ((!entityA.Check_CanTake_Damaged())  &&  (!entityB.Check_CanTake_Damaged()))
        {
            // Neither entity can take damage - skip the collision.
            return true;
        }

        // NPC is neutral - outside the combat system entirely.
        // Nothing damages an NPC, and an NPC damages nothing.
        if ((entityA.Get_Type() == EntityType.Player)  &&  (entityB.Get_Type() == EntityType.NPC))
        {
            // Player and NPC - no damage exchanged.
            return false;
        }

        // PowerUps are Player-exclusive.
        // Enemies should not interact with items meant for the player.
        if ((entityA.Get_Type() == EntityType.PowerUp)  &&  (entityB.Get_Type() != EntityType.Player))
        {
            // PowerUp and non-Player - no damage exchanged.
            return false;
        }

        // Resolve the damage between the 2 entities.
        this.resolve_collision_damage(entityA, entityB);

        // Resolve the push between the 2 entities.
        this.resolve_collision_push(entityA, entityB);

        // Collision fully resolved.
        return true;
    }


    /**
     * Applies damage to each entity based on their collision flags.
     * Each entity independently determines whether it takes damage from the collision.
     *
     * @param entityA the first entity in the collision.
     * @param entityB the second entity in the collision.
     * @return true when damage resolution is complete.
     */
    private boolean resolve_collision_damage(Entity entityA, Entity entityB)
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
     * Physically separates two overlapping entities along the collision normal.
     * Pushes each entity by the full overlap distance in opposite directions,
     * guaranteeing separation and preventing repeated collision damage next frame.
     *
     * @param entityA the first entity in the collision.
     * @param entityB the second entity in the collision.
     * @return true when push resolution is complete.
     */
    private boolean resolve_collision_push(Entity entityA, Entity entityB)
    {
        // Get the vector from B to A (the direction A should be pushed).
        double dx = entityA.Get_X() - entityB.Get_X();
        double dy = entityA.Get_Y() - entityB.Get_Y();

        // Get the distance between the two entity centers.
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Calculate how much the two entities are overlapping.
        // This is our priority - we MUST push at least this far.
        double overlap = (entityA.Get_Radius() + entityB.Get_Radius()) - distance;

        // Normalise to a unit vector (the collision normal).
        double nx;
        double ny;

        // Handle the edge case where entities are exactly on top of each other.
        if (distance == 0)
        {
            // Entities are exactly on top of each other - no direction can be calculated.
            // Use a fallback direction, but still push by the full overlap distance.
            nx = 1;
            ny = 0;
        }
        else
        {
            // Calculate the normalised collision direction vector.
            nx = dx / distance;
            ny = dy / distance;
        }

        // Push each entity by the FULL overlap in opposite directions.
        // This guarantees they are always fully separated after the push,
        // even in edge cases where positions are near zero.
        if (entityA.Check_CanBe_Pushed())
        {
            // Push entity A away from entity B.
            entityA.Push_Entity((int)(nx * overlap * 2), (int)(ny * overlap * 2));
        }

        // Check if entity B can be pushed.
        if (entityB.Check_CanBe_Pushed())
        {
            // Push entity B away from entity A.
            entityB.Push_Entity((int)(-nx * overlap * 2), (int)(-ny * overlap * 2));
        }

        // Push resolution complete.
        return true;
    }


}