/**
 * Created by Marius on 12/8/2016.
 */
package ro.aniov.web.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.aniov.web.rpg.model.Account;
import ro.aniov.web.rpg.model.Role;

import java.util.List;

/**
 * Spring Data JPA repository for the Account entity.
 */

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long>{

    Account findById(Long id);
    Account findByEmail(String email);
    List<Account> findByRole(Role role);
    List<Account> findByEmailContaining(String email_part);

    @Modifying
    @Query("UPDATE Account ac SET ac.enabled = :setAcc WHERE ac.id = :id")
    void setEnabled(@Param("id") Long id, @Param("setAcc") boolean setAcc);

    @Modifying
    @Query("UPDATE Account ac SET ac.accountNonExpired = :setAcc WHERE ac.id = :id")
    void setExpired(@Param("id") Long id, @Param("setAcc") boolean setAcc);

    @Modifying
    @Query("UPDATE Account ac SET ac.accountNonLocked = :setAcc WHERE ac.id = :id")
    void setLock(@Param("id") Long id, @Param("setAcc") boolean setAcc);

    @Modifying
    @Query("UPDATE Account ac SET ac.role = :roleType WHERE ac.id = :id")
    void changeRole(@Param("id") Long id, @Param("roleType") Role roleType);
}
