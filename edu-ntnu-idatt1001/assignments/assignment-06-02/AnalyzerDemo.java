import java.util.Scanner;

public class AnalyzerDemo {
    public static void main(String[] args) {
        while (true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please input the text you wish to have analyzed: ");
            String inputText = scanner.nextLine();
            TextAnalysis textAnalysis = new TextAnalysis(inputText);


            System.out.println("Number of different letters: " + textAnalysis.numUniqueLetters());
            System.out.println("Total number of letters in text: " + textAnalysis.letterCount());
            System.out.println("Percentage of text which is non-letter characters: " + textAnalysis.characterPercent());

            System.out.println("Input the letter you want to check: ");
            String inputLetter = scanner.nextLine();
            System.out.println("Number of occurrences of the letter " + inputLetter + " is " + textAnalysis.specificLetter(inputLetter) );
            System.out.println("The letter(s) occurring most often in the text is: " + textAnalysis.mostUsedLetter() + ". How often it appears is a trade secret.");
            System.out.println("DONE!");
        }
    }
}
// Should implement way to terminate program.
// E.g. inputting 0 should exit.