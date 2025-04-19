import java.util.*;

public final class NewString {

    private final String myString;


    public NewString(String inputString) {
        myString = inputString;
    }


    // Method for shortening string and output first character in each word.
    public NewString shorten(){
        String tempSub = ""; // Initialize tempSub for storing substrings.
        String[] splitString = myString.split(" "); // Split myString by space, and put into an array splitString.
        for(int i = 0; i < splitString.length; i++){
            String substring = splitString[i].substring(0, 1); // Loop through array and extract substring from 0 to, but not including 1. I.e. first character.
            tempSub = tempSub + substring; // Append the substring to the existing tempSub.
        }
        return new NewString(tempSub);
        // System.out.println(tempSub); // Print out the extracted characters.
    }

    public NewString remove(String charRemove){
        String stripString = myString.replace(charRemove, "");
        return new NewString(stripString);
    }

    public void print(){
        System.out.println(myString);
    }


    public static void main(String[] args) {

        NewString testString = new NewString("Potedes og brennevin i messa klokka elleve.");
        testString.print();

        NewString modifiedString = testString.remove("n");
        NewString shortenedString = testString.shorten();
        modifiedString.print();
        shortenedString.print();

    }

}