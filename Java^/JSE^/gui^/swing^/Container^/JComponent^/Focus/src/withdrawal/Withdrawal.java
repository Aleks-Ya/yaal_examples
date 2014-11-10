package withdrawal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

public class Withdrawal {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WithdrawalFrame();
            }
        });
    }
}

class WithdrawalFrame extends JFrame {
    public WithdrawalFrame() {
        JPanel textFieldsPanel = new JPanel(new GridLayout(3, 3, 30, 30));

        final JComponent c00 = new JTextField("1");
        final JComponent c01 = new JTextField("2");
        final JComponent c02 = new JTextField("3");
        final JComponent c10 = new JTextField("4");
        final JComponent c11 = new JTextField("5");
        final JComponent c12 = new JPanel();
        final JComponent c20 = new JTextField("7");
        final JComponent c21 = new JTextField("8");
        final JComponent c22 = new JTextField("9");

        textFieldsPanel.add(c00);
        textFieldsPanel.add(c01);
        textFieldsPanel.add(c02);
        textFieldsPanel.add(c10);
        textFieldsPanel.add(c11);
        textFieldsPanel.add(c12);
        textFieldsPanel.add(c20);
        textFieldsPanel.add(c21);
        textFieldsPanel.add(c22);

        Border border = BorderFactory.createLineBorder(Color.RED, 5);
        c00.setBorder(border);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(new JButton("OK"));
        buttonsPanel.add(new JButton("Cancel"));

        add(textFieldsPanel);
        add(buttonsPanel, BorderLayout.SOUTH);

        setSize(500, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}