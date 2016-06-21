package Container.JComponent.JPanel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Выравнивание элементов на панели JPanel.
 */
public class Alignment extends JFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Alignment();
            }
        });
    }

    public Alignment() {
        setSize(600, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        final FlowLayout layout = new FlowLayout(FlowLayout.LEADING);
        final JPanel p = new JPanel(layout);
        p.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        final JLabel lAlignment = new JLabel("Alignment: LEADING");
        final JLabel lOrientation = new JLabel("ComponentOrientation: RIGHT_TO_LEFT");

        JButton bAlignment = new JButton("Press to change alignment");
        bAlignment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (layout.getAlignment()) {
                    case FlowLayout.LEADING: {
                        layout.setAlignment(FlowLayout.LEFT);
                        lAlignment.setText("Alignment: LEFT");
                        break;
                    }
                    case FlowLayout.LEFT: {
                        layout.setAlignment(FlowLayout.CENTER);
                        lAlignment.setText("Alignment: CENTER");
                        break;
                    }
                    case FlowLayout.CENTER: {
                        layout.setAlignment(FlowLayout.RIGHT);
                        lAlignment.setText("Alignment: RIGHT");
                        break;
                    }
                    case FlowLayout.RIGHT: {
                        layout.setAlignment(FlowLayout.TRAILING);
                        lAlignment.setText("Alignment: TRAILING");
                        break;
                    }
                    case FlowLayout.TRAILING: {
                        layout.setAlignment(FlowLayout.LEADING);
                        lAlignment.setText("Alignment: LEADING");
                        break;
                    }
                    default:
                        throw new RuntimeException("default");
                }
                p.revalidate();
            }
        });

        JButton bOrientation = new JButton("Change orientation");
        bOrientation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (p.getComponentOrientation() == ComponentOrientation.RIGHT_TO_LEFT) {
                    p.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    lOrientation.setText("ComponentOrientation: LEFT_TO_RIGHT");
                } else {
                    p.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    lOrientation.setText("ComponentOrientation: RIGHT_TO_LEFT");
                }
            }
        });

        p.add(new JButton("Do nothing"));
        p.add(bAlignment);
        p.add(bOrientation);
        add(lOrientation, BorderLayout.NORTH);
        add(p);
        add(lAlignment, BorderLayout.SOUTH);
    }
}