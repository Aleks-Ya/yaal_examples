import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
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
        final JLabel l = new JLabel("LEADING");

        JButton b = new JButton("Press to change alignment");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (layout.getAlignment()) {
                    case FlowLayout.LEADING: {
                        layout.setAlignment(FlowLayout.LEFT);
                        l.setText("LEFT");
                        break;
                    }
                    case FlowLayout.LEFT: {
                        layout.setAlignment(FlowLayout.CENTER);
                        l.setText("CENTER");
                        break;
                    }
                    case FlowLayout.CENTER: {
                        layout.setAlignment(FlowLayout.RIGHT);
                        l.setText("RIGHT");
                        break;
                    }
                    case FlowLayout.RIGHT: {
                        layout.setAlignment(FlowLayout.TRAILING);
                        l.setText("TRAILING");
                        break;
                    }
                    case FlowLayout.TRAILING: {
                        layout.setAlignment(FlowLayout.LEADING);
                        l.setText("LEADING");
                        break;
                    }
                    default:
                        throw new RuntimeException("default");
                }
                p.revalidate();
            }
        });

        p.add(new JButton("Do nothing"));
        p.add(b);
        p.add(new JButton("Do nothing too"));
        add(p);
        add(l, BorderLayout.SOUTH);
    }
}