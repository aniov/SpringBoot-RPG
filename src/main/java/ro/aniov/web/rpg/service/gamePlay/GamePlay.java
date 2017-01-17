/**
 * Created by Marius on 12/21/2016.
 */
package ro.aniov.web.rpg.service.gamePlay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.aniov.web.rpg.dto.HeroActionType;
import ro.aniov.web.rpg.dto.HeroArtifactAction;
import ro.aniov.web.rpg.dto.HeroMoveType;
import ro.aniov.web.rpg.dto.HeroPlayDTO;
import ro.aniov.web.rpg.model.characters.Villain.Villain;
import ro.aniov.web.rpg.model.characters.Villain.VillainType;
import ro.aniov.web.rpg.model.characters.hero.Hero;
import ro.aniov.web.rpg.model.characters.hero.artifact.Artifact;
import ro.aniov.web.rpg.service.ArtifactService;
import ro.aniov.web.rpg.service.HeroService;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Random;



/**
 * A Service for Game Play
 * Where all the magic action happens
 */

@Service
public class GamePlay {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private HeroService heroService;

    @Autowired
    private ArtifactService artifactService;

    private String control;

    private GameMap gameMap;

    private HeroPlayDTO heroPlayDTO;

    private Point newHeroPosition;

    private VillainType villainType;

    private Villain villain;

    public void play(String control){
        this.control = control;
        initialize();
        checkControl();

    }

    private void initialize(){
        gameMap = (GameMap) httpSession.getAttribute("gameMap");
        heroPlayDTO = (HeroPlayDTO) httpSession.getAttribute("heroPlayDTO");
    }

    private void heroMove(){

        newHeroPosition = new Point();

        newHeroPosition.setLocation(gameMap.getHeroPosition());
        newHeroPosition.x += HeroMoveType.valueOf(control).getX();
        newHeroPosition.y += HeroMoveType.valueOf(control).getY();

        if (moveIsValid()){

            gameMap.setNextHeroPosition(newHeroPosition);

            if (isVillain()){
                heroPlayDTO.setRunOrFight(true);
                heroPlayDTO.setVillainType(villainType.getName());
                /** Generate new Villain conforming to the type*/
                villain = new Villain(villainType, heroPlayDTO.getLevel());
                heroPlayDTO.setVillain(villain);
                return;
            }
            gameMap.setOnMap(newHeroPosition, GameMap.hero);
            gameMap.setOnMap(gameMap.getHeroPosition(), GameMap.passed);
            gameMap.setHeroPosition(newHeroPosition);
        }
        else {
            gameMap.setGameWin(true);
            heroService.setExperience(heroPlayDTO.getId(), heroPlayDTO.getExperience());
        }
    }

    private void heroArtifactAction() {

        if (HeroArtifactAction.valueOf(control) == HeroArtifactAction.KEEP){
            LinkedHashSet<Artifact> artifactSet =  heroPlayDTO.getArtifacts();
            /** We keep the newer artifact*/
            if (artifactSet.contains(heroPlayDTO.getArtifact())){
                artifactSet.remove(heroPlayDTO.getArtifact());
                Artifact artifact = artifactService.geBytArtifactType(heroPlayDTO.getArtifact().getArtifactType());
                if (artifact != null){
                    artifactService.deleteArtifactFromHero(artifact.getId());
                }
            }
            artifactSet.add(heroPlayDTO.getArtifact());
            heroPlayDTO.setArtifacts(artifactSet);
            heroPlayDTO.setStatsForHeroNewArtifact();
            /** Save the new artifact to Hero Inventory*/
            artifactService.addArtifactToHero(new Hero(heroPlayDTO.getId()), heroPlayDTO.getArtifact());
        }
        else  if (HeroArtifactAction.valueOf(control) == HeroArtifactAction.DROP){
        }
        heroPlayDTO.setMoveOk(true);
    }

