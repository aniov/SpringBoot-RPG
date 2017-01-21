/**
 * Created by Marius on 12/14/2016.
 */
package ro.aniov.web.rpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.aniov.web.rpg.dto.HeroDTO;
import ro.aniov.web.rpg.model.User;
import ro.aniov.web.rpg.model.characters.hero.Hero;
import ro.aniov.web.rpg.model.characters.hero.HeroType;
import ro.aniov.web.rpg.repository.HeroRepository;

import java.util.List;

@Service
public class HeroService {

    @Autowired
    HeroRepository heroRepository;

    @Autowired
    ArtifactService artifactService;

    @Autowired
    UserService userService;


    public List<Hero> getHeroesByUserId(Long id){
        return heroRepository.findByUserIdOrderByIdAsc(id);
    }

    public void createNewHero(HeroDTO heroDTO){

        User user = userService.getUserFromContext();

        /**  Transform from String to Enum type */
        HeroType heroType = HeroType.valueOf(heroDTO.getHeroType());
        Hero hero = new Hero(heroDTO.getName(), heroType, user);
        heroRepository.saveAndFlush(hero);
    }

    public void deleteHero(Long id) {
        heroRepository.delete(id);
        heroRepository.flush();
    }

    public void editName(Long id, String newname) {
        heroRepository.updateHeroName(id, newname);
        heroRepository.flush();
    }

    public void setHealth(Long id, int health){
        heroRepository.updateHeroHealth(id, health);
        heroRepository.flush();
    }

    public void setDamage(Long id, int damage){
        heroRepository.updateHeroDamage(id, damage);
        heroRepository.flush();
    }

    public void setLevel(Long id, int level){
        heroRepository.updateHeroLevel(id, level);
        heroRepository.flush();
    }

    public void setExperience(Long id, int experience){
        heroRepository.updateHeroExperience(id, experience);
        heroRepository.flush();
    }

    public int getHeroLevel(Long id){

        Hero hero = heroRepository.findById(id);
        return hero.getLevel();
    }

    public Hero getHeroById(Long id){
        return heroRepository.findById(id);
    }

    /** If hero id doesn't exists or it doesn't belong to the current user we return false */
    public boolean isHeroIdBoundToAuthorizedUser(Long heroId){
        User user = userService.getUserFromContext();
        Hero hero = heroRepository.findById(heroId);

        return  hero != null && user.getId() == hero.getUser().getId();
    }

    public void saveHero(Hero hero) {
        heroRepository.saveAndFlush(hero);
    }
}
