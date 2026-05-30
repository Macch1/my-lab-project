package dk.sdu.se4.groupX.enemyship;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.se4.groupX.commonenemy.Enemy;
import dk.sdu.se4.groupX.commonenemy.EnemySPI;

import java.util.Random;


public class EnemyShipProcessingSystem implements IEntityProcessingService, EnemySPI
{




    /**
     *
     * @param gameData
     * @return
     */
    private Entity createEnemyShip(GameData gameData)
    {

        Entity enemyShip = new EnemyShip();
        enemyShip.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemyShip.setX(gameData.getDisplayHeight()/2);
        enemyShip.setY(gameData.getDisplayWidth()/2);
        enemyShip.setRadius(8);
        enemyShip.setRotation();
        return enemyShip;
    }




    /**
     *
     * @param gameData contains the UserInterface and the play-area for the game.
     * @param world contains and updates the Game-world, and all the entities inside it.
     */
    @Override
    public void process(GameData gameData, World world)
    {
        Random random_generator = new Random();

        int direction_negative_modifier = 1;
        int direction_options = 3;

        int random_direction_x;
        int random_direction_y;
        double enemyShip_newRotation;

        for (Entity enemyShip : world.getEntities(Enemy.class))
        {

            // Random number in the spectrum = {1, 0, -1}
            // To determine the direction the unit will move.
            random_direction_x = random_generator.nextInt(direction_options) - direction_negative_modifier;
            random_direction_y = random_generator.nextInt(direction_options) - direction_negative_modifier;
            enemyShip_newRotation = 0;

            // Not moving.
            if (random_direction_x == 0 && random_direction_y == 0)
            {
                // Remains empty for now.
            }

            // Moving along the X-aksel.
            if (random_direction_x <= -1)
            {
                enemyShip.setRotation(enemyShip.getRotation() - 5);

                double changeX = Math.cos(Math.toRadians(enemyShip.getRotation()));
                double changeY = Math.sin(Math.toRadians(enemyShip.getRotation()));

                enemyShip.setRotation(enemyShip.getRotation() - 5);

                enemyShip.setX(enemyShip.getX() + changeX);
                enemyShip.setY(enemyShip.getY() + changeY);
            }
            else if (random_direction_x >= 1)
            {

            }

            // Moving along the Y-aksel.
            if (random_direction_y <= -1)
            {

            }
            else if (random_direction_y >= 1)
            {

            }

        }

    }




    /**
     *
     */
    public void enemyShip_targetDecision()
    {

    }



}
