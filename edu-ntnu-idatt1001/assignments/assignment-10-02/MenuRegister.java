/**
 * @author iverik
 * @version 1.0.0
 */

import java.util.ArrayList;

public class MenuRegister {

    /**
     * The "main" list of menus.
     * This is where menus (each consisting of 3 dishes) are stored.
     */
    public ArrayList<Menu> menuList = new ArrayList<>();

    /**
     * The "main" list of dishes.
     * This is where dishes are stored.
     */
    public ArrayList<Dish> dishList = new ArrayList<>();
    public ArrayList<Menu> getMenuList() {
        return menuList;
    }

    public ArrayList<Dish> getDishList() {
        return dishList;
    }

    /**
     * Method for registering a new dish.
     * Adds the newDish to the main Dish List (dishList).
     * @param name
     * @param type
     * @param recipe
     * @param price
     * @return
     */
    public Dish registerNewDish(String name, String type, String recipe, double price){
        Dish newDish = new Dish(name, type, recipe, price);
        dishList.add(newDish);
        return newDish;
    }

    // Register new menu of dishes
    public Menu newMenu(ArrayList<String> dishNames){
        Menu newMenu = new Menu();
        for (String dishName : dishNames) {
            newMenu.add(findDishName(dishName));
        }
        menuList.add(newMenu);
        return newMenu;
    }

    /**
     * Method used for registering new test menu.
     * Menus are built by supplying tree dishes:
     * appetizer ("forrett"), main course ("hovedrett") and dessert ("dessert").
     * @param appetizer
     * @param main
     * @param dessert
     * @return
     */
    public Menu registerNewMenu(String appetizer, String main, String dessert){
        Dish appetizer1 = findDishName(appetizer);
        Dish main1 = findDishName(main);
        Dish dessert1 = findDishName(dessert);
        Menu newMenu = new Menu();
        newMenu.add(appetizer1);
        newMenu.add(main1);
        newMenu.add(dessert1);
        getMenuList().add(newMenu);

        return newMenu;

        //Dish someDish = new Dish(name, type, recipe, price)
        //newMenu.add(someDish);
        // Menu newMenu = new Menu();
        // Menu menu1 = menuOverview.get(0);
        // Dish dish1 = menu1.get(0);

        // menuOverview.add(newMenu);
    }

    /**
     * Then iterate over the dishOverview list.
     * Comparing the elements of the ArrayList with @param.
     * Since dish name is unique, we only need to find the one.
     * Return either the name found at list index i, or null if no matches.
     * Case is ignored.
     * @param name
     * @return getDishList().get(i) or null
     */
    public Dish findDishName(String name){
        // ArrayList<Dish> listOfNames = new ArrayList<>();
        for(int i = 0; i < getDishList().size(); i++){
            if(getDishList().get(i).getName().equalsIgnoreCase(name)){
                return getDishList().get(i);
            }
        }
    return null;
}

    /**
     * Create new ArrayList. Then iterate over the dishOverview list.
     * Comparing the elements of the lists with @param.
     * Adding the findings to the new ArrayList.
     * @param type
     * @return listOfType
     */
    public ArrayList<Dish> findDishType(String type){
        ArrayList<Dish> listOfType = new ArrayList<>();
        for(int i = 0; i < getDishList().size(); i++){
            if(getDishList().get(i).getType().equalsIgnoreCase(type)){
                listOfType.add(getDishList().get(i));
            }
        }
        return listOfType;
    }


    public ArrayList<Menu> searchByMenuPrice(double priceLow, double priceHigh){
        ArrayList<Menu> listOfPrice = new ArrayList<>();
        for(int i = 0; i < getMenuList().size(); i++){
            if((getMenuList().get(i).getMenuPrice() >= priceLow) && (getMenuList().get(i).getMenuPrice() <= priceHigh)){
                listOfPrice.add(getMenuList().get(i));
            } //System.out.println(listOfPrice.get(i).toString());
        }
        return listOfPrice;
    }

    public void printMenuListShort(ArrayList<Menu> menus){
        for(int i = 0; i < menus.size(); i++){
            System.out.println();
            System.out.println("Contents of menu " + i+1 + ": "); // Bump i by 1 in print.
            ArrayList<Dish> currentMenu = menus.get(i).getMenuOfDishes();
               for(Dish dish: currentMenu){
                System.out.println(dish.getName() + ", Price: " + dish.getPrice());
            }
            System.out.println("Total: " + menus.get(i).getMenuPrice());
            System.out.println();
        }
    }
}
