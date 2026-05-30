package dk.sdu.se4.groupX.commonenemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author corfixen
 */
public interface EnemySPI
{
    Entity createEnemy(Entity e, GameData gameData);
}
