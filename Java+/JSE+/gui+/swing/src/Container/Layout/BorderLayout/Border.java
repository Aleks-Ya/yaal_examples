package Container.Layout.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

public class Border {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BorderFrame();
            }
        });
    }
}

class BorderFrame extends JFrame {
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 400;

    BorderFrame() throws HeadlessException {
        init();

        JButton bOk = new JButton("OK");
        JButton bCancel = new JButton("Cancel");

        JPanel pButtons = new JPanel();
        pButtons.add(bOk);
        pButtons.add(bCancel);

        JTextArea taCenter = new JTextArea("Given a compile-time reference type S (source) " +
                "and a compile-time reference type T (target), a casting conversion exists " +
                "from S to T if no compile-time errors occur due to the following rules. ");

        JLabel lNorth = new JLabel("Компоненты пользовательского интерфейса Swing");

        JTextField tfName = new JTextField("Имя");

        JButton bWest = new JButton("Запад");

        add(lNorth, BorderLayout.NORTH);
        add(tfName, BorderLayout.EAST);
        add(bWest, BorderLayout.WEST);
        add(pButtons, BorderLayout.SOUTH);
        add(taCenter, BorderLayout.CENTER);
    }

    private void init() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}