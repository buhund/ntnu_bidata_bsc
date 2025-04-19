import java.util.*;
public class WordProcessor {

    // Instance field.
    private String textString;

    // Constructor method
    public WordProcessor(String inputWords) {
        textString = inputWords;
    }

    // Method to find number of words

    /**
     *
     * @return
     */
    public int wordCount() {
        return textString.split(" ").length;
    }

    // Method for finding average word length.
    public double avgWordLen() {
        double letterCounter = 0; // Initialize counter
        String stripString = textString.replaceAll("[^A-Åa-å ]", ""); // Remove everything except letters and space.
        String[] wordArray = stripString.split(" ");
        for (String s : wordArray) letterCounter += s.length(); // for-each loop

        /* Old loop
        for(int i = 0; i < wordArray.length; i++) {
            letterCounter += wordArray[i].length();
        } */

        double avgLength = letterCounter / wordArray.length;
        return Math.round(avgLength);
    }

    // Method for average number of words per sentence.
    public int avgWordsPerSentence() {

        // String[] sentenceArray = textString.split("[!?.:;]+"); // Split stripString by separator, and put into an array.
        // int avgWords = wordsTotal / sentenceArray.length;

        int words = textString.split("[!?.:;]+").length; // .split makes an array of the words, then find the length of the array. Then put it into var words.
        return wordCount() / words;

        //System.out.println("Number of sentences: " + sentenceArray.length);
        //System.out.println("Average words per sentence: " + avgWords);
    }

    public void replaceWord(String toReplace, String replaceWith) {
        textString = textString.replaceAll(toReplace, replaceWith);
        //System.out.println("Replaced all instances of '" + toReplace + "' with '" + replaceWith + "'.");
        //System.out.println("The sentence now reads as follows: ");
        //System.out.println(editedSentence);
    }

    public void print() {
        System.out.println(textString);
    }

    public String getUpperCaseText() {
        return textString.toUpperCase();
        //System.out.println("Converted the text to all upper case letters: ");
        //System.out.println(upperText);
    }

    public String getTextString() {
        return textString;
    }
}
