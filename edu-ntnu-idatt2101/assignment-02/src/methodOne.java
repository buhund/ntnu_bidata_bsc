import java.util.Locale;

public class methodOne {

    public static void main(String[] args) {
        int n = 20; // Integer
        double x = 9.5; // Decimal
        long startTime = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            multiplication(n, x);
        }
        long endTime = System.nanoTime();
        long runTime = endTime - startTime;
        System.out.println(multiplication(n, x));
        System.out.println("Runetime test for Method 1.\n" +
            "n = " + n + "\n" +
            "x = " + x + "\n" +
            "Run time (ns): " + String.format(new Locale("nb", "NO"), "%,d", runTime) + " nanoseconds.");

        // Format nanoseconds to milliseconds and 4 decimal places
        double elapsedAvgTimeMillis = (double) runTime / 1_000_000; // Convert nanoseconds to milliseconds
        String formattedAvgElapsedTime = String.format("%.4f", elapsedAvgTimeMillis);
        System.out.println("Run time (ms): " + formattedAvgElapsedTime + " milliseconds");
    }

    public static double multiplication(int n, double x) {
        if (n > 1) {
            return (x + multiplication(n - 1, x));
        } else {
            return x;
        }
    }
}
