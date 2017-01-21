package ro.aniov.web.rpg.model.characters.hero;

/**
 * Created by Marius on 12/12/2016.
 */
public enum HeroType {

    HUNTER(8, 4), MAGE(6, 5), PALADIN(10, 3), WARLOCK(6, 6), PRIEST(7, 4);

    private int health;
    private int damage;

    HeroType(int health, int damage) {
        this.health = health;
        this.damage = damage;
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
}
