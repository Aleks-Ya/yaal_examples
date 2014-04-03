package ru.yaal.examples.java.gui.swing;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Container;

/**
 * Created by aleks on 3/28/14.
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        Container frameContainer = frame.getContentPane();

        Component button = new JButton("Hello, Component!");
        Container buttonContainer = new JPanel();
        buttonContainer.add(button);

        Component label = new JLabel("Fuck off, Component");
        Container labelContainer = new JPanel();
        labelContainer.add(label);

        frameContainer.

    }

}
