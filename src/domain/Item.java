package domain;

public class Item {
    private String name;
    private int quantity;
    private String category;
    private int price;

    public Item(String name, int quantity, String category, int price) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public String getCategory() { return category; }
    public int getPrice() { return price; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
