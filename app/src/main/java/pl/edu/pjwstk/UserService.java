package pl.edu.pjwstk;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.Entity.UserEntity;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;


@Repository
public class UserService {
    private final EntityManager em;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();//obiekt statyczny do kodowania hasła

    public UserService(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void saveUser(String username, String password, Set<String> authorities) {
        var userEntity = new UserEntity();

        userEntity.setUsername(username);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setAuthorities(authorities);

        if (getUsers().isEmpty() ) {
            authorities.add("admin");
        }
        em.persist(userEntity);

    }//metoda zapisująca użytkownika

    public UserEntity findByUsername(String username) {
        return em.createQuery("SELECT user FROM UserEntity user WHERE user.username =: username", UserEntity.class)
                .setParameter("username", username) //username!!!
                .getSingleResult();
    }//wyszukiwanie użytkownika z bazy danych

    public Boolean passwordChecking(String rawPassword,String encodePassword){
        return passwordEncoder.matches(rawPassword,encodePassword);//sprawdzanie hasła
    }
    public List<UserEntity> getUsers(){
        return em.createQuery("select user FROM UserEntity user",UserEntity.class).getResultList();
    }
}