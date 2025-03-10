package swing.thread.SwingWorker;

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
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Использование SwingWorker для загрузки большого файла в отдельном потоке
 * (с возможностью отмены).
 * Пример из К. Хорстманн "Java 2", том 1, гл. 14, раздел "Использование класса SwingWorker".
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
    private static final int FRAME_HEIGHT = 600;
    private final JFileChooser fileChooser = new JFileChooser();
    private FileReaderWorker worker;

    public SwingWorkerFrame() {
        init();

        final JLabel lRows = new JLabel("Rows: 0");
        final JTextArea taContent = new JTextArea();
        JScrollPane spArea = new JScrollPane(taContent,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        final JButton bOpen = new JButton("Open");
        final JButton bCancel = new JButton("Cancel");

        JPanel pButtons = new JPanel();
        pButtons.add(bOpen);
        pButtons.add(bCancel);

        add(pButtons, BorderLayout.NORTH);
        add(spArea, BorderLayout.CENTER);
        add(lRows, BorderLayout.SOUTH);

        fileChooser.setCurrentDirectory(new File("."));
        bCancel.setEnabled(false);
        bCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worker.cancel(true);
            }
        });

        bOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(SwingWorkerFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    worker = new FileReaderWorker(lRows, taContent, bOpen, bCancel);
                    worker.setFile(fileChooser.getSelectedFile());
                    worker.execute();
                }
            }
        });

    }

    private void init() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
    }
}

class ProgressData {
    int counter;
    String content;

    ProgressData(int counter, String content) {
        this.counter = counter;
        this.content = content;
    }
}

class FileReaderWorker extends SwingWorker<String, ProgressData> {
    private File file;
    private final JLabel lOut;
    private final JTextArea taContent;
    private final JButton bOpen;
    private final JButton bCancel;

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
        while ((line = reader.readLine()) != null && !isCancelled()) {
            sb.append(line);
            sb.append('\n');
            lineCounter++;
            TimeUnit.MILLISECONDS.sleep(500);
            publish(new ProgressData(lineCounter, sb.toString()));
        }
        return sb.toString();
    }

    @Override
    protected void process(List<ProgressData> chunks) {
        ProgressData data = chunks.get(0);
        lOut.setText("Files: " + data.counter);
        taContent.setText(data.content);
    }

    @Override
    protected void done() {
        try {
            get();
            lOut.setText("Done");
        } catch (InterruptedException | CancellationException e) {
            lOut.setText("Cancelled");
        } catch (ExecutionException e) {
            lOut.setText(e.getMessage());
        }
        bOpen.setEnabled(true);
        bCancel.setEnabled(false);
    }
}