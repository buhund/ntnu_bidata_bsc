import java.util.ArrayList;

public class Menu {
/**
 * @author iverik
 * @version 1.0.0
 */

    /**
     * ArrayList holding data type Dish.
     * Used to construct a Menu of several Dishes.
     */
    private final ArrayList<Dish> menuOfDishes;

    // Constructor
    public Menu(ArrayList<Dish> menuOfDishes) {
        this.menuOfDishes = menuOfDishes;
    }
    public Menu() {
        menuOfDishes =  new ArrayList<>();
    }

    // Getter for menu
    public ArrayList<Dish> getMenuOfDishes() {
        return menuOfDishes;
    }

    public void add(Dish dish){
        menuOfDishes.add(dish);
    }

    public Dish get(int index) {
        return menuOfDishes.get(index);
    }

    /**
     * Calculate price of entire menu by iterating through the list.
     * Initialize price at 0.
     * Get menu, then iterate through the indices and using getPrice.
     * Add price from the "current" menu to the price var, which tallies the total price of the dishes in the menu list.
     * Note to self: size is to a list, what length is to an array.
     * @return
     */
    public double getMenuPrice(){
        double price = 0;
        for(int i = 0; i < getMenuOfDishes().size(); i++){
            price = price + getMenuOfDishes().get(i).getPrice();
        }
    return price;
    }


    /**
     * toString method for a menu, including total menu price.
     * @return
     */
    public String toString(){
        String printMenu = "";
        for(int i = 0; i < getMenuOfDishes().size(); i++){
            printMenu += getMenuOfDishes().get(i).toString() + "\n";
        }
        printMenu = printMenu + "\n" + "Menu total price: " + getMenuPrice();
        return printMenu;
    }

}

