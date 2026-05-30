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
        float newSize = e.getRadius() / 2f;

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
            fragment.setPolygonCoordinates(s, -s, -s, -s, -s, s, s, s);

            // .
            fragment.setX(e.getX() + random.nextInt(20) - 10);
            fragment.setY(e.getY() + random.nextInt(20) - 10);
            fragment.setRadius(newSize);
            fragment.setRotation(random.nextInt(360));

            // .
            world.addEntity(fragment);
        }
    }


}