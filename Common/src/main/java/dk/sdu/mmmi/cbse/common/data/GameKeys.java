package dk.sdu.mmmi.cbse.common.data;


/**
 * GameKeys tracks the state of all key-binds in the game.
 * Maintains both the current and previous frame key states,
 * allowing processors to detect both held keys and single-press events.
 * Updated once per frame by the game loop in Game.java.
 */
public class GameKeys
{

    // Current and previous frame key states — used to detect held and single-press events.
    private static boolean[] keys;
    private static boolean[] pkeys;

    // Key constants — defines the total number of keys and their index mappings.
    private static final int NUM_KEYS = 5;
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int SPACE = 3;
    public static final int ENTER = 4;




    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    /**
     * Default constructor for GameKeys.
     * Initialises the current and previous frame key state arrays.
     */
    public GameKeys()
    {
        // Initialise the current and previous frame key state arrays.
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
    }




    ////////////////////////////////////////////////////////////////
    ////////////////////    Key State Methods    ////////////////////
    ///


    /**
     * Updates the previous frame key states to match the current frame.
     * Called once per frame by the game loop in Game.java.
     * Must be called after all input has been processed for the current frame.
     */
    public void update()
    {
        // Copy the current frame key states into the previous frame key states.
        for (int i = 0; i < NUM_KEYS; i++)
        {
            pkeys[i] = keys[i];
        }
    }


    /**
     * Sets the state of the given key.
     * Called by the key event handlers in Game.java when a key is pressed or released.
     * @param k the key index to update (use the key constants defined in this class).
     * @param b true if the key is pressed, false if released.
     */
    public void setKey(int k, boolean b)
    {
        keys[k] = b;
    }


    /**
     * Returns whether the given key is currently held down.
     * @param k the key index to check (use the key constants defined in this class).
     * @return true if the key is currently held down, false otherwise.
     */
    public boolean isDown(int k)
    {
        return keys[k];
    }


    /**
     * Returns whether the given key was pressed this frame only.
     * A key is considered pressed if it is currently down but was not down last frame.
     * Useful for single-press events such as restarting the game with ENTER.
     * @param k the key index to check (use the key constants defined in this class).
     * @return true if the key was pressed this frame only, false otherwise.
     */
    public boolean isPressed(int k)
    {
        // A key is "pressed" if it is currently down but was not down in the previous frame.
        return keys[k] && !pkeys[k];
    }

}