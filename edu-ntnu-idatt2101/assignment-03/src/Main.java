import java.util.Locale;
import java.util.Random;

public class Main {
  // Threshold for switching to insertSort (For task 1 in assignment)
  private static final int THRESHOLD = 70;

  public static void main(String[] args) {

    int arrayElements = 100;
    long numberArraysToSort = 10;
    long startTime = 0;
    long endTime = 0;
    long runtimeAccumulator = 0;

    for (int i = 0; i < numberArraysToSort; i++) {
      int[] array = randomIntArray(arrayElements);

      System.out.println("*** NEW ARRAY ***\n" + "Before sorting: ");
      printArray(array);

      startTime = System.nanoTime();
      quicksort(array, 0, array.length - 1);
      endTime = System.nanoTime();


      System.out.println("After sorting: ");
      printArray(array);

      System.out.println("Verifying sorting: ");
      verifyIsSorted(array);

      long runTime = endTime - startTime;
      runtimeAccumulator = runtimeAccumulator + runTime;
      System.out.println("Run time: " + String.format(new Locale("nb", "NO"), "%,d", runTime) + " nanoseconds.\n");
    }

    long avgRuntime = runtimeAccumulator / numberArraysToSort;
    double avgRuntimeMillis = (double) avgRuntime / 1_000_000; // Convert nanoseconds to milliseconds

    System.out.println("*** Timing report ***\n" +
        "Arrays sorted: " + String.format(new Locale("nb", "NO"), "%,d", numberArraysToSort) + "\n" +
        "Elements in array: " + String.format(new Locale("nb", "NO"), "%,d", arrayElements) + "\n" +
        "Total runtime: " + String.format(new Locale("nb", "NO"), "%,d", runtimeAccumulator) + " nanoseconds\n" +
        "Average time to sort 1 array: " + String.format(new Locale("nb", "NO"), "%,f", avgRuntimeMillis) + " milliseconds.");
  }

  // Method for finding the median of three elements
  private static int median3sort(int[] array, int start, int end) {
    int middle = (start + end) / 2;
    if (array[start] > array[middle])
      swap(array, start, middle);
    if (array[middle] > array[end]) {
      swap(array, middle, end);
      if (array[start] > array[middle])
        swap(array, start, end);
    }
    return middle;
  }

  // Method for sorting the array
  public static void quicksort(int[] array, int start, int end) {
    if (end - start >= THRESHOLD) {
      int pivot = split(array, start, end);
      quicksort(array, start, pivot - 1);
      quicksort(array, pivot + 1, end);
    } else {
      // Use insertSort for smaller arrays
      insertSort(array, start, end);
    }
  }

  // Method for splitting the array
  private static int split(int[] array, int start, int end) {
    int iStart, iEnd;
    int middle = median3sort(array, start, end);
    int middleValue = array[middle];
    swap(array, middle, end - 1);
    iStart = start;
    iEnd = end - 1;

    while (iStart < iEnd) {
      while (iStart < iEnd && array[iStart] <= middleValue) iStart++;
      while (iStart < iEnd && array[iEnd] >= middleValue) iEnd--;
      if (iStart < iEnd) {
        swap(array, iStart, iEnd);
      }
    }

    swap(array, iStart, end - 1); // Restore pivot
    return iStart;
  }


  //  Method for sorting small arrays
  public static void insertSort(int[] array, int start, int end) {
    for (int j = start + 1; j <= end; j++) {
      int temp = array[j];
      int i = j - 1;
      while (i >= start && array[i] > temp) {
        array[i + 1] = array[i];
        i--;
      }
      array[i + 1] = temp;
    }
  }

  // Method for generating random arrays
  static int[] randomIntArray(int arrayElements) {
    int n = arrayElements;
    int[] randomArray = new int[n];
    Random random = new Random();
    for (int i = 0; i < randomArray.length; i++) {
      randomArray[i] = random.nextInt(-10, 10);
    }
    return randomArray;
  }

  // Method for printing arrays and checksums
  public static void printArray(int[] array) {
    int checkSum = 0;
    for(int i : array) {
      checkSum += i; // Sum of Array numerals
      System.out.print(i + " ");
    }
    System.out.println("\n" + "Checksum: " + checkSum);
    System.out.println();
  }

  // Method for swapping two elements in an array
  private static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  public static void verifyIsSorted(int[] array) {
    boolean isSorted = true; // Assuming the array has been sorted correctly.
    for (int i = 1; i < array.length; i++) {
      if (array[i] < array[i - 1]) {
        isSorted = false;
        break; // Exit if some wrongly placed int is found.
      }
    }
    if (isSorted) {
      System.out.println("Pass! Array is sorted in ascending order.\n");
    } else {
      System.out.println("Fail! The array is NOT sorted in ascending order. Please check your algorithm.\n");
    }
  }

}
