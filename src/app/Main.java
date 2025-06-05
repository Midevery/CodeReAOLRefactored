package app;

import domain.User;
import menu.MenuManager;
import service.AuthService;
import service.InventoryService;
import service.UserService;
import util.InputUtil;

import java.util.Scanner;

import Repository.ItemRepository;

public class Main {
    private final Scanner scanner;
    private final InputUtil inputUtil;
    private final AuthService authService;
    private final MenuManager menuManager;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.inputUtil = new InputUtil(scanner);
        UserService userService = new UserService();
        this.authService = new AuthService(userService);
        ItemRepository itemRepository = new ItemRepository();
        InventoryService inventoryService = new InventoryService(itemRepository);
        this.menuManager = new MenuManager(scanner, inputUtil, inventoryService, userService);
    }

    public void run() {
        System.out.println("=== INVENTORY SYSTEM ===");

        while (true) {
            System.out.println("\n1. Login\n2. Exit");
            System.out.print("Choose: ");
            int opt = inputUtil.readInt();

            switch (opt) {
                case 1 -> handleLogin();
                case 2 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void handleLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        User user = authService.login(username, password);

        if (user != null) {
            System.out.println("Login successful as " + user.getRole());
            menuManager.showMenu(user);
        } else {
            System.out.println("Login failed.");
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
