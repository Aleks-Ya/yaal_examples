package ru.yaal.examples.java.se.gui.swing.combobox;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Добавление элементов в выпадающий список (своя реализация модели JComboBoxModel).
 */
public class MainOwnModel {
    public static void main(String[] args) {
        JComboBox<String> comboBox = new JComboBox<String>(new UniqueStringComboBoxModel());
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

    static class UniqueStringComboBoxModel extends DefaultComboBoxModel<String> {
        @Override
        public void addElement(String element) {
            String selectedItem = (String) getSelectedItem();
            if (!isEmptyString(selectedItem) && !alreadyAdded(selectedItem)) {
                super.addElement(selectedItem);
            }
        }

        private boolean isEmptyString(String str) {
            return str != null && str.trim().isEmpty();
        }

        private boolean alreadyAdded(String item) {
            return getIndexOf(item) >= 0;
        }
    }

    static class OkButtonActionListener implements ActionListener {
        private JComboBox<String> comboBox;

        OkButtonActionListener(JComboBox<String> comboBox) {
            this.comboBox = comboBox;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            comboBox.addItem((String) comboBox.getSelectedItem());
        }
    }

}