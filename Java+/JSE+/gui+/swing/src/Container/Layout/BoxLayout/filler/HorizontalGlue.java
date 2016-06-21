package Container.Layout.BoxLayout.filler;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;

/**
 * Использование Horizontal Glue с BoxLayout.
 * Horizontal Glue расталкивает соседние компоненты на максимальное расстояние по горизонтали.
 */
public class HorizontalGlue {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //init
                JFrame frame = new JFrame();
                frame.setSize(500, 100);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);

                //set box layout
                Container contentPane = frame.getContentPane();
                BoxLayout layout = new BoxLayout(contentPane, BoxLayout.X_AXIS);
                contentPane.setLayout(layout);

                //make JTextField
                JTextField text = new JTextField("Введите что-нибудь :)");
                text.setMaximumSize(new Dimension(250, 20));

                //make JButton
                JButton button = new JButton("The Button");

                //make Horizontal Glue
                Component horizontalGlue = Box.createHorizontalGlue();

                //add components
                frame.add(text);
                frame.add(horizontalGlue);
                frame.add(button);
            }
        });
    }
}