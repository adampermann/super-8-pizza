package ordering;

/**
 * Created by adampermann on 10/15/16.
 */
public class MenuOption {
    private String name = "Menu option";
    private double price = 0;
    private int quantity = 0;


    public MenuOption(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public MenuOption() {
    }
}
