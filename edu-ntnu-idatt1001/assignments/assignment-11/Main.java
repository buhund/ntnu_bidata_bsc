/**
 * @author iverik
 * @version 0.0.1
 *
 * Composition. Because a Property, once created, exists inside the PropertyRegister.
 *
 * To do (Anna):
 * - Catch exception for invalid inputs (negative integers) in addProperty.
 * - Combine searching from deleteProperty and searchForPropertyID into a single method --> getSomeProperty
 * -- Use this method (getSomeProperty) to do deleteProperty and the search/print property.
 */

import java.util.*;

public class Main {

    /**
     * Implements default constructor.
     * public PropertyRegister(){}
     * Should probably be inside main method instead. But this will make it harder to access it inside methods. Look into this!
     */
    public PropertyRegister propertyRegister = new PropertyRegister();


    public static void main(String[] args) {
        Main mainClient = new Main();
        mainClient.userMenu();

    }

    /**
     * User menu.
     * Switch based.
     */
    public void userMenu() {

        int menuInput = -1;
        while (menuInput != 0) {
            System.out.println("---------------------");
            System.out.println("*---- USER MENU ----*");
            System.out.println("---------------------");
            System.out.println("1: Register new property.");
            System.out.println("2: Find property by municipality number, lot number and section number.");
            System.out.println("3: Find all info by Lot number.");
            System.out.println("4: Print number of properties in register.");
            System.out.println("5: Calculate average property area.");
            System.out.println("6: Print all properties (full info).");
            System.out.println("7: Print all properties (short info).");
            System.out.println("19: Delete property from register.");
            System.out.println("9: Add Test Data.");
            System.out.println("0: Press 0 to exit.");

            Scanner scanner1 = new Scanner(System.in);
            menuInput = Integer.parseInt(scanner1.nextLine());
            switch (menuInput) {
                // Forske litt på denne måte å skrive Switch på.
                case 1 -> addNewProperty();
                case 2 -> findPropertyByID();
                case 3 -> findAllAtLotNumber();
                case 4 -> printNumberOfProperties();
                case 5 -> calculateAveragePropertyArea();
                case 6 -> printFullPropertyRegister();
                case 7 -> printShortPropertyRegister();
                case 19 -> deleteProperty();
                case 9 -> testData();
                case 0 -> System.out.println("EXIT. BYE BYE.");
                default -> System.out.println("ERROR. Please select a valid menu option!");
            }
        }
    }

    /**
     * Add new property to register.
     * try-catch block to check and verify valid inputs.
     */
    public void addNewProperty(){
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Input property info, as specified.");
        System.out.println("All numbers must be positive.");

        try{
            System.out.println("Input Municipality Number, in range 101 to 5054: ");
            int municipalityNumber = Integer.parseInt(scanner1.nextLine());
            // scanner1.nextLine() // Gobble up newline in pipeline.

            System.out.println("Input Municipality Name: ");
            String municipalityName = scanner1.nextLine();

            System.out.println("Input lot number: ");
            int lotNumber = Integer.parseInt(scanner1.nextLine());

            System.out.println("Input Section Number: ");
            int sectionNumber = Integer.parseInt(scanner1.nextLine());

            System.out.println("Input Section Name: ");
            String sectionName = scanner1.nextLine();

            System.out.println("Input Lot Area (square meters): ");
            int lotArea = Integer.parseInt(scanner1.nextLine());

            System.out.println("Input Owner Name: ");
            String ownerName = scanner1.nextLine();

            // This property is living INSIDE MAIN! That's not good.
            // Instead, do the method call, drop the toString, and print a "done" message.
            Property newProperty = propertyRegister.registerNewProperty(municipalityNumber, municipalityName, lotNumber, sectionNumber, sectionName, lotArea, ownerName);

            System.out.println("Confirming property info: ");
            System.out.println(newProperty.toString());

            } catch (Exception exception){
            System.out.println("Exception occurred. Please check your inputs.");
            System.out.println(exception.getMessage());
            }
        //finally {}
        }

    /**
     * Search for property by ID.
     * ID = municipality number, lot number and section number.
     */
    public void findPropertyByID(){ // Er dette samtidig nok? Eller skal dem inn på én linje?
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Search by Municipality number, Lot number, Section number.");
        System.out.println("Input municipality number, four digits (nnnn): ");
        int munNum = scanner1.nextInt();
        System.out.println("Input lot number: ");
        int lotNum = scanner1.nextInt();
        System.out.println("Input section number: ");
        int secNum = scanner1.nextInt();

        Property property = propertyRegister.getSomeProperty(munNum, lotNum, secNum);
        if(property == null){
            System.out.println("That property does not exist. Please try again.");
        } else {
            System.out.println(property);
        }

    }


