package service;

import domain.Item;
import domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryService {
    private final List<Item> items = new ArrayList<>();

    public InventoryService() {
        seedInitialItems();
    }

    private void seedInitialItems() {
        addItem("Laptop", 5, "Electronics", 15000);
        addItem("Mouse", 10, "Electronics", 1500);
        addItem("Buku Tulis", 20, "Stationery", 5000);
    }

    public void addItem(String name, int quantity, String category, int price) {
        items.add(new Item(name, quantity, category, price));
    }

    public Optional<Item> findByName(String name) {
        return items.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<Item> getItems() {
        return new ArrayList<>(items); // Return a copy to prevent external modification
    }

    public boolean buyItem(String name, int quantity, User user) {
        Optional<Item> optItem = findByName(name);

        if (optItem.isEmpty()) {
            return false;
        }

        Item item = optItem.get();
        int totalCost = item.getPrice() * quantity;

        if (quantity <= 0 || item.getQuantity() < quantity || user.getBalance() < totalCost) {
            return false;
        }

        item.setQuantity(item.getQuantity() - quantity);
        user.deductBalance(totalCost);
        return true;
    }

    public List<String> getItemDisplayList() {
        if (items.isEmpty()) {
            return List.of("No items available.");
        }

        List<String> displayList = new ArrayList<>();
        for (Item i : items) {
            displayList.add(String.format("%s - %d pcs - %s - %d", i.getName(), i.getQuantity(), i.getCategory(), i.getPrice()));
        }
        return displayList;
    }
}
