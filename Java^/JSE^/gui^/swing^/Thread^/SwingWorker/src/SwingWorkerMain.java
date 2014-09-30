import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public SwingWorkerFrame() {
        init();

        JLabel lRows = new JLabel("Rows: 0");
        JTextArea taContent = new JTextArea();
        JScrollPane spArea = new JScrollPane(taContent,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        final JButton bOpen = new JButton("Open");
        final JButton bCancel = new JButton("Cancel");

        final FileReaderWorker worker = new FileReaderWorker(lRows, taContent, bOpen, bCancel);

        bCancel.setEnabled(false);
        bCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worker.cancel();
            }
        });

        bOpen.addActionListener(new ChooseFileAction(this, worker, bCancel));

        JPanel pButtons = new JPanel();
        pButtons.add(bOpen);
        pButtons.add(bCancel);


        add(pButtons, BorderLayout.NORTH);
        add(spArea, BorderLayout.CENTER);
        add(lRows, BorderLayout.SOUTH);
    }

    private void init() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HIGHT);
        setVisible(true);
    }
}

class ChooseFileAction implements ActionListener {
    private final Component parent;
    private final JFileChooser fileChooser = new JFileChooser();
    private final FileReaderWorker worker;
    private final JButton bCancel;

    ChooseFileAction(Component parent, FileReaderWorker worker, JButton bCancel) {
        this.parent = parent;
        this.worker = worker;
        this.bCancel = bCancel;
        fileChooser.setCurrentDirectory(new File("."));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            worker.setFile(fileChooser.getSelectedFile());
            worker.execute();
        }
    }
}

class FileReaderWorker extends SwingWorker<String, Object> {
    private File file;
    private final JLabel lOut;
    private final JTextArea taContent;
    private final JButton bOpen;
    private final JButton bCancel;
    private boolean cancelled = false;

    FileReaderWorker(JLabel lOut, JTextArea taContent, JButton bOpen, JButton bCancel) {
        this.lOut = lOut;
        this.taContent = taContent;
        this.bOpen = bOpen;
        this.bCancel = bCancel;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    protected String doInBackground() throws Exception {
        bOpen.setEnabled(false);
        bCancel.setEnabled(true);

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        int lineCounter = 0;
        while ((line = reader.readLine()) != null && !cancelled) {
            sb.append(line);
            sb.append('\n');
            lineCounter++;
            waiting();
            publish(lineCounter, sb.toString());
        }
        return sb.toString();
    }

    @Override
    protected void process(List<Object> chunks) {
        lOut.setText("Files: " + chunks.get(0));
        taContent.setText(chunks.get(1).toString());
    }

    @Override
    protected void done() {
        bOpen.setEnabled(true);
        bCancel.setEnabled(false);
    }

    private void waiting() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500);
    }

    public void cancel() {
        cancelled = true;
    }
}