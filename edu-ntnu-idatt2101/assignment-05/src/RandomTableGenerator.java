import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Generate a list of m integers, in range.
 * Add to HashSet to ensure unique numbers.
 * Pass HashSet to writer-function to write to file.
 */
public class RandomTableGenerator {

  public static void main(String[] args) {
    // Parameters for generating integers
    int m = 20_000_000;
    int bound = Integer.MAX_VALUE;
    int origin = Integer.MIN_VALUE;

    Set<Integer> set = new HashSet<>();
    Random random = new Random();

    while (set.size() < m) {
      int randomInt = random.nextInt(bound);
      set.add(randomInt);
    }

    List<Integer> list = new ArrayList<>(set);
    Collections.shuffle(list);

    writeToFile(list, "uniqueRandomIntegers");
  }

  /**
   * Writes list of unique random ints to file.
   * @param list
   * @param fileName
   */
  public static void writeToFile(List<Integer> list, String fileName) {
    try (PrintWriter writer = new PrintWriter(new File(fileName))) {
      for (Integer integer : list) {
        writer.println(integer);
      }
      System.out.println("Random integers have been written to " + fileName);
    } catch (IOException e) {
      System.out.println("An error occurred while writing to the file.");
      e.printStackTrace();
    }
  }
}
