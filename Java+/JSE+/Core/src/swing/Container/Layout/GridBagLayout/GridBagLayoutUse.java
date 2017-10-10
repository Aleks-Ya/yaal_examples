package swing.Container.Layout.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * Растягивание компонентов на несколько ячеек по горизонтали и вертикали.
 */
public class GridBagLayoutUse {
    public static void main(String[] args) {
        EventQueue.invokeLater(new GridBagLayoutUseFrame());
    }
}

class GridBagLayoutUseFrame extends JFrame implements Runnable {

    @Override
    public void run() {
        setLayout(new GridBagLayout());
        setSize(500, 150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        JButton b1 = new JButton("Button 1");
        GridBagConstraints b1Constraints = new GridBagConstraints();
        b1Constraints.gridx = 0;
        b1Constraints.gridy = 0;
        b1Constraints.gridwidth = 1;
        b1Constraints.gridheight = 1;
        add(b1, b1Constraints);

        JButton b2 = new JButton("Button 2");
        GridBagConstraints b2Constraints = new GridBagConstraints();
        b2Constraints.gridx = 1;
        b2Constraints.gridy = 0;
        b2Constraints.gridwidth = 1;
        b2Constraints.gridheight = 1;
        add(b2, b2Constraints);

        JButton b3 = new JButton("Button 3");
        GridBagConstraints b3Constraints = new GridBagConstraints();
        b3Constraints.gridx = 2;
        b3Constraints.gridy = 0;
        b3Constraints.gridwidth = 1;
        b3Constraints.gridheight = 1;
        add(b3, b3Constraints);

        JTextArea ta4 = new JTextArea("TextArea 4");
        ta4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints ta4Constraints = new GridBagConstraints();
        ta4Constraints.gridx = 0;
        ta4Constraints.gridy = 1;
        ta4Constraints.gridwidth = 1;
        ta4Constraints.gridheight = 2;
        ta4Constraints.fill = GridBagConstraints.BOTH;
        add(ta4, ta4Constraints);

        JTextArea ta5 = new JTextArea("TextArea 5");
        ta5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints ta5Constraints = new GridBagConstraints();
        ta5Constraints.gridx = 1;
        ta5Constraints.gridy = 1;
        ta5Constraints.gridwidth = 2;
        ta5Constraints.gridheight = 1;
        ta5Constraints.fill = GridBagConstraints.BOTH;
        add(ta5, ta5Constraints);

        JTextArea ta6 = new JTextArea("TextArea 6");
        ta6.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints ta6Constraints = new GridBagConstraints();
        ta6Constraints.gridx = 2;
        ta6Constraints.gridy = 2;
        ta6Constraints.gridwidth = 1;
        ta6Constraints.gridheight = 1;
        ta6Constraints.fill = GridBagConstraints.BOTH;
        add(ta6, ta6Constraints);
    }
}