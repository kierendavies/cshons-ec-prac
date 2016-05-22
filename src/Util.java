import java.util.Random;

public class Util {
    private static final Random random = new Random();

    public static Pair<Integer, Integer> randomDistinctPair(int bound) {
        int x = random.nextInt(bound);
        int y = random.nextInt(bound - 1);
        if (x <= y) {
            y++;
        } else {
            int t = x;
            x = y;
            y = t;
        }
        return new Pair<>(x, y);
    }

    public static int linearSearch(int[] array, int element) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                index = i;
                break;
            }
        }
        return index;
    }
}
