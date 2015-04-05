import java.io.File;

/**
 * Получение временной папки.
 */
public class TempDir {
	
    public static void main(String[] args) {
        String tmpDirStr = System.getProperty("java.io.tmpdir");
        File tmpDir = new File(tmpDirStr);
        System.out.println("Temp dir: " + tmpDir.getAbsolutePath());
    }
}