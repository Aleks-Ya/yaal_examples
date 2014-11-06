import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

/**
 * Изменение длины JTextField.
 */
public class Size {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SizeFrame();
            }
        });
    }
}

class SizeFrame extends JFrame {
    SizeFrame() throws HeadlessException {
        init();

        JTextField textShort = new JTextField("Hello", 5);
        JTextField textLong = new JTextField("Hello", 10);

        add(textShort);
        add(textLong);
    }

    private void init() {
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new FlowLayout());
    }
}