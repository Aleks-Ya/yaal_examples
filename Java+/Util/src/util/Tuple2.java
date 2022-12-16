package util;

import java.util.Objects;

public final class Tuple2<L, R> {
    private final L left;
    private final R right;

    private Tuple2(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Tuple2<L, R> of(L left, R right) {
        return new Tuple2<>(left, right);
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;
        return Objects.equals(left, tuple2.left) && Objects.equals(right, tuple2.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
