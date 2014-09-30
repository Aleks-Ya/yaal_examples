import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

/**
 * НЕЗАВЕРШЕН ХОРСТМАНН-1 С.797
 * Использование SwingWorker для загрузки большого файла в отдельном потоке
 * (с возможностью отмены).
 */
public class FileChooserMain {
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
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HIGHT = 200;


    public SwingWorkerFrame() {
        setTitle("JFileChooser");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HIGHT);
        setVisible(true);

        JButton bOpenSingleFile = new JButton("Open single file");
        JButton bOpenMultiFiles = new JButton("Open multi files");
        JButton bOpenFilesAndDirs = new JButton("Open files and dirs");
        JButton bOpenSingleDir = new JButton("Open single dir");
        JTextArea taFiles = new JTextArea();

        JFileChooser fileChooser = new JFileChooser();

        bOpenSingleFile.addActionListener(new ChooseFileAction(fileChooser, bOpenSingleFile, false, JFileChooser.FILES_ONLY, taFiles));
        bOpenMultiFiles.addActionListener(new ChooseFileAction(fileChooser, bOpenSingleFile, true, JFileChooser.FILES_ONLY, taFiles));
        bOpenFilesAndDirs.addActionListener(new ChooseFileAction(fileChooser, bOpenSingleFile, true, JFileChooser.FILES_AND_DIRECTORIES, taFiles));
        bOpenSingleDir.addActionListener(new ChooseFileAction(fileChooser, bOpenSingleFile, false, JFileChooser.DIRECTORIES_ONLY, taFiles));

        JPanel pButtons = new JPanel();
        pButtons.add(bOpenSingleFile);
        pButtons.add(bOpenMultiFiles);
        pButtons.add(bOpenFilesAndDirs);
        pButtons.add(bOpenSingleDir);

        add(pButtons, BorderLayout.NORTH);
        add(taFiles, BorderLayout.CENTER);
    }
}

class ChooseFileAction implements ActionListener {
    private final JComponent parent;
    private final JFileChooser fileChooser;
    private final boolean multiSelection;
    private final int mode;
    private final JTextArea lSelectedFiles;

    ChooseFileAction(JFileChooser fileChooser, JComponent parent, boolean multiSelection, int mode, JTextArea lSelectedFiles) {
        this.fileChooser = fileChooser;
        this.parent = parent;
        this.multiSelection = multiSelection;
        this.mode = mode;
        this.lSelectedFiles = lSelectedFiles;
        this.fileChooser.setCurrentDirectory(new File("."));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fileChooser.setMultiSelectionEnabled(multiSelection);
        fileChooser.setFileSelectionMode(mode);
        fileChooser.showOpenDialog(parent);

        String names = fileChooser.isMultiSelectionEnabled() ?
                Arrays.deepToString(fileChooser.getSelectedFiles()) :
                fileChooser.getSelectedFile().toString();
        lSelectedFiles.setText(names);
    }
}