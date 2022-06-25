package lang.conversation.numeric_promositions;

import org.junit.jupiter.api.Test;

class NumericPromotionsTest {
    @Test
    void bytePromotion() {
        byte b = 10;
        int i1 = -b;
        int i2 = +b;
        int i3 = b % b;
        int i4 = b + b;
        int i5 = b * b;
        int i6 = b / b;
        b += 2;
        b -= 2;
        b %= 2;
        b /= 2;
    }

    @Test
    void shortPromotion() {
        short s = 10;
        int i1 = -s;
        int i2 = +s;
        int i3 = s % s;
        int i4 = s + s;
        int i5 = s * s;
        int i6 = s / s;
        s += 2;
        s -= 2;
        s %= 2;
        s /= 2;
    }

    @Test
    void charPromotion() {
        char c = 'a';
        int i1 = -c;
        int i2 = +c;
        int i3 = c % c;
        int i4 = c + c;
        int i5 = c * c;
        int i6 = c / c;
        c += 2;
        c -= 2;
        c %= 2;
        c /= 2;
    }

    @Test
    void intPromotion() {
        int i = 1;
        int i1 = -i;
        int i2 = +i;
        int i3 = i % i;
        int i4 = i + i;
        int i5 = i * i;
        int i6 = i / i;
        i += 2;
        i -= 2;
        i %= 2;
        i /= 2;
    }

    @Test
    void floatPromotion() {
        float f = 1;
        float i1 = -f;
        float i2 = +f;
        float i3 = f % f;
        float i4 = f + f;
        float i5 = f * f;
        float i6 = f / f;
        f += 2;
        f -= 2;
        f %= 2;
        f /= 2;
    }
}
