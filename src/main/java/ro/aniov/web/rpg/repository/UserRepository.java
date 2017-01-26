/**
 * Created by Marius on 12/8/2016.
 */
package ro.aniov.web.rpg.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.aniov.web.rpg.model.User;

/**
 * Spring Data JPA repository for the User entity.
 */

public interface UserRepository extends JpaRepository<User, Long> {

    User findByAccountEmail(String email);
    Page<User> findByFirstNameContaining(String userName, Pageable pageable);
    Page<User> findAll(Pageable pageable);

    User findById(Long id);

}
