public class DoubleHashing implements HashingStrategy {
    @Override
    public int getNextIndex(int[] arr, int h1, int x, int attempt) {
        return Math.abs(h1 + attempt * modHash(x, arr.length)) % arr.length;
    }

    private int modHash(int k, int arrLength) {
        if (k == Integer.MIN_VALUE) {
            k += 1;
        }
        return (2 * Math.abs(k) + 1) % arrLength;
    }
}
