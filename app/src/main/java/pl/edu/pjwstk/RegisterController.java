package pl.edu.pjwstk;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
@RestController
public class RegisterController {
    final UserService userService;

    public HashMap<String,User>users=new HashMap<String,User>();

    public RegisterController(UserService userService) {
        this.userService = userService;
    }
    public User getUser(String username){
        if(users.containsKey(username)){
            return users.get(username);
        }
        return null;
    }
//nazwa użytkownika jako klucz
    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registerRequest){

        Set<String> authorities=new HashSet<>();
        authorities.add("user");
        userService.saveUser(registerRequest.getUsername(),registerRequest.getPassword(),authorities);//rejestracja użytkownika (metoda zapisuje do bazy danych, potrzebuje username hasło i nadaje uprawnienia)
//if(users.isEmpty()){
//   authorities.add("admin");
// }
//if (users.containsKey(registerRequest.getUsername())){
//   throw new UnauthorizedException();//jeżeli tworze tego samego użytkownika, wyrzuca wyjątek
// }
// todo registerUser
// users.put(registerRequest.getUsername(), new
//        User(registerRequest.getUsername(), registerRequest.getPassword(),authorities));//dodaje użytkownika do mapy
    }

}