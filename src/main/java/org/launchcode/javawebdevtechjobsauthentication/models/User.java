package org.launchcode.javawebdevtechjobsauthentication.models;

import com.sun.istack.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity {

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }
    public interface UserRepository extends CrudRepository<User, Integer> {

        User findByUsername(String username);

    }



    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public String getUsername() {
        return username;

    }

}