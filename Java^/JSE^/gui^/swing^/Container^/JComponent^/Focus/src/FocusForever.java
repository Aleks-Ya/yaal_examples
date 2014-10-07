import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Возврат фокуса на компонент каждый раз, когда окно получает фокус.
 */
class FocusForever {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HelloWorldFrame();
            }
        });
    }
}

class HelloWorldFrame extends JFrame {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;

    HelloWorldFrame() {
        init();

        final JTextField text = new JTextField("Focus will returns here.");
        addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                text.requestFocusInWindow();
            }
        });

        add(text);
        add(new JTextField("Other field"));
    }

    private void init() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new FlowLayout());
    }
}