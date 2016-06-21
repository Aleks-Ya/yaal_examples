package LookAndFeel;

import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Смена Look & Feel.
 */
public class ChangeLookAndFeel {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChangeLookAndFeelFrame();
            }
        });
    }
}

class ChangeLookAndFeelFrame extends JFrame {
    private final JTextArea info = new JTextArea(5, 20);

    ChangeLookAndFeelFrame() {
        info.setLineWrap(true);

        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setVisible(true);

        final LookAndFeel metal = new MetalLookAndFeel();
        final LookAndFeel windows = new WindowsLookAndFeel();
        final LookAndFeel motif = new MotifLookAndFeel();

        final JButton bMetal = new JButton("MetalLookAndFeel");
        bMetal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLookAndFeel(metal, frame);
            }
        });

        final JButton bWindows = new JButton("WindowsLookAndFeel");
        bWindows.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLookAndFeel(windows, frame);
            }
        });

        final JButton bMotif = new JButton("MotifLookAndFeel");
        bMotif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLookAndFeel(motif, frame);
            }
        });

        final JButton bDefault = new JButton("Default for the platform");
        bDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String def = UIManager.getSystemLookAndFeelClassName();
                    UIManager.setLookAndFeel(def);
                    SwingUtilities.updateComponentTreeUI(frame);
                    info.setText("Default: " + def);
                } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
                    info.setText(e1.getMessage());
                }
            }
        });

        StringBuilder installed = new StringBuilder("Installed Look And Feels: \n\n");
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            installed.append(info.getName());
            installed.append("\n");
        }
        JTextArea installedLookAndFeels = new JTextArea();
        installedLookAndFeels.setRows(10);
        installedLookAndFeels.setLineWrap(true);
        installedLookAndFeels.setText(installed.toString());

        JPanel pButtons = new JPanel();
        pButtons.add(bDefault);
        pButtons.add(bMotif);
        pButtons.add(bWindows);
        pButtons.add(bMetal);
        frame.add(pButtons, BorderLayout.NORTH);
        frame.add(installedLookAndFeels, BorderLayout.CENTER);
        frame.add(info, BorderLayout.SOUTH);
    }

    private void setLookAndFeel(LookAndFeel lookAndFeel, JFrame frame) {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
            SwingUtilities.updateComponentTreeUI(frame);
            info.setText(lookAndFeel.getName() + " - " + lookAndFeel.getDescription());
        } catch (UnsupportedLookAndFeelException e1) {
            info.setText(e1.toString());
        }
    }

}