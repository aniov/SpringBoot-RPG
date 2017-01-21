/**
 * Created by Marius on 12/12/2016.
 */
package ro.aniov.web.rpg.model.characters.hero.artifact;

/**
 *  Represents an ArtifactType ENUM
 */

public enum ArtifactType {

    SWORD(2, 1, "Sword"), AXE(1, 2, "Axe"), BOW(2, 1, "Bow"), HELMET(3, 1, "Helmet"),
    STAFF(3, 2, "Staff"), RING(2, 1, "Ring"), NECKLACE(1, 3, "Necklace"), SHIELD(4, 1, "Shield");

    private int health;
    private int damage;
    private String name;


    ArtifactType(int health, int damage, String name) {

        this.health = health;
        this.damage = damage;
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }
}
