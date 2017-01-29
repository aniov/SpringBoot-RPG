package ro.aniov.web.rpg.model;

import ro.aniov.web.rpg.model.characters.hero.Hero;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Marius on 12/8/2016.
 */

/**
 * User Entity
 */
@Entity
@Table(name = "site_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "firstname")
    @Size(min = 3, max = 30)
    private String firstName = "Unset";

    @Size(min = 3, max = 30)
    @Column(name = "lastname")
    private String lastName = "Unset";

    @NotNull
    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private sexType sex = sexType.UNKNOWN;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Hero> heroes;

    public enum sexType {
        MALE("MALE"), FEMALE("FEMALE"), UNKNOWN("UNKNOWN");

        private String name;

        sexType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public User() {
    }

    public User(String firstName, String lastName, sexType sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public sexType getSex() {
        return sex;
    }

    public void setSex(sexType sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (sex != user.sex) return false;
        if (account != null ? !account.equals(user.account) : user.account != null) return false;
        return heroes != null ? heroes.equals(user.heroes) : user.heroes == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (heroes != null ? heroes.hashCode() : 0);
        return result;
    }
}
