package core.swing.Container.JComponent.Size;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.EventQueue;

/**
 * Изменение размера и положения компонентов без менеджера раскладки.
 */
public class NoLayout {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NoLayoutFrame();
            }
        });
    }
}

class NoLayoutFrame extends JFrame {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;

    NoLayoutFrame() {
        init();

        setLayout(null);

        JButton b = new JButton("The True Button");
        b.setBounds(100, 100, 200, 100);
        add(b);

        JButton b2 = new JButton("Last Button");
        b2.setLocation(200, 150);
        b2.setSize(300, 120);
        add(b2);
    }

    private void init() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}