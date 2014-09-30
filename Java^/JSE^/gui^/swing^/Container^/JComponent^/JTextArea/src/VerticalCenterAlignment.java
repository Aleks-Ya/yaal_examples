import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VerticalCenterAlignment {
    public static void main(String[] args) {
        JTextArea area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setText("two lines text block, two lines text block, two lines text block, two lines text block");

        JScrollPane scrollPane = new JScrollPane(area);

        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        panel.add(Box.createVerticalGlue());
        panel.add(scrollPane);
        panel.add(Box.createVerticalGlue());

        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        frame.add(panel);

        frame.setVisible(true);
    }
}
