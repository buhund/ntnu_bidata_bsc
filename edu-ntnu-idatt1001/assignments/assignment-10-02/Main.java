/**
 * @author iverik
 * @version 1.0.0
 */

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public Scanner scanner1 = new Scanner(System.in);
    public MenuRegister menuRegister = new MenuRegister();


    public static void main(String[] args) {
//        UserMenu userMenu = new UserMenu();
//        UserMenu.userMenu();
        Main mainClient = new Main();
        mainClient.userMenu();
    }


    public void userMenu() {
        int menuInput = -1;
        while (menuInput != 0) {
        System.out.println("---------------------");
        System.out.println("*---- USER MENU ----*");
        System.out.println("---------------------");
        System.out.println("1: Register new dish.");
        System.out.println("2: Find dish by name.");
        System.out.println("3: Find dishes by type.");
        System.out.println("4: Create and register a new menu, using existing dishes.");
        System.out.println("5: Find menus in price range.");
        System.out.println("6: Print all dishes.");
        System.out.println("7: Print all menus.");
        System.out.println("8: Add Test Data");
        System.out.println("0: Press 0 to exit.");

        Scanner scanner1 = new Scanner(System.in);
        menuInput = Integer.parseInt(scanner1.nextLine());
            switch (menuInput) {
                case 1 -> addNewDish();
                case 2 -> findDishByName();
                case 3 -> findDishByType();
                case 4 -> addNewMenu();
                case 5 -> findMenuInPriceRange();
                case 6 -> printAllDishes();
                case 7 -> printAllMenus();
                case 8 -> populateTestData();
                case 0 -> System.out.println("EXIT. BYE BYE.");
                default -> System.out.println("ERROR. Please select a valid menu option!");
            }
        }
    }

    private void addNewDish() {

        System.out.println("Input dish name: ");
        String name = scanner1.nextLine();

        if (menuRegister.findDishName(name) == null) {
            System.out.println("Input dish type: ");
            String type = scanner1.nextLine();

            System.out.println("Input recipe/ingredients: ");
            String recipe = scanner1.nextLine();

            System.out.println("Input dish price: ");
            double price = scanner1.nextDouble();
            scanner1.nextLine(); // Gobble up newline in pipeline

            Dish newDish = menuRegister.registerNewDish(name, type, recipe, price);
            System.out.println(newDish.toString());

        } else {
            System.out.println("A dish by that name already exists. Please use your imagination!");
        }
    }

