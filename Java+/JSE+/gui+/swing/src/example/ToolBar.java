package example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar {
    public static void main(String[] args) {
        final JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        final JToolBar toolBar = new JToolBar("The Tool Bar");
        toolBar.add(new JButton("Do Nothing"));
        toolBar.add(new JButton("Don't"));
        toolBar.addSeparator();
        toolBar.add(exitButton);

        final JFrame frame = new JFrame("Swing окно");
        frame.setLayout(new BorderLayout());
        frame.add(toolBar, BorderLayout.PAGE_START);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
