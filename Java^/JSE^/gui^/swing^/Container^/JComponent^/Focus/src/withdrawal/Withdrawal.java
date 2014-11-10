package withdrawal;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

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
        JPanel textFieldsPanel = new JPanel();
        textFieldsPanel.setLayout(new GridLayout(3, 3, 30, 30));
        JTextField tf1 = new JTextField("1");
        textFieldsPanel.add(tf1);
        textFieldsPanel.add(new JTextField("2"));
        textFieldsPanel.add(new JTextField("3"));
        textFieldsPanel.add(new JTextField("4"));
        textFieldsPanel.add(new JTextField("5"));
        textFieldsPanel.add(new JPanel());
        textFieldsPanel.add(new JTextField("7"));
        textFieldsPanel.add(new JTextField("8"));
        textFieldsPanel.add(new JTextField("9"));

        Border border = BorderFactory.createLineBorder(Color.RED, 5);
        tf1.setBorder(border);

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
