package dk.sdu.mmmi.cbse.common.data;


/**
 *
 *
 *
 *
 * Note: Future-proofed, by having types beyond what is currently needed.
 */
public enum EntityType
{

    /**
     * None: Just a empty none-defined type. To be used as the default incase the type is not yet determined.
     */
    None,

    /**
     * Background: Something that exists behind the play area, that often seems distant.
     * Such as: Stars in the distance, Galaxies and such.
     */
    Background,

    /**
     * Solids: permanent, immovable, indestructible world geometry.
     * Such as: Walls, pillars and such.
     */
    Solid,

    /**
     * Obstacles: dynamic blockers that can move or be destroyed.
     * Such as: Asteroids, Barrels, Gates, and such.
     */
    Obstacle,

    /**
     * Players: the entity controlled by the player.
     * Such as: Player ship, player character and such.
     */
    Player,

    /**
     * NPC: None Player Characters, and we separate them from enemies (even if technically they enemies also are NPC's).
     * Such as: Background characters, Traders, Quest givers and such.
     */
    NPC,

    /**
     * Enemy: None Player Characters, that attacks the player.
     * Such as: Enemy ships, enemy turrets and such.
     */
    Enemy,

    /**
     * Bullet: A special type of Obstacle, used by other types of Entities to Interact with other Entities or damage other Entities.
     * Such as: Bullets, Mines, and such.
     */
    Bullet,

    /**
     * Item: A special type of Obstacle, used by Player Entities when colliding.
     * Such as: Power-ups, Pick-ups, Coins, Health-packs and such.
     */
    PowerUp

}
