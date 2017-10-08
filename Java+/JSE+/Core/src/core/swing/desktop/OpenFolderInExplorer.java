package core.swing.desktop;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * Задача: открыть папку в проводнике (Windows и Linux).
 */
public class OpenFolderInExplorer {
    public static void main(String[] args) throws IOException {
        File dir = new File(".");
        Desktop.getDesktop().open(dir);
    }
}