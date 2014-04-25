package ru.yaal.example.java.se.swing.jeditorpane;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {
    public static void main(String[] args) {
        JEditorPane editor = new JEditorPane();
        editor.setEditable(false);
        editor.setText("Информирую Вас о том, что в связи с празднованием 1-го мая – Дня труда и 9-ого мая – Дня Победы, мы отдыхаем:");

        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(editor);
    }
}
