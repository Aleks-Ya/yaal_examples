import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;


class PaintComponent {
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
        Container contentPane = getContentPane();
        HelloWorldPanel helloWorldPanel = new HelloWorldPanel();
        contentPane.add(helloWorldPanel);
    }

    private void init() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class HelloWorldPanel extends JPanel {
    private static final int MESSAGE_X = 100;
    private static final int MESSAGE_Y = 200;

    @Override
    protected void paintComponent(Graphics g) {
        g.drawString("Hello, World!", MESSAGE_X, MESSAGE_Y);
    }
}
