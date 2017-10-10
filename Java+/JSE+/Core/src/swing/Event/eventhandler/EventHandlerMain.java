package swing.Event.eventhandler;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

/**
 * Использование EventHandler для генерации слушателей событий, состоящих из вызова одного метода.
 */
public class EventHandlerMain {
    public static void main(String[] args) throws InterruptedException {
        new Frame();
    }
}

class Frame extends JFrame {
    private final JLabel l1 = new JLabel();
    private final JLabel l2 = new JLabel();
    private final JLabel l3 = new JLabel();

    Frame() throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
        setLayout(new FlowLayout());

        JButton b = new JButton("Click me");
        add(b);
        add(l1);
        add(l2);
        add(l3);

        final ActionListener listener = EventHandler.create(ActionListener.class, this, "toFront");
        b.addActionListener(listener);
    }

    /**
     * Этот метод не удалось вызвать.............
     */
    public void label1() {
        l1.setText("action");
    }

}