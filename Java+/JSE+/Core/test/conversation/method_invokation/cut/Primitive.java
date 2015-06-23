package conversation.method_invokation.cut;

import org.junit.Test;

import static java.lang.System.out;

/**
 * Конверсия при вызове метода.
 */
public class Primitive {

    /**
     * Конверсия при вызове метода с параметрами примитивных типов.
     */
    @Test
    public void primitiveAll() {
        Cut cut = new Cut();

        out.println("byte => " + cut.m((byte) 1));
        out.println("short => " + cut.m((short) 1));
        out.println("char => " + cut.m((char) 1));
        out.println("int => " + cut.m((int) 1));
        out.println("long => " + cut.m((long) 1));
        out.println("float => " + cut.m((float) 1));
        out.println("double => " + cut.m((double) 1));

        out.println("Byte => " + cut.m(new Byte("1")));
        out.println("Short => " + cut.m(new Short("1")));
        out.println("Char => " + cut.m(new Character('a')));
        out.println("Int => " + cut.m(new Integer(1)));
        out.println("Long => " + cut.m(new Long(1)));
        out.println("Float => " + cut.m(new Float(1)));
        out.println("Double => " + cut.m(new Double(1)));
    }

    /**
     * Конверсия при вызове метода с параметрами примитивных типов (Byte).
     */
    @Test
    public void primitiveByte() {
        Cut cut = new Cut();
        byte b = 1;
        Byte bb = new Byte("1");
        out.println("byte      => " + cut.m(b));
        out.println("byte+byte => " + cut.m(b + b));
        out.println("Byte      => " + cut.m(bb));
        out.println("Byte+byte => " + cut.m(bb + b));
        out.println("byte+Byte => " + cut.m(b + bb));
    }
}

/**
 * Тестируемый класс.
 */
class Cut {
    String m(byte d) {
        return "byte";
    }

    String m(short d) {
        return "short";
    }

    String m(char d) {
        return "char";
    }

    String m(int d) {
        return "int";
    }

    String m(long d) {
        return "long";
    }

    String m(float d) {
        return "float";
    }

    String m(double d) {
        return "double";
    }
}