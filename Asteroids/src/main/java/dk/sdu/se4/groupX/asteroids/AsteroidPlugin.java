package dk.sdu.se4.groupX.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.se4.groupX.commonasteroids.Asteroid;
import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private final Random random = new Random();
    private static final int ASTEROID_COUNT = 5;

    @Override
    public void start(GameData gameData, World world) {

        for (int i = 0; i < ASTEROID_COUNT; i++)
        {
            world.addEntity(createAsteroid(gameData));
        }
    }

    @Override
    public void stop(GameData gameData, World world)
    {
        for (Entity asteroid : world.getEntities(Asteroid.class))
        {
            world.removeEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData)
    {
        // .
        Entity asteroid = new Asteroid();

        // .
        int size = random.nextInt(20) + 10;

        // .
        asteroid.Set_PolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.Set_X(random.nextInt(gameData.getDisplayWidth()));
        asteroid.Set_Y(random.nextInt(gameData.getDisplayHeight()));
        asteroid.Set_Radius((float)size);
        asteroid.Set_Rotation(random.nextInt(360));

        // Health and collision logic.
        asteroid.Set_Can_Collide(true);
        asteroid.Set_CanTake_CollideDamage(true);
        asteroid.Set_CanTake_Damaged(true);
        asteroid.Set_CollisionDamage(100); // destroys ships on contact
        asteroid.Set_Health(50);

        // .
        return asteroid;
    }
}