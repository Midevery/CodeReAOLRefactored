package service;

import domain.Role;
import domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User("admin", "admin123", Role.ADMIN));
        users.add(new User("user", "user123", Role.USER));
    }

    public boolean addUser(String username, String password, Role role) {
        if (findByUsername(username).isPresent()) {
            return false;
        }
        users.add(new User(username, password, role));
        return true;
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public int depositToUser(String username, int amount) {
        Optional<User> optUser = findByUsername(username);

        if (optUser.isEmpty()) {
            return -1;
        }

        if (amount <= 0) {
            return -2;
        }

        User user = optUser.get();
        user.deposit(amount);
        return 0;
    }
}