package lang.operator.equals;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

class Main {
    @Test
    void main() {
        Integer i1 = 1;
        Integer i2 = new Integer(1);
        int i3 = 1;
        Byte b1 = 1;
        Long g1 = 1L;

        out.println("[Integer i1 = 1;] equals [Byte b1 = 1;] -->" + i1.equals(b1));
        out.println("[Integer i1 = 1;] equals [Long g1 = 1L;] -->" + i1.equals(g1));
        out.println("[Integer i1 = 1;] equals [Integer i2 = new Integer(1);] -->" + i1.equals(i2));
        out.println("[Integer i1 = 1;] == [(byte) Byte b1 = 1; ] -->" + (i1 == (byte) b1));
        out.println("[Integer i1 = 1;] == [Byte b1 = 1; ] --> Compile Error \"incomparable types: Integer and Byte\"");
        out.println("[Integer i1 = 1;] == [int i3 = 1;] -->" + (i1 == i3));
        out.println("[Integer i1 = 1;] == [Integer i2 = new Integer(1);] -->" + (i1 == i2));
        out.println("[new Integer(1)] == [new Integer(1)] -->" + (new Integer(1) == new Integer(1)));
        out.println("[new Integer(1)] == [(int) new Integer(1)] -->" + (new Integer(1) == (int) new Integer(1)));
    }
}