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
    void testWithincollisiondistance_WhenOverlapping_ReturnsTrue()
    {

        // Arrange
        entity1.Set_X(0);
        entity1.Set_Y(0);
        entity1.Set_Radius(10);

        entity2.Set_X(5);
        entity2.Set_Y(0);
        entity2.Set_Radius(10);

        // Act & Assert
        assertTrue(collisionDetector.within_collision_distance(entity1, entity2));
    }


    // .
    @Test
    void testWithincollisiondistance_WhenApart_ReturnsFalse()
    {
        // Arrange
        entity1.Set_X(0);
        entity1.Set_Y(0);
        entity1.Set_Radius(5);

        entity2.Set_X(15);
        entity2.Set_Y(0);
        entity2.Set_Radius(5);

        // Act & Assert
        assertFalse(collisionDetector.within_collision_distance(entity1, entity2));
    }


    // .
    @Test
    void testWithincollisiondistance_WhenTouching_ReturnsFalse()
    {
        // Arrange
        entity1.Set_X(0);
        entity1.Set_Y(0);
        entity1.Set_Radius(5);

        entity2.Set_X(10);
        entity2.Set_Y(0);
        entity2.Set_Radius(5);

        // Act & Assert
        assertFalse(collisionDetector.within_collision_distance(entity1, entity2));
    }


    // .
    @Test
    void testResolveCollision_WhenCannotCollide_NoDamage()
    {
        // Arrange
        World test_world = new World();
        GameData test_gameData = new GameData();

        entity1.Set_X(0);
        entity1.Set_Y(0);
        entity1.Set_Radius(10);
        entity1.Set_Health(100);
        entity1.Set_Can_Collide(false);  // cannot collide!

        entity2.Set_X(5);
        entity2.Set_Y(0);
        entity2.Set_Radius(10);
        entity2.Set_Health(100);
        entity2.Set_Can_Collide(false);  // cannot collide!

        test_world.addEntity(entity1);
        test_world.addEntity(entity2);

        // Act
        collisionDetector.process(test_gameData, test_world);

        // Assert
        assertEquals(100, entity1.Get_Health());
        assertEquals(100, entity2.Get_Health());
    }


    // .
    @Test
    void testResolveCollision_WhenCanDamage_DamageApplied()
    {
        // Arrange
        World test_world = new World();
        GameData test_gameData = new GameData();

        entity1.Set_X(0);
        entity1.Set_Y(0);
        entity1.Set_Radius(10);
        entity1.Set_Health(100);
        entity1.Set_Can_Collide(true);  // can collide!
        entity1.Set_CanTake_CollideDamage(true);
        entity1.Set_CanTake_Damaged(true);
        entity1.Set_CollisionDamage(25);

        entity2.Set_X(5);
        entity2.Set_Y(0);
        entity2.Set_Radius(10);
        entity2.Set_Health(100);
        entity2.Set_Can_Collide(true);  // can collide!
        entity2.Set_CanTake_CollideDamage(true);
        entity2.Set_CanTake_Damaged(true);
        entity2.Set_CollisionDamage(50);

        test_world.addEntity(entity1);
        test_world.addEntity(entity2);

        // Act
        collisionDetector.process(test_gameData, test_world);

        // Assert
        assertEquals(50, entity1.Get_Health());
        assertEquals(75, entity2.Get_Health());
    }


    // .
    @Test
    void testResolveCollision_WhenCanDamage_OnlyOneDamageApplied()
    {
        // Arrange
        World test_world = new World();
        GameData test_gameData = new GameData();

        entity1.Set_X(0);
        entity1.Set_Y(0);
        entity1.Set_Radius(10);
        entity1.Set_Health(100);
        entity1.Set_Can_Collide(true);  // can collide!
        entity1.Set_CanTake_CollideDamage(true);
        entity1.Set_CanTake_Damaged(false);   // Can not take damage.
        entity1.Set_CollisionDamage(25);

        entity2.Set_X(5);
        entity2.Set_Y(0);
        entity2.Set_Radius(10);
        entity2.Set_Health(100);
        entity2.Set_Can_Collide(true);  // can collide!
        entity2.Set_CanTake_CollideDamage(true);
        entity2.Set_CanTake_Damaged(true);   // can take damage.
        entity2.Set_CollisionDamage(50);

        test_world.addEntity(entity1);
        test_world.addEntity(entity2);

        // Act
        collisionDetector.process(test_gameData, test_world);

        // Assert
        assertEquals(100, entity1.Get_Health());
        assertEquals(75, entity2.Get_Health());
    }



    // .
    @Test
    void testResolveCollision_WhenCanDamage_NoDamageApplied()
    {
        // Arrange
        World test_world = new World();
        GameData test_gameData = new GameData();

        entity1.Set_X(0);
        entity1.Set_Y(0);
        entity1.Set_Radius(10);
        entity1.Set_Health(100);
        entity1.Set_Can_Collide(true);  // can collide!
        entity1.Set_CanTake_CollideDamage(true);
        entity1.Set_CanTake_Damaged(false);   // Can not be damaged.
        entity1.Set_CollisionDamage(25);

        entity2.Set_X(5);
        entity2.Set_Y(0);
        entity2.Set_Radius(10);
        entity2.Set_Health(100);
        entity2.Set_Can_Collide(true);  // can collide!
        entity2.Set_CanTake_CollideDamage(true);
        entity2.Set_CanTake_Damaged(false);   // Can not be damaged.
        entity2.Set_CollisionDamage(50);

        test_world.addEntity(entity1);
        test_world.addEntity(entity2);

        // Act
        collisionDetector.process(test_gameData, test_world);

        // Assert
        assertEquals(100, entity1.Get_Health());
        assertEquals(100, entity2.Get_Health());
    }


    @Test
    void testWithincollisiondistance_WithItself_ReturnsFalse()
    {
        // Arrange
        entity1.Set_X(0);
        entity1.Set_Y(0);
        entity1.Set_Radius(10);
        entity1.Set_Health(100);
        entity1.Set_Can_Collide(true);
        entity1.Set_CanTake_CollideDamage(true);
        entity1.Set_CanTake_Damaged(true);
        entity1.Set_CollisionDamage(25);

        World test_world = new World();
        GameData test_gameData = new GameData();
        test_world.addEntity(entity1);

        // Act
        collisionDetector.process(test_gameData, test_world);

        // Assert
        assertEquals(100, entity1.Get_Health());
    }




}