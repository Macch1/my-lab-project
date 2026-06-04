package dk.sdu.mmmi.cbse.common.data;


/**
 * GameData contains the UserInterface and the play-area for the game.
 * This includes the display dimensions and Key-binds.
 */
public class GameData {

    private int displayWidth  = 800 ;
    private int displayHeight = 800;
    private final GameKeys keys = new GameKeys();


    /**
     *
     *
     */
    public GameKeys getKeys() {
        return keys;
    }

    /**
     *
     * @param width
     */
    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    /**
     *
     * @return int
     */
    public int getDisplayWidth() {
        return displayWidth;
    }

    /**
     *
     * @param height
     */
    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    /**
     *
     * @return int
     */
    public int getDisplayHeight() {
        return displayHeight;
    }


}
