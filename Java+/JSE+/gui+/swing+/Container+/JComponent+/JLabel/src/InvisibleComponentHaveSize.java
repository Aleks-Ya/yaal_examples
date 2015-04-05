import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Размер невидимого компонента учитывается менеджером раскладки.
 * На основе Horizontal Glue.
 */
class InvisibleComponentHaveSize {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //init
                JFrame frame = new JFrame();
                frame.setSize(500, 100);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);

                //set box layout
                Container contentPane = frame.getContentPane();
                BoxLayout layout = new BoxLayout(contentPane, BoxLayout.X_AXIS);
                contentPane.setLayout(layout);

                //make JTextField
                JTextField text = new JTextField("Введите что-нибудь :)");
                text.setMaximumSize(new Dimension(250, 20));

                //make invisible and visible buttons
                final JButton buttonInvisible = new JButton("Invisible Button");
                buttonInvisible.setEnabled(false);

                final JButton buttonVisible = new JButton("Show/Hide");
                buttonVisible.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttonInvisible.setVisible(!buttonInvisible.isVisible());
                    }
                });

                //add components
                frame.add(text);
                frame.add(Box.createHorizontalGlue());
                frame.add(buttonInvisible);
                frame.add(buttonVisible);
            }
        });
    }
}