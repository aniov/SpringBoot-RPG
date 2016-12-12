package ro.aniov.web.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.aniov.web.rpg.model.User;

/**
 * Created by Marius on 12/8/2016.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
