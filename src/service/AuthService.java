package service;

import domain.User;
import java.util.Optional;

public class AuthService {
    private UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User login(String username, String password) {
        Optional<User> optUser = userService.findByUsername(username);
        if (optUser.isPresent()) {
            User user = optUser.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}