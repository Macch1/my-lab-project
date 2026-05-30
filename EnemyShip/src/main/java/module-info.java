import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

module EnemyShip {
    requires Common;
    requires CommonEnemy;
    provides IGamePluginService with dk.sdu.se4.groupX.enemyship.EnemyShipPlugin;
    provides IEntityProcessingService with dk.sdu.se4.groupX.enemyship.EnemyShipProcessingSystem;
}
