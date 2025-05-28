package service;

import domain.User;

public class AuthService {
    private UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User login(String username, String password) {
        User user = userService.findByUsername(username);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }
}
