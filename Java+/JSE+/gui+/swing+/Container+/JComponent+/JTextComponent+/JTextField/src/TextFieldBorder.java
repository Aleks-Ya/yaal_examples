import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

/**
 * Рамка вокруг JTextField.
 */
public class TextFieldBorder {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TextFieldBorderFrame();
            }
        });
    }
}

class TextFieldBorderFrame extends JFrame {
    TextFieldBorderFrame() throws HeadlessException {
        init();

        JTextField field = new JTextField("Hello", 20);
        Border border = BorderFactory.createLineBorder(Color.black, 5);
        field.setBorder(border);

        add(field);
    }

    private void init() {
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new FlowLayout());
    }
}