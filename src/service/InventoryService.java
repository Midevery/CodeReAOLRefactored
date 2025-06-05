package service;

import java.util.ArrayList;
import java.util.List;

import Repository.ItemRepository;
import domain.Item;
import domain.User;
import viewer.InventoryViewer;


public class InventoryService {
    private ItemRepository itemRepository;

    public InventoryService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void addItem(String name, int quantity, String category, int price) {
        Item item = new Item(name, quantity, category, price);
        itemRepository.addItem(item);
        System.out.println("Item added.");
    }

    public boolean buyItem(String name, int quantity, User user) {
        Item item = itemRepository.findItemByName(name);
        if (item == null) {
            System.out.println("Item not found.");
            return false;
        }
        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return false;
        }
        if (item.getQuantity() < quantity) {
            System.out.println("Insufficient stock.");
            return false;
        }
        int totalPrice = item.getPrice() * quantity;
        if (user.getBalance() < totalPrice) {
            System.out.println("Insufficient balance.");
            return false;
        }
        item.setQuantity(item.getQuantity() - quantity);
        user.deductBalance(totalPrice);
        System.out.println("Purchase successful. Total price: " + totalPrice);
        return true;
    }

    public Item findItemByName(String name) {
        return itemRepository.findItemByName(name);
    }

    public void viewItems() {
        InventoryViewer viewer = new InventoryViewer();
        viewer.displayItems(itemRepository.getItems());
    }
    
    public List<String> getItemDisplayList() {
        List<String> displayList = new ArrayList<>();
        
        if (itemRepository.getItems().isEmpty()) {
            displayList.add("No items.");
        } else {
            for (Item i : itemRepository.getItems()) {
                displayList.add(String.format(
                    "%s - %d pcs - %s - Price: %d",
                    i.getName(), i.getQuantity(), i.getCategory(), i.getPrice()
                ));
            }
        }
        
        return displayList;
    }

}
