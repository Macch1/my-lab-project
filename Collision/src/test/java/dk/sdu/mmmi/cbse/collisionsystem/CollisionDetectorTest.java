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
    void testCollides_WhenOverlapping_ReturnsTrue()
    {
        // Arrange
        entity1.setX(0);
        entity1.setY(0);
        entity1.setRadius(10);

        entity2.setX(5);
        entity2.setY(0);
        entity2.setRadius(10);

        // Act & Assert
        assertTrue(collisionDetector.collides(entity1, entity2));
    }

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