package swing.Container.JComponent.JTextComponent.JTextPane;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Component;

/**
 * Выравнивание текста.
 */
public class Alignment {
    public static void main(String[] args) {
        JTextPane editor = new JTextPane();
        editor.setEditable(false);
        editor.setAlignmentX(Component.RIGHT_ALIGNMENT);
        editor.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        editor.setText("Происхождение Александра Сергеевича Пушкина идёт от разветвлённого нетитулованного дворянского " +
                "рода Пушкиных, восходившего по генеалогической легенде к «мужу честну» Ратше, современнику " +
                "Александра Невского.");
        StyledDocument doc = editor.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_RIGHT);
        doc.setParagraphAttributes(0, doc.getLength(), attrs, false);

        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(editor);
    }
}
