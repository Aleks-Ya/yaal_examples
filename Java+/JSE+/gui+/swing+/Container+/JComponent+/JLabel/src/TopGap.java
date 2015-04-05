import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

/**
 * Оставляем свободное пространство над JLabel.
 */
class TopGap {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TopGapFrame();
            }
        });
    }
}

class TopGapFrame extends JFrame {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;

    TopGapFrame() {
        init();
        setLayout(new FlowLayout());
        JLabel label = new JLabel("<html><body>Строка 1<br/>Строка 2<br/>Строка 3</body></html>");
        label.setPreferredSize(new Dimension(300, 300));
        label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        add(label);
    }

    private void init() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}