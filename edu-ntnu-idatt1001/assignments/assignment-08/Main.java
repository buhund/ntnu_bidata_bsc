import java.util.Scanner;

public class Main {


    Person person01;
    Employee employee01;

    /**
     * Generate person01 and employee01. Then call userManu with employee as parameter.
     * @param args
     */
    public static void main(String[] args) {
        Person person01 = new Person("Kålrabi", "Kåre", 1984);
        Employee employee01 = new Employee(person01, 101337, 1990, 100, 60);

        userMenu(employee01);
    }


    /**
     * Generating a user menu to give perform actions on employee object.
     * @param employee01
     */
    public static void userMenu(Employee employee01){
        int menuInput = -1;
        while (menuInput != 0) {
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Welcome to the employee registry.");
            System.out.println("The alpha client of this software only allows for the operation on one employee object.");
            System.out.println("Input the number corresponding to your choice of action.");
            System.out.println("---------------------------------------------------------------");
            System.out.println("1: Register new employee.");
            System.out.println("2: Select new employee.");
            System.out.println("3: View all registered employee data for current employee.");
            System.out.println("4: Edit salary for current employee.");
            System.out.println("5: Update monthly tax percentage.");
            System.out.println("6: Check years of service with the Company.");
            System.out.println("Press 0 to EXIT.");
            menuInput = Integer.parseInt(scanner1.nextLine()); // Parsing string input to int

            if (menuInput == 1) {
                System.out.println("This functionality has not yet been implemented. Sorry for the inconvenience.");

            } else if (menuInput == 2) {
                System.out.println("This functionality has not yet been implemented. You can only work on employee ID-" + employee01.employeeNum);

            } else if (menuInput == 3) {
                System.out.println(employee01);

            } else if (menuInput == 4) { // Edit salary, and print updated value
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Input new salary for employee ID-" + employee01.employeeNum);
                int newSalary = Integer.parseInt(scanner2.nextLine());
                employee01.setSalaryMonth(newSalary);
                System.out.println("Confirming new salary: " + employee01.getSalaryMonth());
                System.out.println("\n");


            } else if (menuInput == 5) { // Edit tax percentage, and print updated value
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Input new tax percent for employee ID-" + employee01.employeeNum + ". Integer only.");
                int newTaxPct = Integer.parseInt(scanner2.nextLine());
                employee01.setTaxPercent(newTaxPct);
                System.out.println("Confirming new tax percent: " + (employee01.getTaxPercent() * 100));
                System.out.println("\n");

            } else if (menuInput == 6) { // Check if employed for more than X years
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Input number of years you wish to check against: ");
                int checkMoreThan = Integer.parseInt(scanner2.nextLine());
                if (employee01.employedMoreThanXYears(checkMoreThan)) {
                    System.out.println("Employee *has* been with the company for more than " + checkMoreThan + " years.");
                } else {
                    System.out.println("Employee has *not* been with the company for more than " + checkMoreThan + " years.");
                }

            } else if (menuInput == 0) {
                System.out.println("kthxbb");

            } else {
                System.out.println("Invalid menu choice. Please try again.");
            }
        }
    }
}