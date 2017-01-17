/**
 * Created by Marius on 12/13/2016.
 */
package ro.aniov.web.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.aniov.web.rpg.model.characters.hero.Hero;

import java.util.List;


@Repository
@Transactional
public interface HeroRepository extends JpaRepository<Hero, Long> {

    List<Hero> findByUserIdOrderByIdAsc(Long id);

    @Modifying
    @Query("UPDATE Hero h SET h.name = :newname WHERE h.id = :id")
    int updateHeroName(@Param("id") Long id, @Param("newname") String newname);

    @Modifying
    @Query("UPDATE Hero h SET h.health = :newhealth WHERE h.id = :id")
    int updateHeroHealth(@Param("id") Long id, @Param("newhealth") int newhealth);

    @Modifying
    @Query("UPDATE Hero h SET h.damage = :newdamage WHERE h.id = :id")
    int updateHeroDamage(@Param("id") Long id, @Param("newdamage") int newdamage);

    @Modifying
    @Query("UPDATE Hero h SET h.level = :newlevel WHERE h.id = :id")
    int updateHeroLevel(@Param("id") Long id, @Param("newlevel") int newlevel);

    @Modifying
    @Query("UPDATE Hero h SET h.experience = :newexperience WHERE h.id = :id")
    int updateHeroExperience(@Param("id") Long id, @Param("newexperience") int newexperience);

    Hero findById(Long id);

}
