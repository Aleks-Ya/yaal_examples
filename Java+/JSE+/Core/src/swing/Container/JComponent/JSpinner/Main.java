package swing.Container.JComponent.JSpinner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;

public class Main {
    public static void main(String[] args) {
        JSpinner spinner = new JSpinner();
        spinner.addChangeListener(new SpinnerListener());
        spinner.setPreferredSize(new Dimension(100, 30));

        JPanel panel = new JPanel();
        panel.add(spinner);

        JFrame frame = new JFrame();
        frame.setSize(600, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);
    }

    /**
     * Выводит значение спиннера в консоль.
     */
    private static class SpinnerListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            Object source = e.getSource();
            JSpinner spinner = (JSpinner) source;
            System.out.println(spinner.getValue());
        }
    }
}
