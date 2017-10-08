package core.swing.Container.JComponent.JLayeredPane;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;
import java.awt.EventQueue;

/**
 * Размещение компонентов на разных слоях в JLayeredPane.
 */
public class LayeredPane extends JFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LayeredPane();
            }
        });
    }

    public LayeredPane() {
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        JButton buttonTop = new JButton("Верхняя кнопка");
        buttonTop.setBounds(10, 10, 200, 100);

        JButton buttonBottom = new JButton("Нижняя кнопка");
        buttonBottom.setBounds(80, 80, 200, 100);

        JLayeredPane pane = getLayeredPane();

        JLabel label = new JLabel("Layout: " + pane.getLayout());
        label.setBounds(50, 50, 300, 100);

        pane.add(buttonTop, JLayeredPane.PALETTE_LAYER);
        pane.add(buttonBottom, JLayeredPane.DEFAULT_LAYER);
        pane.add(label, JLayeredPane.DRAG_LAYER);
    }
}