    /**
     * Find and get all properties, i.e. sections/sub-properties at a given Lot number.
     */
    public void findAllAtLotNumber(){
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Input Lot Number");
        System.out.println("Input Lot number, two digits (nn): ");
        int lotNum = scanner1.nextInt();
        System.out.println(propertyRegister.searchForAllAtLotNumber(lotNum).toString());

    }

    /**
     * Print out the number of current properties registered.
     */
    public void printNumberOfProperties(){
        System.out.println("Number of properties in register: " + propertyRegister.getRegisterOfProperties().size());
    }

    /**
     * Calculate average property area.
     * Format to two decimal points.
     */
    public void calculateAveragePropertyArea(){
        double avg = propertyRegister.averageLotArea();
        System.out.println("Average lot area : " + String.format("%.2f",avg) + " square meters.");
    }

    /**
     * Print a shortened version of the register.
     * Only munNumb-lotNumb/secNumb.
     * Not yet implemented.
     */
    public void printShortPropertyRegister() {
/*        ArrayList<Property> properties;
        properties = propertyRegister.getRegisterOfProperties();
        propertyRegister.printShortPropertyRegister(properties);
    }
        // MacDerp
    */

        for(int i = 0; i < propertyRegister.getRegisterOfProperties().size(); i++){
            System.out.println("Property number " + i + 1 + ":");
            System.out.println(propertyRegister.getRegisterOfProperties().get(i).toShortString());
        }
    }

    /**
     * Print a full list of the properties in register.
     * Iterating over list to print.
     */
    public void printFullPropertyRegister(){
        for(int i = 0; i < propertyRegister.getRegisterOfProperties().size(); i++){
            System.out.println("Property number " + i + 1 + ":");
            System.out.println(propertyRegister.getRegisterOfProperties().get(i));
        }
    }


    /**
     * Delete property from register.
     * Menu for confirming deletion, with double confirmation.
     */
    public void deleteProperty() {
        int menuInput = -1;
        while (menuInput != 0) {
            System.out.println("Deletion Menu. Continue, or go back?");
            System.out.println("0: No, please take me back to the main menu!.");
            System.out.println("2: Yes, I know what I'm doing.");
            Scanner scanner1 = new Scanner(System.in);
            menuInput = Integer.parseInt(scanner1.nextLine());
            if (menuInput == 2) {
                System.out.println("Input municipality number, four digits (nnnn): ");
                int munNum = Integer.parseInt(scanner1.nextLine());
                System.out.println("Input lot number, two digits (nn): ");
                int lotNum = Integer.parseInt(scanner1.nextLine());
                System.out.println("Input section number, two digits (nn): ");
                int secNum = Integer.parseInt(scanner1.nextLine());

                System.out.println("This will delete the property. Are you really sure?");
                int deleteConfirmation = -1;
                // while (deleteConfirmation != 0) {
                    System.out.println("1: No, please take me back to the main menu!.");
                    System.out.println("2: Yes, I know what I'm doing.");
                    deleteConfirmation = Integer.parseInt(scanner1.nextLine());
                    switch (deleteConfirmation){
                        case 1:
                            System.out.println("Returning to main menu.");
                            System.out.println("");
                            break;
                        case 2:
                            propertyRegister.deleteProperty(munNum, lotNum, secNum);
                            System.out.println("Property deleted.");
                            break;
                        default:
                            System.out.println("ERROR. Please select a valid menu options!");
                            break;
                        }

            } else if (menuInput == 0) { // KKTHXBBQ
                System.out.println("Returning to main menu.");
                System.out.println("");

            } else { // Bork
                System.out.println("ERROR. Please select a valid menu options!");
                System.out.println("");
                }
            }
    }

    /**
     * Test data for client.
     */
    private void testData(){
    propertyRegister.registerNewProperty(1445, "Gloppen", 77, 631, "", 1017.6, "Jens Olsen");
    propertyRegister.registerNewProperty(1445, "Gloppen", 77, 131, "Syningom", 661.3, "Nicolay Madsen");
    propertyRegister.registerNewProperty(1445, "Gloppen", 75, 19, "Fugletun", 650.6, "Evilyn Jensen");
    propertyRegister.registerNewProperty(1445, "Gloppen", 74, 188, "", 1457.2, "Karl Ove Bråten");
    propertyRegister.registerNewProperty(1445, "Gloppen", 69, 47, "Høiberg", 1339.4, "Elsa Indregård");
    //Property badProperty = propertyRegister.registerNewProperty(9040, "Nordkjosbotn", -90, -130, "Flatskogen", -1090, "Ola Dunk");
    }
}