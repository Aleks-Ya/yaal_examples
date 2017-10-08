package core.swing.Container.JComponent.JEditorPane;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;

public class VerticalCenterAlignment {
    public static void main(String[] args) {
        JEditorPane editor = new JEditorPane();
        editor.setEditable(false);
        editor.setFocusable(false);
        editor.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        editor.setText("Java Platform, Standard Edition or Java SE is a widely used platform for development and " +
                "deployment of portable applications for desktop and server environments.");

        JScrollPane scrollPane = new JScrollPane(editor);

        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        panel.add(Box.createVerticalGlue());
        panel.add(scrollPane);
        panel.add(Box.createVerticalGlue());

        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.add(panel);
    }
}
