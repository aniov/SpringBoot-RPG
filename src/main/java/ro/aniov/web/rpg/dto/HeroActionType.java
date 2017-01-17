package ro.aniov.web.rpg.dto;

/**
 * Created by Marius on 12/21/2016.
 */
public enum HeroActionType {

    FIGHT("Fight"), RUN("Run");

    private String name;

    HeroActionType(String action) {
        this.name = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
