import java.util.Random;

public class LoopRandom {

    public static void main(String[] args) {

        Random random = new Random();

        int[] array = new int[10]; // Create array with 10 elements.
        for(int i=0; i<10; i++){ // Looper through array indices. Set value to 0. Increment index by 1.
            array[i]=0; // At Index i, set value to 0.
        }

        // Generate numbers and store in array.
        for(int i=0; i <= 10000; i++){
            int n = random.nextInt(10);
            array[n]++; // At index n (random), increment Value by 1.
        }

        System.out.println("~~~~~ Random Number Generator ~~~~~");

        for (int i=0; i < 10; i++) {
            System.out.println(i + " was generated a total of " + array[i] + " times.");
        }
        System.out.println("** RNG Inc. **");
    }
}

/*
E   | i | V
1   | 0 | 0
2   | 1 | 0
3   | 2 | 0
4   | 3 | 0
5   | 4 | 0
6   | 5 | 0
n+1 | n | ++
*/