import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Student testStud01 = new Student("Ante-Mikkel");
        System.out.println(testStud01);

        Student testStud02 = new Student("Kliff-Arne");
        System.out.println(testStud02);

        Student testStud03 = new Student("Arnt-Hjalmar");
        testStud03.increaseNumbAss(10);
        System.out.println(testStud03);


        userMenu();
    }

    /**
     * User menu for interacting with Student and AssignmentOverview.
     */
    public static void userMenu() {
        AssignmentOverview someOverview = new AssignmentOverview();
        int menuInput = -1;
        while (menuInput != 0) {
            System.out.println("*-- USER MENU --*");
            System.out.println("1: Register new student.");
            System.out.println("2: Show number of registered students.");
            System.out.println("3: Look up student and show number of solved assignments.");
            System.out.println("4: Add solved assignments to student total");
            System.out.println("5: Show student information.");
            System.out.println("6: Print all students.");
            System.out.println("0: Press 0 to exit.");

            Scanner scanner2 = new Scanner(System.in);
            System.out.println("Input the number corresponding to your choice of action.");
            System.out.print("---|: ");
            menuInput = Integer.parseInt(scanner2.nextLine()); // Parsing menu input/nextLine to int
            /*
            menuInput = scanner2.nextInt();
            scanner2.nextLine(); // Workaround to discard new line in pipeline.
             */

            // Add new student
            if (menuInput == 1) {
                System.out.println("Input new student name: ");
                Scanner scanner1 = new Scanner(System.in);
                String inputName = scanner1.nextLine();
                someOverview.addStudent(inputName);
                System.out.println(inputName);
                System.out.println("");

                /*
                Student testStud01 = new Student("Ante-Mikkel");
                testStud01.increaseNumbAss(3);

                Student testStud02 = new Student("Kliff-Arne");
                testStud02.increaseNumbAss(6);

                Student testStud03 = new Student("Arnt-Hjalmar");
                testStud03.increaseNumbAss(10);
                */
            }

                // Number of registered students
            else if (menuInput == 2) {
                System.out.println("Number of registered students: " + someOverview.getNumbStudents()); // Print result
                System.out.println("");

                // Number of solved assignments
            } else if (menuInput == 3) {
                System.out.println("Input student name (firstname, lastname) to show number of solved assignments: ");
                Scanner scanner1 = new Scanner(System.in);
                String inputName = scanner1.nextLine();
                someOverview.numbAssSolvedForStudentX(inputName);

                System.out.println("Student: " + inputName);
                System.out.println("Solved assignments: " + someOverview.numbAssSolvedForStudentX(inputName));
                System.out.println("");

                // Add solved assignments to total
            } else if (menuInput == 4) {
                System.out.println("Add number of finished assignments to student.");
                System.out.println("Input student name: ");
                Scanner scanner1 = new Scanner(System.in);
                String inputName = scanner1.nextLine();
                System.out.println("Input number of assignments to add to student total: ");
                int addFinishedAss = Integer.parseInt(scanner2.nextLine());
                someOverview.incrNumbApprovedAss(inputName, addFinishedAss);
                someOverview.numbAssSolvedForStudentX(inputName);

                System.out.println("");
                System.out.println("");

                // Show student info
            } else if (menuInput == 5) {
                Scanner scanner1 = new Scanner(System.in);
                String inputName = scanner1.nextLine();
                System.out.println("Student: " + someOverview.findStudent(inputName));;
                System.out.println("Assignments: " + someOverview.numbAssSolvedForStudentX(inputName));;
                System.out.println("");

                // Print all students
            } else if (menuInput == 6){
                someOverview.printAllStudents();
                System.out.println("");

                // KTHXBBQ
            } else if (menuInput == 0) {
                System.out.println("BYE BYE");
                System.out.println("");

                // The Answer
            } else if (menuInput == 42) {
                System.out.println("You found the answer to everything!");
                String str = "\uD83D\uDC27";
                String repeated = str.repeat(10) + "\n";
                String repeated2 = repeated.repeat(10);
                System.out.println(repeated2);
                System.out.println("");

                // Bork
            } else {
                System.out.println("ERROR. Please select one of the provided menu options!");
                System.out.println("");
            }
        }
    }
}