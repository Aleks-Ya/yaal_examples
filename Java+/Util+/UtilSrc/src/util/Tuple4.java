package util;

import java.util.Objects;

public final class Tuple4<E1, E2, E3, E4> {
    private final E1 e1;
    private final E2 e2;
    private final E3 e3;
    private final E4 e4;

    public Tuple4(E1 e1, E2 e2, E3 e3, E4 e4) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
        this.e4 = e4;
    }

    public static <E1, E2, E3, E4> Tuple4<E1, E2, E3, E4> of(E1 e1, E2 e2, E3 e3, E4 e4) {
        return new Tuple4<>(e1, e2, e3, e4);
    }


    public E1 element1() {
        return e1;
    }

    public E2 element2() {
        return e2;
    }

    public E3 element3() {
        return e3;
    }

    public E4 element4() {
        return e4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple4<?, ?, ?, ?> tuple4 = (Tuple4<?, ?, ?, ?>) o;
        return Objects.equals(e1, tuple4.e1) && Objects.equals(e2, tuple4.e2) && Objects.equals(e3, tuple4.e3) && Objects.equals(e4, tuple4.e4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(e1, e2, e3, e4);
    }
}
