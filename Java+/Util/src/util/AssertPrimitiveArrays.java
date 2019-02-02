package util;

public final class AssertPrimitiveArrays {
    /**
     * Hamcrest невозможно использовать с массивами примитивов
     * (см. example ArrayAssert).
     */
    public static void assertArray(int[] actual, int[] expected) {
        if (actual == null) {
            throw new AssertionError("Actual array is null");
        }
        if (expected == null) {
            throw new AssertionError("Expected array is null");
        }
        if (actual.length != expected.length) {
            throw new AssertionError("Length mismatch: expected " + expected.length
                    + " actual " + actual.length);
        }
        for (int i = 0; i < actual.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Value mismatch: expected " + expected[i]
                        + " actual " + actual[i]);
            }
        }
    }
}
