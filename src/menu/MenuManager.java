package menu;

import domain.Role;
import domain.User;
import service.InventoryService;
import service.UserService;
import util.InputUtil;

import java.util.Scanner;

public class MenuManager {
    private final Scanner scanner;
    private final InputUtil inputUtil;
    private final InventoryService inventoryService;
    private final UserService userService;

    public MenuManager(Scanner scanner, InputUtil inputUtil, InventoryService inventoryService, UserService userService) {
        this.scanner = scanner;
        this.inputUtil = inputUtil;
        this.inventoryService = inventoryService;
        this.userService = userService;
    }

    public void showMenu(User user) {
        if (user.getRole() == Role.ADMIN) handleMenuLoop(user, Role.ADMIN);
        else handleMenuLoop(user, Role.USER);
    }

    private void handleMenuLoop(User user, Role role) {
        while (true) {
            showMenuOptions(role);
            System.out.print("Choose: ");
            int choice = inputUtil.readInt();

            if (!handleMenuChoice(choice, user, role)) {
                break; // logout
            }
        }
    }

    private void showMenuOptions(Role role) {
        if (role == Role.ADMIN) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add Item");
            System.out.println("2. View Items");
            System.out.println("3. Add User");
            System.out.println("4. View Users");
            System.out.println("5. Deposit");
            System.out.println("6. Logout");
        } else {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View Items");
            System.out.println("2. Buy Item");
            System.out.println("3. View Balance");
            System.out.println("4. Logout");
        }
    }

    private boolean handleMenuChoice(int choice, User user, Role role) {
        if (role == Role.ADMIN) {
            return handleAdminChoice(choice, user);
        } else {
            return handleUserChoice(choice, user);
        }
    }

    private boolean handleAdminChoice(int choice, User user) {
        switch (choice) {
            case 1 -> addItem();
            case 2 -> viewItems();
            case 3 -> addUser();
            case 4 -> viewUsers();
            case 5 -> depositToUser();
            case 6 -> {
                return false; // Logout
            }
            default -> System.out.println("Invalid option.");
        }
        return true;
    }

    private boolean handleUserChoice(int choice, User user) {
        switch (choice) {
            case 1 -> viewItems();
            case 2 -> buyItem(user);
            case 3 -> System.out.println("Balance: " + user.getBalance());
            case 4 -> {
                return false; // Logout
            }
            default -> System.out.println("Invalid option.");
        }
        return true;
    }

    private void addItem() {
        System.out.print("Item name: ");
        String name = scanner.nextLine();
        System.out.print("Quantity: ");
        int qty = inputUtil.readInt();
        System.out.print("Category: ");
        String cat = scanner.nextLine();
        System.out.print("Price: ");
        int price = inputUtil.readInt();

        inventoryService.addItem(name, qty, cat, price);
        System.out.println("Item added successfully.");
    }

    private void addUser() {
        System.out.print("Username: "); 
        String uname = scanner.nextLine(); 
        System.out.print("Password: "); 
        String pass = scanner.nextLine(); 
        System.out.print("Role (ADMIN/USER): "); 
        String roleStr = scanner.nextLine(); 

        try {
            Role role = Role.valueOf(roleStr.trim().toUpperCase());
            if (userService.addUser(uname, pass, role)) {
                System.out.println("User added successfully.");
            } else {
                System.out.println("User '" + uname + "' already exists.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role. Please enter either 'ADMIN' or 'USER'.");
        }
    }

    private void viewUsers() {
        for (User u : userService.getAllUsers()) {
            System.out.printf("%s (%s) - Balance: %d\n", u.getUsername(), u.getRole(), u.getBalance());
        }
    }

    private void depositToUser() {
        System.out.print("Username: "); 
        String uname = scanner.nextLine(); 
        System.out.print("Amount: "); 
        int amount = inputUtil.readInt(); 

        int depositStatus = userService.depositToUser(uname, amount);
        switch (depositStatus) {
            case 0 -> System.out.println("Deposit successful.");
            case -1 -> System.out.println("User not found.");
            case -2 -> System.out.println("Deposit amount must be positive.");
            default -> System.out.println("An unknown error occurred during deposit.");
        }
    }

    private void buyItem(User user) {
        System.out.print("Item name: ");
        String name = scanner.nextLine();
        System.out.print("Quantity: ");
        int qty = inputUtil.readInt();

        if (inventoryService.buyItem(name, qty, user)) {
            System.out.println("Purchase successful.");
        } else {
            System.out.println("Purchase failed. Check balance or item availability.");
        }
    }
    
    public void viewItems() {
        for (String line : inventoryService.getItemDisplayList()) {
            System.out.println(line);
        }
    }

}
