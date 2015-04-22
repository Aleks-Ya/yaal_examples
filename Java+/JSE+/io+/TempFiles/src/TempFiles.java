import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import static java.lang.System.out;

public class TempFiles {
    public static void main(String[] args) throws IOException {
		tempFile();
		tempFiles();
		tempFolders();
        systemTempDir();
    }
	
	/**
	 * java.io.File#createTempFile
	 */
    private static void tempFile() throws IOException {
        File f1 = File.createTempFile("prefix-file_", ".suffix");
        File f2 = File.createTempFile("prefix-file_", null);// ".tmp" suffix by default
        
        f1.deleteOnExit();
        f2.deleteOnExit();
        
        out.println(f1);        
        out.println(f2);
	}
    
    /**
     * java.nio.file.Files#createTempFile
     */
    private static void tempFiles() throws IOException {
        Path f1 = Files.createTempFile("prefix-files_", ".suffix");
        Path f2 = Files.createTempFile("prefix-files_", null);// ".tmp" suffix by default
        
        f1.toFile().deleteOnExit();
        f2.toFile().deleteOnExit();
        
        out.println(f1);
        out.println(f2);
	}
		
	private static void tempFolders() throws IOException {
		Path tmpDir = Files.createTempDirectory("prefix-folder_");
		tmpDir.toFile().deleteOnExit();
        out.println(tmpDir);
	}

    public static void systemTempDir() {
        String tmpDirStr = System.getProperty("java.io.tmpdir");
        File tmpDir = new File(tmpDirStr);
        System.out.println("Temp dir: " + tmpDir.getAbsolutePath());
    }
}