package swing.example.box;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class BoxLayoutUse {
    public static void main(String[] args) {
        JButton button = new JButton("Bukvoed");
//        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label = new JLabel("Text!");
        JTextField field = new JTextField(10);
        JTextArea text = new JTextArea("Java Web Archive (.war)");

        final JFrame frame = new JFrame("Swing окно");
        JPanel contentPane = (JPanel) frame.getContentPane();
        BoxLayout layout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
//        contentPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.setLayout(layout);
        frame.add(button);
        frame.add(label);
//        frame.add(field);
//        frame.add(text);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setVisible(true);
    }

}
