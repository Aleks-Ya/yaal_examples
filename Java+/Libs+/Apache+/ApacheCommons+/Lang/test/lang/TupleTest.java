package lang;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TupleTest {

    @Test
    void pair() {
        var left = "a";
        var right = "b";
        var pair = Pair.of(left, right);

        assertThat(pair.getKey()).isEqualTo(left);
        assertThat(pair.getValue()).isEqualTo(right);

        assertThat(pair.getLeft()).isEqualTo(left);
        assertThat(pair.getRight()).isEqualTo(right);

        assertThatThrownBy(() -> pair.setValue("bb")).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void mutablePair() {
        var left1 = "a";
        var right1 = "b";
        var pair = MutablePair.of(left1, right1);
        assertThat(pair.getKey()).isEqualTo(left1);
        assertThat(pair.getValue()).isEqualTo(right1);
        assertThat(pair.getLeft()).isEqualTo(left1);
        assertThat(pair.getRight()).isEqualTo(right1);

        var left2 = "aa";
        var right2 = "bb";
        pair.setLeft(left2);
        pair.setRight(right2);
        assertThat(pair.getKey()).isEqualTo(left2);
        assertThat(pair.getValue()).isEqualTo(right2);
        assertThat(pair.getLeft()).isEqualTo(left2);
        assertThat(pair.getRight()).isEqualTo(right2);

        var right3 = "bbb";
        pair.setValue(right3);
        assertThat(pair.getKey()).isEqualTo(left2);
        assertThat(pair.getValue()).isEqualTo(right3);
        assertThat(pair.getLeft()).isEqualTo(left2);
        assertThat(pair.getRight()).isEqualTo(right3);
    }

    @Test
    void immutablePair() {
        var left = "a";
        var right = "b";
        var pair = ImmutablePair.of(left, right);

        assertThat(pair.getKey()).isEqualTo(left);
        assertThat(pair.getValue()).isEqualTo(right);
        assertThat(pair.getLeft()).isEqualTo(left);
        assertThat(pair.getRight()).isEqualTo(right);

        assertThatThrownBy(() -> pair.setValue("bb")).isInstanceOf(UnsupportedOperationException.class);
    }

}
