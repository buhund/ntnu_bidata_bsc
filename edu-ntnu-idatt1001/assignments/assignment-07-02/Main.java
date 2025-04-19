import java.util.Scanner;

public class Main {

    // Main method.
    public static void main(String[] args) {
        textInput(); // Invoke textInput method

    }

    public static void textInput() {
        System.out.print("Input text: ");
        Scanner scanner1 = new Scanner(System.in);
        String input = scanner1.nextLine();
        WordProcessor userText = new WordProcessor(input); // Create a new WP object called userText with input text as argument.
        userMenu(userText);
    }

    public static void userMenu(WordProcessor userText) {
        int menuInput = -1;
        while (menuInput != 0) {
            System.out.println("What do you wish to do with the text. ");
            System.out.println("Input the number corresponding to your choice of action.");
            System.out.println("1: Show number of words.");
            System.out.println("2: Show average word length.");
            System.out.println("3: Show average number of words per sentence.");
            System.out.println("4: I LOVE STATS! SHOW 'EM ALL!<3");
            System.out.println("5: Replace all instances of a word with another word and print the new text.");
            System.out.println("6: Print the text.");
            System.out.println("7: Convert the text to upper case and print.");
            System.out.println("8: Restart and input new text.");
            System.out.println("0: Press 0 to exit.");

            Scanner scanner2 = new Scanner(System.in);
            System.out.println("Input the number corresponding to your choice of action.");
            menuInput = Integer.parseInt(scanner2.nextLine()); // Parsing string input to int. Better workaround.
            /*
            menuInput = scanner2.nextInt();
            scanner2.nextLine(); // Workaround to discard new line in pipeline.
             */


            if (menuInput == 1) {
                System.out.println("Number of words: " + userText.wordCount());
                System.out.println("");

            } else if (menuInput == 2) {
                System.out.println("Avg. word length, rounded to nearest integer: " + userText.avgWordLen()); // Print result
                System.out.println("");

            } else if (menuInput == 3) {
                System.out.println("Average number of words per sentence: " + userText.avgWordsPerSentence());
                System.out.println("");

            } else if (menuInput == 4) { // ALL the stats!
                System.out.println("Number of words: " + userText.wordCount());
                System.out.println("Avg. word length, rounded to nearest integer: " + userText.avgWordLen()); // Print result
                System.out.println("Average number of words per sentence: " + userText.avgWordsPerSentence());
                System.out.println("");

            } else if (menuInput == 5) {
                Scanner scanner3 = new Scanner(System.in);
                System.out.println("Enter the word you want to replace: ");
                String toReplaceSc = scanner3.nextLine();
                System.out.println("Enter the word you want to put in: ");
                String replaceWithSc = scanner3.nextLine();
                userText.replaceWord(toReplaceSc, replaceWithSc);
                System.out.println("");

            } else if (menuInput == 6) {
                userText.print();
                System.out.println("");

            } else if (menuInput == 7) {
                System.out.println(userText.getUpperCaseText());
                System.out.println("");

            } else if (menuInput == 8) { // Restart
                System.out.print("Input text: ");
                String input = scanner2.nextLine();
                userText = new WordProcessor(input); // Replace old userText object with new userText object.
                System.out.println("");

            } else if (menuInput == 0) {
                System.out.println("BYE BYE");

            } else if (menuInput == 42) {
                System.out.println("You found the answer to everything!");
                System.out.println("");

            } else {
                System.out.println("ERROR. Please select one of the provided menu options!");
                System.out.println("");
            }
        }
    }
}
