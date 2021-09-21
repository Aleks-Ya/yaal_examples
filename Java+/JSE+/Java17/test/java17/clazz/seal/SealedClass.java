package java17.clazz.seal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SealedClass {

    @Test
    void seal() {
        BinaryExpression add = new Addition();
        assertThat(add.perform(2, 5)).isEqualTo(7);
        BinaryExpression subtracts = new Subtraction();
        assertThat(subtracts.perform(7, 2)).isEqualTo(5);
    }

    private sealed interface BinaryExpression permits Addition, Subtraction {
        double perform(double x, double y);
    }

    private static final class Addition implements BinaryExpression {
        @Override
        public double perform(double x, double y) {
            return x + y;
        }
    }

    private static final class Subtraction implements BinaryExpression {
        @Override
        public double perform(double x, double y) {
            return x - y;
        }
    }

}