    private void heroAction() {

        if (HeroActionType.valueOf(control) == HeroActionType.FIGHT) {
           fight();
        }
        else if (HeroActionType.valueOf(control) == HeroActionType.RUN){
            Random random = new Random();
            if (random.nextBoolean()){
                fight();
            }
            else {
                heroPlayDTO.setMoveOk(true);
            }
        }
    }

    private void fight(){

        if (fightIsWon()) {

            gameMap.setOnMap(gameMap.getHeroPosition(), GameMap.passed);
            gameMap.setOnMap(gameMap.getNextHeroPosition(), GameMap.hero);
            gameMap.setHeroPosition(gameMap.getNextHeroPosition());

            heroPlayDTO.setRunOrFight(false);
            heroPlayDTO.setExperience(heroPlayDTO.getExperience() + 20);

            /** 50% chance to generate(drop) new Artifact*/
            Artifact artifact = generateNewArtifact();
            if (artifact == null){
                heroPlayDTO.setMoveOk(true);
            }
            else {
                heroPlayDTO.setArtifact(artifact);
                heroPlayDTO.setKeepOrDrop(true);
            }
            if (heroPlayDTO.getExperience() == 100){
                heroPlayDTO.setExperience(0);
                heroPlayDTO.setLevel(heroPlayDTO.getLevel() + 1);
                heroPlayDTO.setLevelUP(true);
                heroPlayDTO.setStats();

                heroService.setDamage(heroPlayDTO.getId(), heroPlayDTO.getDamage());
                heroService.setHealth(heroPlayDTO.getId(), heroPlayDTO.getHealth());
                heroService.setLevel(heroPlayDTO.getId(), heroPlayDTO.getLevel());
                heroService.setExperience(heroPlayDTO.getId(), heroPlayDTO.getExperience());
            }
        }
        else {
            heroPlayDTO.setDead(true);
        }
    }

    private Artifact generateNewArtifact() {
            return new Artifact(heroPlayDTO.getLevel());
    }

    private boolean fightIsWon() {

        villain = heroPlayDTO.getVillain();
        while (villain.getHealth() > 0 && heroPlayDTO.getHealth() > 0){

            villain.setHealth(villain.getHealth() - heroPlayDTO.getDamage());
            heroPlayDTO.setHealth(heroPlayDTO.getHealth() - villain.getDamage());
        }

        if (villain.getHealth() <= 0 && heroPlayDTO.getHealth() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    private void checkControl(){
        heroPlayDTO.setLevelUP(false);

        if ( !heroPlayDTO.isRunOrFight() && !heroPlayDTO.isKeepOrDrop()) {
            for (HeroMoveType moveType : HeroMoveType.values()) {
                if (moveType.name().equals(control)) {
                    heroMove();
                    return;
                }
            }
        }
        else if (heroPlayDTO.isRunOrFight()) {
            for (HeroActionType actionType : HeroActionType.values()) {
                if (actionType.name().equals(control)) {
                    heroAction();
                    return;
                }
            }
        }
        else if (heroPlayDTO.isKeepOrDrop()) {
            for (HeroArtifactAction heroArtifactAction : HeroArtifactAction.values()) {
                if (heroArtifactAction.name().equals(control)) {
                    /** The fight is over, We set current villain to NULL*/
                    villain = null;

                    heroArtifactAction();
                }
            }
        }
    }

    private boolean moveIsValid(){
        int size = gameMap.getSize();

        if (newHeroPosition.getX() >= 0 && newHeroPosition.getX() < size &&
                newHeroPosition.getY() >= 0 && newHeroPosition.getY() < size)
            return true;
        return false;
    }

    private boolean isVillain(){
        String villain = gameMap.getFromMap(newHeroPosition);
        for (VillainType villainType : VillainType.values()){
            if (villainType.name().toString().equals(villain)){
                this.villainType = villainType;
                return true;
            }
        }
        return false;
    }


}
