package dk.sdu.se4.groupX.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.se4.groupX.commonasteroids.Asteroid;
import dk.sdu.se4.groupX.commonasteroids.AsteroidSplitterSPI;
import java.util.Random;

public class AsteroidSplitter implements AsteroidSplitterSPI
{

    // .
    private static final float MIN_SIZE = 5f;

    // .
    private final Random random = new Random();



    @Override
    public void splitAsteroid(Entity e, World world)
    {
        // .
        world.removeEntity(e);

        // .
        float newSize = e.Get_Radius() / 2f;

        // .
        if (newSize < MIN_SIZE)
        {
            return;
        }

        // .
        for (int i = 0; i < 2; i++)
        {
            // .
            Entity fragment = new Asteroid();

            // .
            int s = (int) newSize;

            // .
            fragment.Set_PolygonCoordinates(s, -s, -s, -s, -s, s, s, s);

            // .
            fragment.Set_X(e.Get_X() + random.nextInt(20) - 10);
            fragment.Set_Y(e.Get_Y() + random.nextInt(20) - 10);
            fragment.Set_Radius(newSize);
            fragment.Set_Rotation(random.nextInt(360));

            // Set collision properties
            fragment.Set_Health(50);

            // .
            world.addEntity(fragment);
        }
    }


}