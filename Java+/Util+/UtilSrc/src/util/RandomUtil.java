package util;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    private RandomUtil() {
    }

    public static int randomIntZeroOrPositive() {
        return random.nextInt(Integer.MAX_VALUE);
    }

    public static int randomIntPositive() {
        return 1 + random.nextInt(Integer.MAX_VALUE - 1);
    }

    public static long randomLongPositive() {
        long value;
        do {
            value = random.nextLong();
        } while (value <= 0);
        return value;

    }
}
