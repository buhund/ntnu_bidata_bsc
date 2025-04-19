import java.util.Random;


public class MyRandom {

    private final Random _random;

    public MyRandom() {
        this._random = new Random();
    }

    // Method for generating random integer in interval.
    public int nextInteger(int lower, int upper){ // Intervallet (lower, upper)
        return _random.nextInt(upper - lower) + lower;
    }

    // Method for generating random double in interval.
    public double nextDouble(double lower, double upper){ // Intervallet [0.0, 1.0>
        return (_random.nextDouble() * (upper - lower) + lower);
    }

    public static void main(String[] args) {
        MyRandom RNG = new MyRandom();

        System.out.println("Random int: " + RNG.nextInteger(4,12));
        System.out.println("Random double: " + RNG.nextDouble(2.5,7.5));
    }
}
