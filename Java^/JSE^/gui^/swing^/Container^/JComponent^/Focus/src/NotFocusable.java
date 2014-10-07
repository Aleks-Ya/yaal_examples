import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.EventQueue;
import java.awt.FlowLayout;

/**
 * Компонент, на который не может быть переведен фокус.
 */
class NotFocusable {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame();
            }
        });
    }

    static class Frame extends JFrame {
        private static final int FRAME_WIDTH = 600;
        private static final int FRAME_HEIGHT = 400;

        Frame() {
            init();

            JButton b1 = new JButton("Hello");
            JButton b2 = new JButton("Not focusable");
            b2.setFocusable(false);
            JButton b3 = new JButton("Bye");

            add(b1);
            add(b2);
            add(b3);
        }

        private void init() {
            setSize(FRAME_WIDTH, FRAME_HEIGHT);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
            setLayout(new FlowLayout());
        }
    }
}
