import java.util.Arrays;

class HashPerformance {
    private final int[] arr;
    private int collision;

    // Initialize the array to the smallest power of 2 that is greater than or equal to length.
    public HashPerformance(int length) {
        arr = new int[(int) Math.pow(2, Math.ceil(Math.log(length) / Math.log(2)))];
        collision = 0;
    }

    // Inserts a value into the hash table based on a given hashing strategy.
    public void push(int x, HashingStrategy strategy) {
        int h1 = multiHash(x);
        if (arr[h1] == 0) {
            arr[h1] = x;
        } else {
            int counter = 1;
            while (counter < arr.length) {
                int h2 = strategy.getNextIndex(arr, h1, x, counter);
                if (arr[h2] == 0) {
                    arr[h2] = x;
                    break;
                } else {
                    counter++;
                    collision++;
                }
            }
        }
    }

    // Returns the index of the value if it exists in the hash table.
    private int multiHash(int k) {
        double A = k * ((Math.sqrt(5.0) - 1.0) / 2.0);
        A -= (int) A;
        return (int) (arr.length * Math.abs(A)) % arr.length;
    }

    // Sets up an experiment to test the performance of two the hashing strategies on a given range of values.
    public static void main(String[] args) {
        int range = 100_000_000;
        HashPerformance ht = new HashPerformance(range);
        HashingStrategy[] strategies = {new LinearProbing(), new DoubleHashing()};
        int[] percentages = {50, 80, 90, 99, 100};

        // Markdown table formatting print
        System.out.println("| Strategy | Percentage | Collisions | Time(ms)|");
        System.out.println("| -------- | ---------- | ---------- | ------- |");

        // Start timing
        long startTime = System.currentTimeMillis();

        for (HashingStrategy strategy : strategies) {
            for (int percentage : percentages) {
                int insertCount = (int) (range * (percentage / 100.0));
                ht.reset();
                for (int i = 0; i < insertCount; i++) {
                    int random = (int) (Math.random() * range * 10);
                    ht.push(random, strategy);
                }
                // End timing
                long endTime = System.currentTimeMillis();

//                System.out.println("Strategy: " + strategy.getClass().getSimpleName() + ", Percentage: " + percentage + "%");
//                System.out.println("Collisions: " + ht.collision);

                long runtime = endTime - startTime;
//                System.out.println("Time: " + runtime + " ms");

                // Markdown table formatting print
                System.out.println("| " + strategy.getClass().getSimpleName() + " | " + percentage + "%" + " | " + ht.collision + " | " + runtime + " ms |");

            }
        }
    }

    // Resets the hash table and collision count between different hashing strategies.
    public void reset() {
        Arrays.fill(arr, 0);
        collision = 0;
    }
}
