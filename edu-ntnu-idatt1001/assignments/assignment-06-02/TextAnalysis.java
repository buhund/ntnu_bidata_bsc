public class TextAnalysis {

    // Instance field?
    int[] charCount = new int[30]; // Create single column array with 30 elements (0 trough 29).

    // Constructor
    public TextAnalysis(String text) {

        for(int i=0; i<29; i++) { // Loop through indices in charCount. Set value equal 0. Increment index i by 1.
            charCount[i] = 0;
        }

        // Save all characters and letters in an array.
        // Subtract 97 from currentChar to place is at the correct index/"cell", [0, 29].
        // char 97 = a, char 122 = z
        text = text.toLowerCase(); // Convert entire string to lower case
        for(int i = 0; i < text.length(); i++){ // Increment i to go through text. Bound is from 0 to 1 less than length b.c. zero-index.
            char currentChar = text.charAt(i); // Set currentChar to the current value at place "i" in text.
            if (currentChar >= 97 && currentChar <= 122){ // Check a through z
                charCount[currentChar - 97] ++;
            } else if (currentChar == 230) { // Check æ. Increment 26 if found.
                charCount[26] ++;
            } else if (currentChar == 248) { // Check ø. Increment 27 if found.
                charCount[27] ++;
            } else if (currentChar == 229) { // Check å. Increment 28 if found.
                charCount[28] ++;
            } else {
                charCount[29] ++; // Heap everything else (characters) in bucket 29.
            }
        }
    }
    // Check number of letters. I.e. everything NOT at index 29.
    public int numUniqueLetters(){
        int numLetters = 0;
        for(int i = 0; i < 29; i++) { // Check values for all "cells"/indices less than 29.
            if (charCount[i] != 0) {
                numLetters ++ ;
            }
         }
        return numLetters;
    }

    // Find total number of letters in text.
    public int letterCount(){
        int letters = 0;
        for(int i = 0; i < 29; i++){
            letters = charCount[i] + letters;
        }
        return letters;
    }

    // Find what percentage the non-letter characters is out of the entire string.
    public double characterPercent(){
        int characters = charCount[29];
        double charactersPercent = (characters / ( (double) letterCount() + characters) ) * 100;
        // X is what percent of Y = (X/Y)*100
        return charactersPercent;
        // Ev. avrunde:
        // return Math.round(charactersPercent * 100.0) / 100.0;
    }

    // Find occurrences of a specified letter, inputLetter.
    public int specificLetter(String inputLetter){
        inputLetter = inputLetter.toLowerCase();
        char inputChar = inputLetter.charAt(0);

        if (inputChar >= 97 && inputChar <= 122){ // Check a through z
            return charCount[inputChar - 97];
        } else if (inputChar == 230) { // Check æ
            return charCount[26];
        } else if (inputChar == 248) { // Check ø
            return charCount[27];
        } else if (inputChar == 229) { // Check å
            return charCount[28];
        } else {
            return 0;
        }
    }

    // Find the most often occurring letter(s).
    public String mostUsedLetter(){
        int mostValue = 0;
        String mostCharIDString = "";
        for(int i = 0; i < 29; i++){
            if(charCount[i] > mostValue){
                mostValue = charCount[i];
                mostCharIDString = "" + (char)(i+97);
            } else if (charCount[i] == mostValue) {
                mostCharIDString = mostCharIDString + ", " + (char)(i+97);
            }
        } return mostCharIDString;
    }
}


/*
a 97
z 122
230 æ
248 ø
229 å

// delta 32

 */