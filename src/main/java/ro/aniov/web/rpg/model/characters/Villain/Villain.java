/**
 * Created by Marius on 12/12/2016.
 */
package ro.aniov.web.rpg.model.characters.Villain;

public class Villain {

    private int health;
    private int damage;
    private int level;
    private VillainType villainType;

    public Villain(VillainType villainType, int level) {

        this.level = level;
        this.villainType = villainType;
        health = this.level * this.villainType.getHealth();
        damage = this.level * this.villainType.getDamage();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getLevel() {
        return level;
    }
}
