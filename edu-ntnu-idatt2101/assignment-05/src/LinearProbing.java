public class LinearProbing implements HashingStrategy {
    @Override
    public int getNextIndex(int[] arr, int h1, int x, int attempt) {
        return (h1 + attempt) % arr.length;
    }
}