/*    public void findDishByName() {
        System.out.println("Input name: ");
        String findName = scanner1.nextLine();
        ArrayList<Dish> dishes = menuRegister.findDishName(findName); // Var to work on dish list.
        if(dishes.size() > 0) {
            for (int i = 0; i < dishes.size(); i++){
                System.out.println(dishes.get(i).toString());
            }
        } else {
            System.out.println("The dish \"" + findName + "\" is not in database.");
        }
    }*/

    public void findDishByName() {
        System.out.println("Input name: ");
        String findName = scanner1.nextLine();
        if(menuRegister.findDishName(findName) == null){
            System.out.println("The dish \"" + findName + "\" is not in database.");
        } else {
            System.out.println(menuRegister.findDishName(findName).toString());
        }
    }

    /**
     *
     */
    public void findDishByType() {
        System.out.println("Input type: ");
        String findType = scanner1.nextLine();
        ArrayList<Dish> dishes = menuRegister.findDishType(findType); // Var to work on dish list.
        if(dishes.size() > 0) {
            for (int i = 0; i < dishes.size(); i++){
                System.out.println(dishes.get(i).toString());
            }
        } else {
            System.out.println("There are no \"" + findType + "\" in the database.");
        }
    }

    /**
     * Adding new menu by system input.
     * Incrementing a dishes-counter (numDishes) each time the loop has run.
     * Allows for adding 3 dishes to a menu.
     * Checking that the required number of dishes (3) are available.
     * Adds the foundDish list to a newMenu list.
     * Exits the while-loop at 3 dishes.
     * Adds the newMenu to the menuList.
     */
    public void addNewMenu() {
        Menu newMenu = new Menu();

        System.out.println("Input three dishes you wish to have in your menu.");

        int numDishes = 0;
        int maxDishes = Math.min(menuRegister.dishList.size(), 3); // max = size or 3, whichever is less
        // Better juju
        while(numDishes < maxDishes) {
            System.out.println("Input dish name: ");

            String dish1 = scanner1.nextLine();

            Dish foundDish = menuRegister.findDishName(dish1);
            if (foundDish == null) {
                System.out.println("The dish \"" + dish1 + "\" is not in database. Please add it, or find another.");
                continue; // Returns to if, if the if-condition failed.
            } else {
                newMenu.add(foundDish);
                numDishes++;
            }
        }
        menuRegister.getMenuList().add(newMenu);
        System.out.println("\n" + "Menu composition: " + "\n");
        System.out.println(newMenu.toString());

        // Bad juju - Data may change from the first check to the second check.
/*        if(menuRegister.findDishName(dish1) == null){
            System.out.println("The dish \"" + dish1 + "\" is not in database. Please add it, or find another.");
        } else {
            someDishes.add((menuRegister.findDishName(dish1)))
        }
*/
    }

    public void findMenuInPriceRange () {
        System.out.println("Input the price range.");
        System.out.println("Lower limit: ");
        double priceLow = scanner1.nextDouble();
        System.out.println("Upper limit: ");
        double priceHigh = scanner1.nextDouble();
        ArrayList<Menu> affordableMenus = menuRegister.searchByMenuPrice(priceLow, priceHigh);
        menuRegister.printMenuListShort(affordableMenus);
    }

    /**
     * Print all dishes, by iterating over the dishList ArrayList.
     */
    public void printAllDishes(){
        for(int i = 0; i < menuRegister.getDishList().size(); i++){
            System.out.println(menuRegister.getDishList().get(i));
        }
    }

    public void printAllMenus(){
/*        for(int i = 0; i < menuRegister.getMenuList().size(); i++){
            System.out.println(menuRegister.getMenuList().get(i));;
        }*/
        ArrayList<Menu> menus;
        menus = menuRegister.menuList;
        menuRegister.printMenuListShort(menus);
    }

    public void populateTestData(){
        Dish appet1 = menuRegister.registerNewDish("Saltsticks", "Appetizer", "Find sticks, add salt.", 15);
        Dish appet2 = menuRegister.registerNewDish("Norwegian Brødskalk", "Appetizer", "Bread", 20);
        Dish appet3 = menuRegister.registerNewDish("Olive bread", "Appetizer", "Bread and olives and olive oil.", 45 );
        Dish appet4 = menuRegister.registerNewDish("Old Norwegian Brødskalk", "Appetizer", "Old bread.", 20);

        Dish main1 = menuRegister.registerNewDish("Haggis", "Main", "Sheep's intestines, onion, potatoes.", 210);
        Dish main2 = menuRegister.registerNewDish("Smalahove", "Main", "Sheep's head, onion, potatoes.", 240);
        Dish main3 = menuRegister.registerNewDish("Knekkebrød", "Main", "Scandinavian knekkebrød with brunost.", 310);
        Dish main4 = menuRegister.registerNewDish("Lapskaus", "Main", "Lots of veggies and some meat.", 280);

        Dish dess1 = menuRegister.registerNewDish("Sjokoladekake", "Dessert", "Kakao", 40);
        Dish dess2 = menuRegister.registerNewDish("Kvæfjordkake", "Dessert", "Secret", 240);
        Dish dess3 = menuRegister.registerNewDish("Lefse", "Dessert", "Cinnamon, butter and sugar.", 120);
        Dish dess4 = menuRegister.registerNewDish("Vaffel", "Dessert", "Add 1 egg", 20);

        ArrayList<Menu> testMenu1 = new ArrayList<>();
        testMenu1.add(menuRegister.registerNewMenu(appet1.getName(), main1.getName(), dess1.getName()));
        testMenu1.add(menuRegister.registerNewMenu(appet2.getName(), main2.getName(), dess2.getName()));
        testMenu1.add(menuRegister.registerNewMenu(appet3.getName(), main3.getName(), dess3.getName()));
        testMenu1.add(menuRegister.registerNewMenu(appet4.getName(), main4.getName(), dess4.getName()));

    }
}