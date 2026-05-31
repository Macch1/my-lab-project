package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {

    private CollisionDetector collisionDetector;
    private Entity entity1;
    private Entity entity2;

    // .
    @BeforeEach
    void setUp() {
        collisionDetector = new CollisionDetector();
        entity1 = new Entity();
        entity2 = new Entity();
    }

    // .
    @Test
    void testCollides_WhenOverlapping_ReturnsTrue() { ... }

    // .
    @Test
    void testCollides_WhenApart_ReturnsFalse() { ... }

    // .
    @Test
    void testCollides_WhenTouching_ReturnsFalse() { ... }

    // .
    @Test
    void testResolveCollision_WhenCannotCollide_NoDamage() { ... }

    // .
    @Test
    void testResolveCollision_WhenCanDamage_DamageApplied() { ... }
}