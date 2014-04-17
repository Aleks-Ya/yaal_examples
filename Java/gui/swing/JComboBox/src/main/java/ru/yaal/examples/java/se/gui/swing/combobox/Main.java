package ru.yaal.examples.java.se.gui.swing.combobox;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import java.awt.BorderLayout;

public class Main {
    public static void main(String[] args) {
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setEditable(true);

        ComboBoxModel<String> comboBoxModel = comboBox.getModel();

        JButton okButton = new JButton("OK");

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(300, 50);
        frame.setVisible(true);
        frame.add(comboBox, BorderLayout.CENTER);
        frame.add(okButton, BorderLayout.EAST);
    }
}
