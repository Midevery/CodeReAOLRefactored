package service;

import domain.Role;
import domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User("admin", "admin123", Role.ADMIN));
        users.add(new User("user", "user123", Role.USER));
    }

    public void addUser(String username, String password, Role role) {
        if (findByUsername(username) != null) {
            System.out.println("User already exists.");
            return;
        }
        users.add(new User(username, password, role));
        System.out.println("User added.");
    }

    public User findByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }

    public List<User> getAllUsers() {
        return users;
    }

    public boolean depositToUser(String username, int amount) {
        User user = findByUsername(username);
        if (user == null) {
            System.out.println("User not found.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return false;
        }
        user.deposit(amount);
        System.out.println("Deposited " + amount);
        return true;
    }
}
