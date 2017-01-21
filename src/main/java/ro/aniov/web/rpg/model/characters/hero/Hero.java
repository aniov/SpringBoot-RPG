/**
 * Created by Marius on 12/12/2016.
 */
package ro.aniov.web.rpg.model.characters.hero;

import org.springframework.format.annotation.DateTimeFormat;
import ro.aniov.web.rpg.model.User;
import ro.aniov.web.rpg.model.characters.hero.artifact.Artifact;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * Hero Entity
 */

@Entity
@Table(name = "hero")
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    private int level = 1;

    @NotNull
    private int health;

    @NotNull
    private int damage;

    @NotNull
    @Max(value = 100, message = "Maximum experience")
    private int experience = 0;

    @NotNull
    @Column(name = "hero_type")
    @Enumerated(EnumType.STRING)
    private HeroType heroType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hero", cascade = CascadeType.ALL)
    private Set<Artifact> artifacts;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy/MM/dd hh:mm:ss")
    private Date created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate(){
        if (created == null)
            created = new Date();
    }

    public Hero() {
    }

    public Hero(String name, HeroType heroType, User user) {
        this.name = name;
        this.heroType = heroType;
        this.health = heroType.getHealth() * level;
        this.damage = heroType.getDamage() * level;
        this.user = user;
    }

    public Hero(Long heroId){
        id = heroId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public void setHeroType(HeroType heroType) {
        this.heroType = heroType;
    }

    public Set<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(Set<Artifact> artifact) {
        this.artifacts = artifact;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
