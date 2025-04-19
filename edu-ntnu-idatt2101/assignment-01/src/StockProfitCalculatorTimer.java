import java.util.Random;

public class StockProfitCalculatorTimer {


  public static void main(String[] args) {

    // Original exercise array.
    int[] stockTickerArray = {-1, +3, -9, +2, +2, -1, +2, -1, -5};

    // Configuration for random testing array.
    int elementsInRandomArray = 4_000;
    long runtimeAccumulator = 0;
    long numberOfRuns = 100;


    // Loop to calculate runtime based on an average of n runs.
    for (int i = 0; i < numberOfRuns; i++) {
      int[] stockPriceArray = randomIntArray(elementsInRandomArray);
      long runtime = stockPriceAlgorithmTimeAnalyzer(stockPriceArray);
      runtimeAccumulator = runtimeAccumulator + runtime;
    }

    long avgRuntime = runtimeAccumulator / numberOfRuns;

    double elapsedAvgTimeMillis = (double) avgRuntime / 1_000_000; // Convert nanoseconds to milliseconds

    // Format milliseconds to 4 decimal places
    String formattedAvgElapsedTime = String.format("%.4f", elapsedAvgTimeMillis);

    // Formatted printout of timing operation.
    System.out.println("Total average runtime in Milliseconds: " + formattedAvgElapsedTime + "\n" +
        "Total average runtime in Nanoseconds: " + (avgRuntime) + "\n" +
        "Number of runs: " + numberOfRuns + "\n" +
        "Elements in random array: " + elementsInRandomArray
    );
  }

  static long stockPriceAlgorithmTimeAnalyzer(int[] stockTickerArray) {
    if (stockTickerArray.length <= 1) return 0;

    int potentialProfit = 0;
    int buyDay = 0;
    int sellDay = 0;
    int maxProfit = 0;


    // Start timing
    long startTime = System.nanoTime();

    int[] runningSumArray = new int[stockTickerArray.length];
    int runningSum = 0;

    for (int s = 0; s < stockTickerArray.length; s++) {
      runningSum = runningSum + stockTickerArray[s];
      runningSumArray[s] = runningSum;
    }

    for (int i = 0; i < runningSumArray.length - 1; i++) {
      for (int j = i + 1; j < runningSumArray.length; j++) {
        potentialProfit = runningSumArray[j] - runningSumArray[i];
        if (potentialProfit > maxProfit) {
          maxProfit = potentialProfit;
          buyDay = i + 1; // +1 to convert index to day.
          sellDay = j + 1; // +1 to convert index to day.
        }
      }
    }

    long endTime = System.nanoTime();
    // End timing

    long totalRuntime = (endTime - startTime);

    // Find buy and sell price.
    int buyPrice = stockTickerArray[buyDay - 1];
    int sellPrice = stockTickerArray[sellDay - 1];


    return totalRuntime;
  }


  /**
   * Array for testing algorithm speed.
   * Creates an array of n random numbers, in the range (including) -10 to 10.
   * @return int[] randomArray
   */
  static int[] randomIntArray(int arrayElements) {
    int n = arrayElements;
    int[] randomArray = new int[n];
    Random random = new Random();
    for (int i = 0; i < randomArray.length; i++) {
      randomArray[i] = random.nextInt(-10, 10);
    }

    return randomArray;
  }


}