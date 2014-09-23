import static java.lang.System.out;

/**
 * Конверсия при вызове метода с параметрами примитивных типов.
 */
public class PrimitiveAll {
    public static void main(String[] args) {
        Cut cut = new Cut();
        
        out.println("byte => "   + cut.m( (byte) 1));
        out.println("short => "  + cut.m( (short) 1));
        out.println("char => "   + cut.m( (char) 1));
        out.println("int => "    + cut.m( (int) 1));
        out.println("long => "   + cut.m( (long) 1));
        out.println("float => "  + cut.m( (float) 1));
        out.println("double => " + cut.m( (double) 1));

        out.println("Byte => "   + cut.m(new Byte("1")));
        out.println("Short => "  + cut.m(new Short("1")));
        out.println("Char => "   + cut.m(new Character('a')));
        out.println("Int => "    + cut.m(new Integer(1)));
        out.println("Long => "   + cut.m(new Long(1)));
        out.println("Float => "  + cut.m(new Float(1)));
        out.println("Double => " + cut.m(new Double(1)));
    }
}