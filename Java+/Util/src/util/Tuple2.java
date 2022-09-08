package util;

public record Tuple2<L, R>(L left, R right) {
    public static <L, R> Tuple2<L, R> of(L left, R right) {
        return new Tuple2<>(left, right);
    }
}
