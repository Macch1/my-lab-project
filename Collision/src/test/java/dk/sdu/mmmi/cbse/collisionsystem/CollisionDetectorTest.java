package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.GameData;
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
    void testCollides_WhenApart_ReturnsFalse()
    {
        // Arrange
        entity1.setX(0);
        entity1.setY(0);
        entity1.setRadius(5);

        entity2.setX(15);
        entity2.setY(0);
        entity2.setRadius(5);

        // Act & Assert
        assertFalse(collisionDetector.collides(entity1, entity2));
    }


    // .
    @Test
    void testCollides_WhenTouching_ReturnsFalse()
    {
        // Arrange
        entity1.setX(0);
        entity1.setY(0);
        entity1.setRadius(5);

        entity2.setX(10);
        entity2.setY(0);
        entity2.setRadius(5);

        // Act & Assert
        assertFalse(collisionDetector.collides(entity1, entity2));
    }


    // .
    @Test
    void testResolveCollision_WhenCannotCollide_NoDamage()
    {
        // Arrange
        World test_world = new World();
        GameData test_gameData = new GameData();

        entity1.setX(0);
        entity1.setY(0);
        entity1.setRadius(10);
        entity1.setHealth(100);
        entity1.setCanCollide(false);  // cannot collide!

        entity2.setX(5);
        entity2.setY(0);
        entity2.setRadius(10);
        entity2.setHealth(100);
        entity2.setCanCollide(false);  // cannot collide!

        test_world.addEntity(entity1);
        test_world.addEntity(entity2);

        // Act
        collisionDetector.process(test_gameData, test_world);

        // Assert
        assertEquals(100, entity1.getHealth());
        assertEquals(100, entity2.getHealth());
    }


    // .
    @Test
    void testResolveCollision_WhenCanDamage_DamageApplied()
    {
        // Arrange
        World test_world = new World();
        GameData test_gameData = new GameData();

        entity1.setX(0);
        entity1.setY(0);
        entity1.setRadius(10);
        entity1.setHealth(100);
        entity1.setCanCollide(true);  // can collide!
        entity1.setCanDamage(true);
        entity1.setCanBeDamaged(true);
        entity1.setCollisionDamage(25);

        entity2.setX(5);
        entity2.setY(0);
        entity2.setRadius(10);
        entity2.setHealth(100);
        entity2.setCanCollide(true);  // can collide!
        entity2.setCanDamage(true);
        entity2.setCanBeDamaged(true);
        entity2.setCollisionDamage(50);

        test_world.addEntity(entity1);
        test_world.addEntity(entity2);

        // Act
        collisionDetector.process(test_gameData, test_world);

        // Assert
        assertEquals(50, entity1.getHealth());
        assertEquals(75, entity2.getHealth());
    }


    // .
    @Test
    void testResolveCollision_WhenCanDamage_OnlyOneDamageApplied()
    {
        // Arrange
        World test_world = new World();
        GameData test_gameData = new GameData();

        entity1.setX(0);
        entity1.setY(0);
        entity1.setRadius(10);
        entity1.setHealth(100);
        entity1.setCanCollide(true);  // can collide!
        entity1.setCanDamage(true);
        entity1.setCanBeDamaged(false);   // Can not take damage.
        entity1.setCollisionDamage(25);

        entity2.setX(5);
        entity2.setY(0);
        entity2.setRadius(10);
        entity2.setHealth(100);
        entity2.setCanCollide(true);  // can collide!
        entity2.setCanDamage(true);
        entity2.setCanBeDamaged(true);   // can take damage.
        entity2.setCollisionDamage(50);

        test_world.addEntity(entity1);
        test_world.addEntity(entity2);

        // Act
        collisionDetector.process(test_gameData, test_world);

        // Assert
        assertEquals(100, entity1.getHealth());
        assertEquals(75, entity2.getHealth());
    }



    // .
    @Test
    void testResolveCollision_WhenCanDamage_NoDamageApplied()
    {
        // Arrange
        World test_world = new World();
        GameData test_gameData = new GameData();

        entity1.setX(0);
        entity1.setY(0);
        entity1.setRadius(10);
        entity1.setHealth(100);
        entity1.setCanCollide(true);  // can collide!
        entity1.setCanDamage(true);
        entity1.setCanBeDamaged(false);   // Can not be damaged.
        entity1.setCollisionDamage(25);

        entity2.setX(5);
        entity2.setY(0);
        entity2.setRadius(10);
        entity2.setHealth(100);
        entity2.setCanCollide(true);  // can collide!
        entity2.setCanDamage(true);
        entity2.setCanBeDamaged(false);   // Can not be damaged.
        entity2.setCollisionDamage(50);

        test_world.addEntity(entity1);
        test_world.addEntity(entity2);

        // Act
        collisionDetector.process(test_gameData, test_world);

        // Assert
        assertEquals(100, entity1.getHealth());
        assertEquals(100, entity2.getHealth());
    }


    @Test
    void testCollides_WithItself_ReturnsFalse()
    {
        // Arrange
        entity1.setX(0);
        entity1.setY(0);
        entity1.setRadius(10);
        entity1.setHealth(100);
        entity1.setCanCollide(true);
        entity1.setCanDamage(true);
        entity1.setCanBeDamaged(true);
        entity1.setCollisionDamage(25);

        World test_world = new World();
        GameData test_gameData = new GameData();
        test_world.addEntity(entity1);

        // Act
        collisionDetector.process(test_gameData, test_world);

        // Assert
        assertEquals(100, entity1.getHealth());
    }




}