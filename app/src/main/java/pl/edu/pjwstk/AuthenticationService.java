package pl.edu.pjwstk;

import pl.edu.pjwstk.Entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    final UserSession userSession;
    final RegisterController registerController;
    final UserService userService;

    public AuthenticationService(UserSession userSession, RegisterController registerController, UserService userService) {
        this.userSession = userSession;
        this.registerController = registerController;
        this.userService = userService;
    }

    public boolean login(String username, String password){
    //User registerUser=registerController.getUser(username);
        UserEntity user = userService.findByUsername(username);
    if(userService.passwordChecking(password,user.getPassword())){
        userSession.logIn();
        SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(user));
        return true;
    }
    return false;
}
}
