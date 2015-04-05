package aligment;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

/**
 * Выравнивание компонентов на панели с BoxLayout.
 */
public class VerticalBoxAlignment {

    public static void main(String[] args) {
        JButton button = new JButton("ВПРАВО --->");
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JTextField text = new JTextField("<------- Выравнивание влево :)");
        text.setMaximumSize(new Dimension(250, 20));
        text.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel("--> Выравняно по центру <--");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JFrame frame = new JFrame();
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        BoxLayout layout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
        contentPane.setLayout(layout);

        frame.add(button);
        frame.add(text);
        frame.add(label);
        frame.setVisible(true);
    }
}