/**
 * Created by Marius on 12/21/2016.
 */
package ro.aniov.web.rpg.dto;

import ro.aniov.web.rpg.model.characters.Villain.Villain;
import ro.aniov.web.rpg.model.characters.hero.Hero;
import ro.aniov.web.rpg.model.characters.hero.HeroType;
import ro.aniov.web.rpg.model.characters.hero.artifact.Artifact;

import java.util.LinkedHashSet;

public class HeroPlayDTO {

    private Long id;
    private String name;
    private int level;
    private boolean levelUP = false;
    private int baseHealth;
    private int baseDamage;
    private int health;
    private int damage;
    private int experience;
    private HeroType heroType;
    private String villainType;
    private int villainLevel;
    private Artifact artifact;
    private Villain villain;
    private LinkedHashSet<Artifact> artifacts;
    private boolean isDead = false;
    private boolean keepOrDrop = false;
    private boolean runOrFight = false;
    private boolean moveOk = true;

    public HeroPlayDTO(Hero hero) {
        this.id = hero.getId();
        this.name = hero.getName();
        this.level = hero.getLevel();
        this.heroType = hero.getHeroType();
        this.baseHealth = getHeroType().getHealth() * level;
        this.baseDamage = getHeroType().getDamage() * level;
        this.artifacts = new LinkedHashSet<>(hero.getArtifacts());
        this.experience = hero.getExperience();
        setStats();
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

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
        moveOk = false;
        keepOrDrop = false;
        runOrFight = false;
        health = 0;
    }

    public LinkedHashSet<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(LinkedHashSet<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isKeepOrDrop() {
        return keepOrDrop;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public boolean isLevelUP() {
        return levelUP;
    }

    public void setLevelUP(boolean levelUP) {
        this.levelUP = levelUP;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public String getVillainType() {
        return villainType;
    }

    public int getVillainLevel() {
        return villainLevel;
    }

    public void setVillainLevel(int villainLevel) {
        this.villainLevel = villainLevel;
    }

    public void setVillainType(String villainType) {
        this.villainType = villainType;
    }

    public void setKeepOrDrop(boolean keepOrDrop) {
        this.keepOrDrop = keepOrDrop;
        if (this.keepOrDrop){
            moveOk = runOrFight = false;
        }
    }

    public boolean isRunOrFight() {
        return runOrFight;
    }

    public void setRunOrFight(boolean runOrFight) {
        this.runOrFight = runOrFight;
        if (this.runOrFight){
            moveOk = keepOrDrop =  false;
        }
    }

    public boolean isMoveOk() {
        return moveOk;
    }

    public void setMoveOk(boolean moveOk) {
        this.moveOk = moveOk;
        if (this.moveOk){
            runOrFight = keepOrDrop = false;
        }
    }

    public Villain getVillain() {
        return villain;
    }

    public void setVillain(Villain villain) {
        this.villain = villain;
    }

    public void setStats(){
        health = baseHealth;
        damage = baseDamage;
        if (artifacts != null) {
            for (Artifact artifact : artifacts) {
                health += artifact.getHealth();
                damage += artifact.getDamage();
            }
        }
    }

    public void setStatsForHeroNewArtifact(){
        health += artifact.getHealth();
        damage += artifact.getDamage();
    }

    @Override
    public String toString() {
        return "HeroPlayDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", health=" + health +
                ", damage=" + damage +
                ", experience=" + experience +
                ", heroType=" + heroType +
                ", artifacts=" + artifacts +
                ", isDead=" + isDead +
                '}';
    }
}
