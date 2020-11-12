package java8.lambda;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;

/**
 * Использование лямбды для реализации интерфейса ActionListener.
 */
public class ListenerLambda {

    @Test
    public void listener() {
        JButton testButton = new JButton("Test Button");
        testButton.addActionListener(e -> System.out.println("Click Detected by Lambda Listner"));

        JFrame frame = new JFrame("Listener Test");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(testButton, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
