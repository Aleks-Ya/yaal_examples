package swing.Container.JComponent.JLabel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.EventQueue;

/**
 * Вывод многострочного текста с помощью JLabel.
 */
class MultiLineLabel {
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
        JLabel label = new JLabel("<html><body>Строка 1<br/>Строка 2</body></html>");
        add(label);
    }

    private void init() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}