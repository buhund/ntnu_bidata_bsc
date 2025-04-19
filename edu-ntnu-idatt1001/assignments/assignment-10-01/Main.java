import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Testing testing, 1, 2, 3.
        EventRegister events = new EventRegister();
//        events.newEvent(1001, 1605_11_05_2350L, "Parliament", "Fireworks", "Guy Fawkes");
//        events.newEvent(1002, 1918_11_11_1100L, "Verdun", "Lasting Peace", "Great Britain");
//        events.newEvent(1004, 1918_11_10_1330L, "Verdun", "Bombardment", "Great Britain");
//        events.newEvent(1003, 1989_19_11_1853L, "Berlin", "Mauerfall", "Europe");
//        System.out.println(events.sortByDateAndTime());
        userMenu();


    }

    public static void userMenu() {
        EventRegister events = new EventRegister();
        int menuInput = -1;
        while (menuInput != 0) {
            System.out.println("*-- USER MENU --*");
            System.out.println("1: Register new event.");
            System.out.println("2: Populate with test data.");
            System.out.println("2: Find all event by location.");
            System.out.println("3: Find all event by date.");
            System.out.println("4: Find all event by in time interval.");
            System.out.println("5: Generate list of events, sorted by Location");
            System.out.println("6: Generate list of events, sorted by Type");
            System.out.println("7: Generate list of events, sorted by Date and Time");
            System.out.println("0: Press 0 to exit.");

            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Input the number corresponding to your choice of action.");
            System.out.print("---|: ");
            menuInput = Integer.parseInt(scanner1.nextLine()); // Parsing menu input/nextLine to int


            // Add new student
            if (menuInput == 1) {

                System.out.println("Please input event details: ");
                System.out.println("-----------------------------------");
                Scanner scanner2 = new Scanner(System.in);

                System.out.println("Input event ID, four digits (nnnn): ");
                int eventID = scanner2.nextInt();
                // scanner2.nextLine(); // Workaround to discard new line in pipeline.

                System.out.println("Input event date in the format yyyy_MM_dd_HH_mm: ");
                long date = scanner2.nextLong();
                scanner2.nextLine(); // Workaround to discard new line in pipeline.

                System.out.println("Input event location: ");
                String location = scanner2.nextLine();
                // scanner2.nextLine(); // Workaround to discard new line in pipeline.

                System.out.println("Input event type: ");
                String type = scanner2.nextLine();
                // scanner2.nextLine(); // Workaround to discard new line in pipeline.

                System.out.println("Input event organiser: ");
                String organiser = scanner2.nextLine();
                // scanner2.nextLine(); // Workaround to discard new line in pipeline.

                events.newEvent(eventID, date, location, type, organiser);
                System.out.println(events);
                System.out.println("");


            } else if (menuInput == 2) { // Test data
                events.newEvent(1001, 1605_11_05_2350L, "Parliament", "Fireworks", "Guy Fawkes");
                events.newEvent(1002, 1918_11_11_1100L, "Verdun", "Lasting Peace", "Great Britain");
                events.newEvent(1003, 1989_19_11_1853L, "Berlin", "Mauerfall", "Europe");
                events.newEvent(1004, 2022_06_01_1330L, "Vardø", "VM i snøballkasting", "Vardø snøballklubb");


            } else if (menuInput == 3) { // Find by location
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Input location: "); // Print result
                String location = scanner2.nextLine();
                System.out.println(events.findByLocation(location));


            } else if (menuInput == 4) { // Find by date
                System.out.println("Input a date you wish to check: ");
                Scanner scanner2 = new Scanner(System.in);
                long date = scanner1.nextLong();
                events.findByDate(date);
                System.out.println("Events found at date/time " + date + ": ");
                System.out.println(events);
                System.out.println("");


            } else if (menuInput == 5) { // Find by time interval
                System.out.println("Input time interval to search: ");
                System.out.println("From date: ");

                Scanner scanner2 = new Scanner(System.in);
                long dateLow = scanner1.nextLong();

                System.out.println("To date: ");
                long dateHigh = scanner1.nextLong();
                System.out.println(events.findByTimeInterval(dateLow, dateHigh));
                System.out.println("");


            } else if (menuInput == 6) { // Generate list, sorting by location
                System.out.println(events.sortByLocation());
                System.out.println("");


            } else if (menuInput == 7){ // Generate list, sorting by type
                System.out.println(events.sortByType());
                System.out.println("");


            } else if (menuInput == 8){ // Generate list, sorting by date and time
                System.out.println(events.sortByDateAndTime());
                System.out.println("");


            } else if (menuInput == 0) { // KKTHXBBQ
                System.out.println("BYE BYE");
                System.out.println("");


            } else { // Bork
                System.out.println("ERROR. Please select a valid menu options!");
                System.out.println("");
            }
        }

    }
}
