package swing.example;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.WindowConstants;

public class RadionButtonMenuBar {
    public static void main(String[] args) {
        JRadioButtonMenuItem traceLevelMenuItem = new JRadioButtonMenuItem("TRACE");
        JRadioButtonMenuItem infoLevelMenuItem = new JRadioButtonMenuItem("INFO");
        infoLevelMenuItem.setSelected(true);
        JRadioButtonMenuItem errorLevelMenuItem = new JRadioButtonMenuItem("ERROR");

        JMenu logLevelMenu = new JMenu("Log Level");
        logLevelMenu.add(traceLevelMenuItem);
        logLevelMenu.add(infoLevelMenuItem);
        logLevelMenu.add(errorLevelMenuItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(logLevelMenu);

        final JFrame frame = new JFrame("Swing окно");
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
