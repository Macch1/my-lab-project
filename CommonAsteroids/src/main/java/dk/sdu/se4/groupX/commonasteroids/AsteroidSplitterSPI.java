package dk.sdu.se4.groupX.commonasteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public interface AsteroidSplitterSPI {
    void splitAsteroid(Entity e, World world);
}