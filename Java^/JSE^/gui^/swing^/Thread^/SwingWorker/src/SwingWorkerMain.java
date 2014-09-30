import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * НЕЗАВЕРШЕН ХОРСТМАНН-1 С.797
 * Использование SwingWorker для загрузки большого файла в отдельном потоке
 * (с возможностью отмены).
 */
public class SwingWorkerMain {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SwingWorkerFrame();
            }
        });
    }
}

class SwingWorkerFrame extends JFrame {
    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HIGHT = 600;
    private final JButton bOpen = new JButton("Open");
    private final JButton bCancel = new JButton("Cancel");
    private final JTextArea taContent = new JTextArea();
    private final JLabel lRows = new JLabel("Rows: 0");


    public SwingWorkerFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HIGHT);
        setVisible(true);

        bOpen.addActionListener(new ChooseFileAction(bOpen));

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

class ChooseFileAction implements ActionListener {
    private final JComponent parent;
    private final JFileChooser fileChooser = new JFileChooser();

    ChooseFileAction(JComponent parent) {
        this.parent = parent;
        fileChooser.setCurrentDirectory(new File("."));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fileChooser.showOpenDialog(parent);
    }
}