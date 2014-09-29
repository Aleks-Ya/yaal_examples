import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.HeadlessException;

/**
 * НЕЗАВЕРШЕН ХОРСТМАНН-1 С.797
 * Использование SwingWorker для загрузки большого файла в отдельном потоке
 * (с возможностью отмены).
 */
public class SwingWorkerFrame extends JFrame {
    private final JButton bOpen = new JButton("Open");
    private final JButton bCancel = new JButton("Cancel");
    private final JTextArea taContent = new JTextArea();
    private final JLabel lRows = new JLabel();

    public static void main(String[] args) {
        new SwingWorkerFrame();
    }

    public SwingWorkerFrame() throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 600);
        setVisible(true);

        JPanel pButtons = new JPanel();
        pButtons.add(bOpen);
        pButtons.add(bCancel);

        JScrollPane spArea = new JScrollPane(taContent,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(pButtons, BorderLayout.NORTH);
        add(spArea, BorderLayout.CENTER);
        add(lRows, BorderLayout.SOUTH);
    }
}