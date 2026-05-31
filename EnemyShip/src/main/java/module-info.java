import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

module EnemyShip {
    requires Common;
    requires CommonEnemy;
    requires CommonPlayer;
    requires CommonBullet;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    provides IGamePluginService with dk.sdu.se4.groupX.enemyship.EnemyShipPlugin;
    provides IEntityProcessingService with dk.sdu.se4.groupX.enemyship.EnemyShipProcessor;
}
