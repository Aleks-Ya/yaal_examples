package Event.listener.keyboard;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Выводит на экран нажатые клавиши.
 */
public class Keyboard {
    public static void main(String[] args) {
        EventQueue.invokeLater(new KeyboardFrame());
    }
}

class KeyboardFrame extends JFrame implements Runnable {
    private final JTextArea text = new JTextArea();

    @Override
    public void run() {
        KeyListener keyboardEventsListener = new KeyboardEventsListener();
        addKeyListener(keyboardEventsListener);

        text.setLineWrap(true);
        text.setFocusable(false);

        setSize(300, 100);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(text, BorderLayout.CENTER);
    }

    private class KeyboardEventsListener extends KeyAdapter {

        @Override
        public void keyTyped(KeyEvent e) {
            String key;
            switch (e.getKeyChar()) {
                case KeyEvent.VK_ESCAPE: {
                    key = "ESC";
                    break;
                }
                case KeyEvent.VK_ENTER: {
                    key = "Enter";
                    break;
                }
                case KeyEvent.VK_KP_UP: {
                    key = "Up";
                    break;
                }
                default: {
                    key = String.valueOf(e.getKeyChar());
                }
            }

            int modifiers = e.getModifiers();
            String modifiersText = KeyEvent.getKeyModifiersText(modifiers);
            text.append(modifiersText + key + " ");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            String key = null;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP: {
                    key = "Up";
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    key = "Down";
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    key = "Right";
                    break;
                }
                case KeyEvent.VK_LEFT: {
                    key = "Left";
                    break;
                }
            }

            if (key != null) {
                int modifiers = e.getModifiers();
                String modifiersText = KeyEvent.getKeyModifiersText(modifiers);
                text.append(modifiersText + key + " ");
            }
        }
    }
}