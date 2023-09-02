package util;

import java.util.Objects;

public final class Tuple3<E1, E2, E3> {
    private final E1 e1;
    private final E2 e2;
    private final E3 e3;

    public Tuple3(E1 e1, E2 e2, E3 e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public static <E1, E2, E3> Tuple3<E1, E2, E3> of(E1 e1, E2 e2, E3 e3) {
        return new Tuple3<>(e1, e2, e3);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple3<?, ?, ?> tuple3 = (Tuple3<?, ?, ?>) o;
        return Objects.equals(e1, tuple3.e1) && Objects.equals(e2, tuple3.e2) && Objects.equals(e3, tuple3.e3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(e1, e2, e3);
    }
}
