import static java.lang.System.out;

/**
 * Конверсия при вызове метода с параметрами примитивных типов (Byte).
 */
public class PrimitiveByte {
    public static void main(String[] args) {
        Cut cut = new Cut();
        byte b = 1;
        Byte bb = new Byte("1");
        out.println("byte      => "   + cut.m(b));
        out.println("byte+byte => "   + cut.m(b + b));
        out.println("Byte      => "   + cut.m(bb));
        out.println("Byte+byte => "   + cut.m(bb + b));
        out.println("byte+Byte => "   + cut.m(b + bb));
    }
}