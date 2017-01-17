package ro.aniov.web.rpg.model;

/**
 * Created by Marius on 12/8/2016.
 */
public enum Role {

    ROLE_TRIAL("ROLE_TRIAL"), ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
