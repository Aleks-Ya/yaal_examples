package swing.example.box;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BoxUse {

    public static void main(String[] args) {
        JButton button = new JButton("Bukvoed");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label = new JLabel("Label!");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new LineBorder(Color.RED));
        JTextField field = new JTextField(20);
        JTextArea text = new JTextArea("Java Web Archive (.war)");

        Box box = Box.createVerticalBox();
        box.add(button);
        box.add(label);
        box.setBorder(new LineBorder(Color.green));
//        box.add(field);
//        box.add(text);
//        box.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JFrame frame = new JFrame("Swing окно");
        frame.setContentPane(box);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setVisible(true);
    }
}
