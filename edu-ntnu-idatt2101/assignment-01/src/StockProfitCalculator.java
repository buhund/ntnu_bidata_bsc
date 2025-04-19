import java.util.Random;

/**
 * Stock Profit Calculator, task 1
 * @version v1
 */

public class StockProfitCalculator {


  public static void main(String[] args) {
    // Change in price, days 1 through 9
    int[] stockTickerArray = {-1, +3, -9, +2, +2, -1, +2, -1, -5};


    int potentialProfit = 0;
    int buyDay = 0;
    int sellDay = 0;
    int maxProfit = 0;


    int[] runningSumArray = new int[stockTickerArray.length];
    int runningSum = 0;

    // Create array of the running sum of stock value, adding them to the array for their specific days
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

    int sellPrice = runningSumArray[sellDay - 1];
    int buyPrice = runningSumArray[buyDay - 1];

    printResult(buyPrice, sellPrice, maxProfit, buyDay, sellDay);
  }

  static void printResult(int buyPrice, int sellPrice, int maxProfit, int buyDay, int sellDay) {

    String profitReport = (
    "Buy price: " + buyPrice + " (day " + buyDay + ")\n" +
    "Sell price: " + sellPrice + " (day " + sellDay + ")\n" +
    "Maximum profit: " + maxProfit);

    System.out.println(profitReport);

  }

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