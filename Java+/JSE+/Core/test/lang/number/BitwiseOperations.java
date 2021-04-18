package lang.number;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BitwiseOperations {
    @Test
    public void literalPositive() {
        byte b1 = 0b0000_0101;
        short b2 = 0b0000_0000_0000_0101;
        int b3 = 0b0000_0000_0000_0000_0000_0000_0000_0101;
        long b4 = 0b0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0101;
        int exp = 5;
        assertEquals(exp, b1);
        assertEquals(exp, b2);
        assertEquals(exp, b3);
        assertEquals(exp, b4);
    }

    @Test
    public void literalNegative() {
        byte b1 = (byte) 0b1111_1011;
        int b2 = 0b1111_1111_1111_1111_1111_1111_1111_1011;
        short b3 = 0b1111_1111_1111_1111_1111_1111_1111_1011;
        long b4 = 0b1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1011L;
        int exp = -5;
        assertEquals(exp, b1);
        assertEquals(exp, b2);
        assertEquals(exp, b3);
        assertEquals(exp, b4);
    }

    @Test
    public void toBinaryString() {
        int i = 250;
        String act = Integer.toBinaryString(i);
        String exp = "11111010";
        assertEquals(exp, act);
    }

    @Test
    public void and() {
        int b1 = 0b101;
        int b2 = 0b110;
        int act = b1 & b2;
        int exp = 0b100;
        assertEquals(exp, act);
    }

    @Test
    public void or() {
        int b1 = 0b101;
        int b2 = 0b110;
        int act = b1 | b2;
        int exp = 0b111;
        assertEquals(exp, act);
    }

    @Test
    public void xor() {
        int b1 = 0b101;
        int b2 = 0b110;
        int act = b1 ^ b2;
        int exp = 0b011;
        assertEquals(exp, act);
    }

    @Test
    public void complement() {
        int b = 0b0000_0000_0000_0000_0000_0000_0000_0101;
        int act = ~b;
        int exp = 0b1111_1111_1111_1111_1111_1111_1111_1010;
        assertEquals(exp, act);
    }

    @Test
    public void signedLeftShift() {
        int b = 0b1111_0001_0000_0000_0000_0000_0000_0101;
        int act = b << 2;
        System.out.println(Integer.toBinaryString(act));
        int exp = 0b1100_0100_0000_0000_0000_0000_0001_0100;
        assertEquals(exp, act);
    }

    @Test
    public void signedRightShift() {
        int b = 0b1111_0001_0000_0000_0000_0000_0000_0101;
        int act = b >> 2;
        int exp = 0b1111_1100_0100_0000_0000_0000_0000_0001;
        assertEquals(exp, act);
    }

    @Test
    public void unsignedRightShift() {
        int b = 0b1111_0001_0000_0000_0000_0000_0000_0101;
        int act = b >>> 2;
        int exp = 0b0011_1100_0100_0000_0000_0000_0000_0001;
        assertEquals(exp, act);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void checkSpecificByteValue() {
        int value = 0b0000_0001_0000_0000_0000_0000_0000_0101;
        int mask1 = 0b0000_0001_0000_0000_0000_0000_0000_0000;
        int mask2 = 0b0000_0000_1000_0000_0000_0000_0000_0000;
        boolean isSet1 = (value & mask1) > 0;
        boolean isSet2 = (value & mask2) > 0;
        assertTrue(isSet1);
        assertFalse(isSet2);
    }
}
