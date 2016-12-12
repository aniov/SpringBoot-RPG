package ro.aniov.web.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.aniov.web.rpg.model.Account;

/**
 * Created by Marius on 12/8/2016.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    Account findById(Long id);
    Account findByEmail(String email);
}
