package core.swing.Container.JComponent.Size;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

/**
 * Изменение размера компонента в FlowLayout.
 */
class FlowLayoutSize {
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

        setLayout(new FlowLayout());

        JButton b = new JButton("Maximum and Minimum size");
        b.setPreferredSize(new Dimension(100, 100));
        add(b);
    }

    private void init() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}