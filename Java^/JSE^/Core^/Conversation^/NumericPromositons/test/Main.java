import org.junit.Test;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

public class Main {
    @Test
    public void bytePromotion() {
        byte b = 10;
        b += 2;
        b -= 2;
        b %= 2;
        b /= 2;
        int i1 = -b;
        int i2 = +b;
        int i3 = b % b;
        int i4 = b + b;
        int i5 = b * b;
        int i6 = b / b;
    }

    @Test
    public void shortPromotion() {
        short s = 10;
        s += 2;
        s -= 2;
        s %= 2;
        s /= 2;
        int i1 = -s;
        int i2 = +s;
        int i3 = s % s;
        int i4 = s + s;
        int i5 = s * s;
        int i6 = s / s;
    }

    @Test
    public void charPromotion() {
        char c = 'a';
        c += 2;
        c -= 2;
        c %= 2;
        c /= 2;
        int i1 = -c;
        int i2 = +c;
        int i3 = c % c;
        int i4 = c + c;
        int i5 = c * c;
        int i6 = c / c;
    }

    @Test
    public void intPromotion() {
        int i = 1;
        i += 2;
        i -= 2;
        i %= 2;
        i /= 2;
        int i1 = -i;
        int i2 = +i;
        int i3 = i % i;
        int i4 = i + i;
        int i5 = i * i;
        int i6 = i / i;
    }

    @Test
    public void floatPromotion() {
        float f = 1;
        f += 2;
        f -= 2;
        f %= 2;
        f /= 2;
        float i1 = -f;
        float i2 = +f;
        float i3 = f % f;
        float i4 = f + f;
        float i5 = f * f;
        float i6 = f / f;
    }
}
