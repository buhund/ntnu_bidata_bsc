import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

class parenthesesMatcher {
    private final Stack<Character> stack;

    public parenthesesMatcher() {
        stack = new Stack<>();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void push(char c) {
        stack.push(c);
    }

    public char pop() {
        return isEmpty() ? '\0' : stack.pop();
    }

    public char peek() {
        return isEmpty() ? '\0' : stack.peek();
    }

    public static boolean isValidClosing(parenthesesMatcher stack, char closingChar) {
        char openingChar = stack.peek();

        if ((openingChar == '{' && closingChar == '}') ||
                (openingChar == '(' && closingChar == ')') ||
                (openingChar == '[' && closingChar == ']')) {
            stack.pop();
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File("./JosefusComplexity.java")))) {
            String line;
            parenthesesMatcher stack = new parenthesesMatcher();
            while ((line = br.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    if ("{([".indexOf(c) != -1) {
                        stack.push(c);
                    } else if ("})]".indexOf(c) != -1) {
                        if (!isValidClosing(stack, c)) {
                            System.out.println("Mismatched brackets");
                            return;
                        }
                    }
                }
            }
            if (stack.isEmpty()) {
                System.out.println("All brackets matched");
            } else {
                System.out.println("Mismatched brackets");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }
}
