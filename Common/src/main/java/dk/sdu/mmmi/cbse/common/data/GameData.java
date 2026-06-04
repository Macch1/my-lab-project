package dk.sdu.mmmi.cbse.common.data;


/**
 * GameData contains the UserInterface and the play-area for the game.
 * This includes the display dimensions and key-binds.
 * Passed to all processors and plugins every frame as the primary source of game environment data.
 */
public class GameData
{


    // The dimensions of the game display area in pixels.
    private int displayWidth  = 800;
    private int displayHeight = 800;

    // The key-binds for the game, tracking which keys are currently pressed.
    private final GameKeys keys = new GameKeys();




    ////////////////////////////////////////////////////////////////
    ////////////////////    Display Methods    ////////////////////
    ///


    /**
     * Sets the width of the game display area.
     * @param width the display width in pixels.
     */
    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }


    /**
     * Returns the width of the game display area.
     * @return the display width in pixels.
     */
    public int getDisplayWidth() {
        return this.displayWidth;
    }


    /**
     * Sets the height of the game display area.
     * @param height the display height in pixels.
     */
    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }


    /**
     * Returns the height of the game display area.
     * @return the display height in pixels.
     */
    public int getDisplayHeight() {
        return this.displayHeight;
    }




    ////////////////////////////////////////////////////////////////
    ////////////////////    Input Methods    ////////////////////
    ///


    /**
     * Returns the GameKeys instance used to track key-bind states.
     * @return the GameKeys instance for this game session.
     */
    public GameKeys getKeys() {
        return this.keys;
    }


}