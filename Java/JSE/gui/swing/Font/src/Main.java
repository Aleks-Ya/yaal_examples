import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Font[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        System.out.printf("Available fonts: %d\n", availableFonts.length);

        Map<? extends AttributedCharacterIterator.Attribute, Object> fontAttrs = new HashMap<>();
        fontAttrs.put(TextAttribute.FONT, TextAttribute.WEIGHT_HEAVY);

        Font font = new Font(fontAttrs);

        JLabel label = new JLabel("Съешь этих французских булок да выпей чаю, мудак");
        label.setFont(font);

        JFrame frame = new JFrame();
        frame.setSize(600, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(label);
    }
}
