package java8.lambda;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;

/**
 * Использование лямбды для реализации интерфейса ActionListener.
 */
class ListenerLambdaTest {

    @Test
    void listener() {
        JButton testButton = new JButton("Test Button");
        testButton.addActionListener(e -> System.out.println("Click Detected by Lambda Listner"));

        JFrame frame = new JFrame("Listener Test");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(testButton, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
