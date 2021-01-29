package pl.edu.pjwstk.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {// obiekt odpowiadający tabeli SQL

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)//klucz inkrementuje się sam
    private Long id;

    @Column(unique = true)//nazwa użytkownika musi być unikatowa
    private String username;

    private String password;

    @ElementCollection
    @CollectionTable(name = "authorities",joinColumns = @JoinColumn(name = "id"))
    @Column(name = "authority")
    private Set<String> authorities = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}