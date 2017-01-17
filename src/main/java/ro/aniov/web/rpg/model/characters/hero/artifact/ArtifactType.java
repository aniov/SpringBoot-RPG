/**
 * Created by Marius on 12/12/2016.
 */
package ro.aniov.web.rpg.model.characters.hero.artifact;

/**
 *  Represents an ArtifactType ENUM
 */

public enum ArtifactType {

    SWORD(7, 5, "Sword"), AXE(1, 6, "Axe"), BOW(2, 5, "Bow"), HELMET(6, 1, "Helmet"),
    STAFF(4, 5, "Staff"), RING(3, 3, "Ring"), NECKLACE(2, 4, "Necklace"), SHIELD(9, 1, "Shield");

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
