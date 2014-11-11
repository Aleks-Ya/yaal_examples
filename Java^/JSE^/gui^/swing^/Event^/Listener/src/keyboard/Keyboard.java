package keyboard;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 * Выводит на экран нажатые клавиши.
 */
public class Keyboard {
    public static void main(String[] args) {
        JLabel label = new JLabel();

        KeyboardEventsListener keyboardEventsListener = new KeyboardEventsListener(label);

        JFrame frame = new JFrame();
        frame.setSize(300, 100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(label);
        frame.addKeyListener(keyboardEventsListener);
    }
}