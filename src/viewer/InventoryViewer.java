package viewer;

import domain.Item;
import java.util.List;

public class InventoryViewer {
    public void displayItems(List<Item> items) {
        if (items.isEmpty()) {
            System.out.println("No items.");
            return;
        }
        for (Item i : items) {
            System.out.printf("%s - %d pcs - %s - Price: %d\n", 
                i.getName(), i.getQuantity(), i.getCategory(), i.getPrice());
        }
    }
}
