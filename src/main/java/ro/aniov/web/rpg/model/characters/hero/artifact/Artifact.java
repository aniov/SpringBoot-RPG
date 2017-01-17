/**
 * Created by Marius on 12/12/2016.
 */
package ro.aniov.web.rpg.model.characters.hero.artifact;

import ro.aniov.web.rpg.model.characters.hero.Hero;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Random;

@Entity
@Table(name = "inventory")
public class Artifact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "health")
    private int health;

    @NotNull
    @Column(name = "damage")
    private int damage;

    @NotNull
    @Column(name = "level")
    private int level;

    @Column(name = "artifact_type")
    @Enumerated(EnumType.STRING)
    private ArtifactType artifactType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hero_id", nullable = false)
    private Hero hero;

    public Artifact() {}

    /**
     * Create a new Artifact based on lvl
     */
    public Artifact(int level) {

        this.level = level;
        this.artifactType = getRandomArtifact();
        this.health = level * this.artifactType.getHealth();
        this.damage = level * this.artifactType.getDamage();
    }

    public Long getId() {
        return id;
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

    public void setLevel(int level) {
        this.level = level;
    }

    public ArtifactType getArtifactType() {
        return artifactType;
    }

    public void setArtifactType(ArtifactType artifactType) {
        this.artifactType = artifactType;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    /**
    *  Pick a random artifact
     */
    private ArtifactType getRandomArtifact(){

        int random = new Random().nextInt(ArtifactType.values().length);
        return ArtifactType.values()[random];
    }

    @Override
    public String toString() {
        return "Artifact{" +
                "id=" + id +
                ", health=" + health +
                ", damage=" + damage +
                ", level=" + level +
                ", artifactType=" + artifactType +
                ", hero=" + hero +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artifact artifact = (Artifact) o;

        return artifactType == artifact.artifactType;
    }

    @Override
    public int hashCode() {
        return artifactType != null ? artifactType.hashCode() : 0;
    }
}
