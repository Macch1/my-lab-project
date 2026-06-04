package dk.sdu.se4.groupX.enemyship;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.se4.groupX.commonenemy.Enemy;

import java.util.Random;


public class EnemyShipPlugin implements IGamePluginService
{





    public EnemyShipPlugin()
    {

    }




    /////////////////////////////////////////////////////////////////////////
    ////////////////////    GamePluginService Methods    ////////////////////
    ///


    /**
     * Starts the game using the gameData and World.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void start(GameData gameData, World world)
    {
        // .
        System.out.println("EnemyShip plugin starting.");

        // .
        for (int i = 0; i < world.Get_MaxEnemies(); i++)
        {
            // .
            Entity enemy = EnemyCreationHelper.createEnemy(gameData, world);

            // .
            world.addEntity(enemy);
            world.AddTo_CurrentEnemyCount(1);
        }

    }




    /**
     * Stops the game using the gameData and World.
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void stop(GameData gameData, World world)
    {
        // .
        System.out.println("EnemyShip plugin stopping.");

        // .
        for (Entity enemy : world.getEntities(Enemy.class))
        {
            world.removeEntity(enemy);
        }

        // .
        world.Set_CurrentEnemyCount(0);
    }









    //////////////////////////////////////////////////////////////
    ////////////////////    Helper Methods    ////////////////////
    ///






}
