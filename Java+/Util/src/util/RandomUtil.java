package util;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    private RandomUtil() {
    }

    public static int randomIntZeroOrPositive() {
        return random.nextInt(0, Integer.MAX_VALUE);
    }

    public static int randomIntPositive() {
        return random.nextInt(1, Integer.MAX_VALUE);
    }

}
