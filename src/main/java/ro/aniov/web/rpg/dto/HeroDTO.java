/**
 * Created by Marius on 12/14/2016.
 */
package ro.aniov.web.rpg.dto;

import ro.aniov.web.rpg.dto.validators.NameIsValid;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO representing registering a new Hero.
 */

public class HeroDTO {

    @NotNull
    @Size(min = 3, max = 30, message = "Hero name must be between 3 and 30 characters")
    @NameIsValid
    private String name;

    @NotNull(message = "You must pick a hero type")
    private String heroType;

    public HeroDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeroType() {
        return heroType;
    }

    public void setHeroType(String heroType) {
        this.heroType = heroType;
    }
}
