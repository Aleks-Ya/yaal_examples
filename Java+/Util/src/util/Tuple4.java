package util;

public record Tuple4<E1, E2, E3, E4>(E1 element1, E2 element2, E3 element3, E4 element4) {
    public static <E1, E2, E3, E4> Tuple4<E1, E2, E3, E4> of(E1 element1, E2 element2, E3 element3, E4 element4) {
        return new Tuple4<>(element1, element2, element3, element4);
    }
}
