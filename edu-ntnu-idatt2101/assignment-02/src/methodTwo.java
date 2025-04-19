import java.util.Locale;

public class methodTwo {

    public static void main(String[] args) {
        int n = 11; // Integer
        double x = 9.5; // Decimal
        long startTime = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            multiplicationBitwise(n, x);
        }
        long endTime = System.nanoTime();
        long runTime = endTime - startTime;
        System.out.println(multiplicationBitwise(n, x));
        System.out.println("Runetime test for Method 2.\n" +
            "Run time (ns): " + String.format(new Locale("nb", "NO"), "%,d", runTime) + " nanoseconds.");

        // Format nanoseconds to milliseconds and 4 decimal places
        double elapsedAvgTimeMillis = (double) runTime / 1_000_000; // Convert nanoseconds to milliseconds
        String formattedAvgElapsedTime = String.format("%.4f", elapsedAvgTimeMillis);
        System.out.println("Run time (ms): " + formattedAvgElapsedTime + " milliseconds");
    }

    public static double multiplicationModulo(double n, double x) {
        if (n == 1) {
            return x;

        } else if (n % 2 == 0) {
            double halfResult = multiplicationModulo(x, n / 2);
            return halfResult + halfResult;

        } else {
            double halfResult = multiplicationModulo(x, (n-1) / 2);
            return x + halfResult + halfResult;
        }

    }

    public static double multiplicationBitwise(int n, double x) {

        if (n == 1) return x;

        if ((n & 1) == 0) return multiplicationBitwise(n/2, x+x);
        return x + multiplicationBitwise((n-1)/2, x+x);

    }
}
