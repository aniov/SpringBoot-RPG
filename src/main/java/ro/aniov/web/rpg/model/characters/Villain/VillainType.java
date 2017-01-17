/**
 * Created by Marius on 12/20/2016.
 */
package ro.aniov.web.rpg.model.characters.Villain;

public enum VillainType {

    MURLOC(3, 4, "Murcloc"), ORC(5, 3, "Orc"), GOBLIN(4, 2, "Goblin"), GHOST(2, 5, "Ghost");

    int health;
    int damage;
    String name;

    VillainType(int health, int damage, String name) {
        this.health = health;
        this.damage = damage;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
