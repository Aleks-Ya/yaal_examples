package core.swing.Container.JComponent.JRootPane;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.WindowConstants;
import java.awt.Container;
import java.awt.EventQueue;

/**
 * Добавление элементов на корневую панель.
 */
public class RootPanel extends JFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RootPanel();
            }
        });
    }

    public RootPanel() {
        setSize(600, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        JRootPane rootPane1 = getRootPane();
        Container contentPane = rootPane1.getContentPane();
        contentPane.add(new JLabel("Layout: " + rootPane1.getLayout()));
    }
}