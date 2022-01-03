package jrebel;

import java.util.concurrent.TimeUnit;

/**
 * Add a method without restarting JVM.
 * 1. Run the main class ("Rebel Run 'JRebelWorks.main()'").
 * 2. Uncomment lines
 * 3. Compile current Java class (Ctrl-Shift-F9 for Idea).
 * 4. Expect: print 2 instead of 1.
 */
public class JRebelWorks {
    public JRebelWorks() {
    }

    public static void main(String[] args) throws InterruptedException {
        JRebelWorks jrw = new JRebelWorks();
        while (true) {
            TimeUnit.SECONDS.sleep(2);
            System.out.println(jrw.getString());
        }
    }

    String getString() {
//        return String.valueOf(getNum());
        return String.valueOf(1);
    }

//    int getNum() {
//        return 2;
//    }
}
