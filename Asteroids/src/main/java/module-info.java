import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

module Asteroids {
    requires Common;
    requires CommonAsteroids;
    provides IGamePluginService with dk.sdu.se4.groupX.asteroids.AsteroidPlugin;
    provides IEntityProcessingService with dk.sdu.se4.groupX.asteroids.AsteroidProcessor;
}