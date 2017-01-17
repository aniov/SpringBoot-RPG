package ro.aniov.web.rpg.model.characters.hero;

/**
 * Created by Marius on 12/12/2016.
 */
public enum HeroType {

    HUNTER(10, 6), MAGE(8, 7), PALADIN(12, 5), WARLOCK(8, 8), PRIEST(9, 6);

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
