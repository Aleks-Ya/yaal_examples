package hierarchy;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

/**
 * Слушатель изменения иерархии компонентов.
 */
public class HierarchyListenerUse {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HierarchyListenerFrame();
            }
        });
    }
}

class HierarchyListenerFrame extends JFrame {
    private final JTextArea text = new JTextArea();

    HierarchyListenerFrame() throws HeadlessException {
        JLabel label = new JLabel("Label");

        JPanel middlePanel = new JPanel();
        middlePanel.add(label);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setName("Bottom Panel");
        bottomPanel.add(middlePanel);
        bottomPanel.addHierarchyListener(new MyHierarchyListener());

        add(bottomPanel, BorderLayout.NORTH);
        add(text, BorderLayout.CENTER);

        setSize(1000, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class MyHierarchyListener implements HierarchyListener {
        @Override
        public void hierarchyChanged(HierarchyEvent e) {
            text.append("Source: " + e.getSource() + "\n");
            text.append("Changed: " + e.getChanged() + "\n");
            text.append("Changed parent: " + e.getChangedParent() + "\n");
            text.append("Change flags: " + e.getChangeFlags() + "\n");
            text.append("Param string: " + e.paramString() + "\n");
            text.append("ID: " + e.getID() + "\n");
            text.append("\n\n");
        }
    }
}