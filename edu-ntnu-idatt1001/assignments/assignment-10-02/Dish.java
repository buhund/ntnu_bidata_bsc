/**
 * @author iverik
 * @version 1.0.0
 */
public class Dish {

    private final String name;
    private final String type;
    private final String recipe;
    private double price; // May want to change prices at some point.

    // Constructur for Dish.

    /**
     * Need JavaDoc on constructor and class parameters.
     * @param name
     * @param type
     * @param recipe
     * @param price
     */
    public Dish(String name, String type, String recipe, double price) {
        this.name = name;
        this.type = type;
        this.recipe = recipe;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRecipe() {
        return recipe;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String basicDishInfo(){
        return "Name: " + this.name + ", price: " + this.price;
    }

    public String toString(){
        return "Name: " + this.name + "\n" +
                "Type: " + this.type + "\n" +
                "Price: " + this.price + "\n" +
                "Recipe: " + this.recipe + "\n";
    }



}
