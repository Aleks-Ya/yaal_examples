package Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.Font;
import java.awt.LayoutManager;

public class LogicalNames {
    private static final String ALPHABET = "Съешь этих французских булок да выпей чаю, мудак";

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        LayoutManager layout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);

        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setLayout(layout);


        frame.add(makeLabel("Default"));// Можно "Default" или любую левую константу
        frame.add(makeLabel("Dialog"));
        frame.add(makeLabel("DialogInput"));
        frame.add(makeLabel("Monospaced"));
        frame.add(makeLabel("Serif"));
        frame.add(makeLabel("SansSerif"));
    }

    private static JLabel makeLabel(String fontLogicalName) {
        Font font = new Font(fontLogicalName, Font.PLAIN, 20);
        String message = String.format("%s/%s: %s", fontLogicalName, font.getFamily(), ALPHABET);
        JLabel label = new JLabel(message);
        label.setFont(font);
        return label;
    }
}
