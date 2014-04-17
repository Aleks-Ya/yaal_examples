package ru.yaal.examples.java.se.gui.swing.combobox;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Добавление элементов в выпадающий список через JComboBox (без DefaultComboBoxModel).
 */
public class Main2 {
    public static void main(String[] args) {
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setEditable(true);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new OkButtonActionListener(comboBox));

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(300, 50);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(comboBox, BorderLayout.CENTER);
        frame.add(okButton, BorderLayout.EAST);
    }

    static class OkButtonActionListener implements ActionListener {
        private JComboBox<String> comboBox;

        OkButtonActionListener(JComboBox<String> comboBox) {
            this.comboBox = comboBox;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedItem = (String) comboBox.getSelectedItem();
            if (selectedItem != null && !selectedItem.trim().isEmpty()) {
                comboBox.addItem(selectedItem);
            }
        }
    }
}

