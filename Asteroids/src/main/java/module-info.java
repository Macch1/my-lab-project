import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.se4.groupX.commonasteroids.AsteroidSplitterSPI;

module Asteroids {
    requires Common;
    requires CommonAsteroids;
    provides IGamePluginService with dk.sdu.se4.groupX.asteroids.AsteroidPlugin;
    provides IEntityProcessingService with dk.sdu.se4.groupX.asteroids.AsteroidProcessor;
    provides AsteroidSplitterSPI with dk.sdu.se4.groupX.asteroids.AsteroidSplitter;
}