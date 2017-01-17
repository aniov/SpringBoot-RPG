/**
 * Created by Marius on 12/22/2016.
 */
package ro.aniov.web.rpg.dto;

public enum HeroArtifactAction {

    KEEP("Keep"), DROP("Drop");

    private String name;

    HeroArtifactAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
