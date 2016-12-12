package ro.aniov.web.rpg.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Marius on 12/8/2016.
 */

/**
 * Account Entity
 */

@Entity
@Table(name="account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @NotBlank
    @Size(min = 5, max=50)
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Column(name ="password")
    private String passwordHash;

    @NotNull
    @Column(name = "enabled")
    private boolean enabled = true;

    @NotNull
    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;

    @NotNull
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    @NotNull
    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    @NotNull
    @Column(name = "role")
    private Role role = Role.ROLE_USER;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy/MM/dd hh:mm:ss")
    private Date created;

    @OneToOne(mappedBy = "account")
    private User user;

    @PrePersist
    protected void onCreate(){
        if (created == null)
            created = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
