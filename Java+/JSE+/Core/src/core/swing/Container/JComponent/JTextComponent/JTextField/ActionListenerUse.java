package core.swing.Container.JComponent.JTextComponent.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Вызов слушателя, когда пользователь нажимает Enter.
 */
public class ActionListenerUse {
    public static void main(String[] args) {
        JTextField field = new JTextField("Hello");
        field.addActionListener(new EnterListener());

        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(field);
    }
}

class EnterListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField source = (JTextField) e.getSource();
        source.setText("Enter");
    }
}