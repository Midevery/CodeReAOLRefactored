package Repository;

import java.util.ArrayList;
import java.util.List;
import domain.Item;

public class ItemRepository {
    private List<Item> items = new ArrayList<>();

    public ItemRepository() {
        items.add(new Item("Laptop", 5, "Electronics", 15000));
        items.add(new Item("Mouse", 10, "Electronics", 1500));
        items.add(new Item("Buku Tulis", 20, "Stationery", 5000));
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item findItemByName(String name) {
        return items.stream()
            .filter(i -> i.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    public List<Item> getItems() {
        return items;
    }
}
