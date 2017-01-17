/**
 * Created by Marius on 12/22/2016.
 */
package ro.aniov.web.rpg.dto;


public enum HeroMoveType {

    UP(-1, 0, "Up"), DOWN(1, 0, "Down"), LEFT(0, -1, "Left"), RIGHT(0, 1, "Right");

    private int x;
    private int y;
    private String name;


    HeroMoveType(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
