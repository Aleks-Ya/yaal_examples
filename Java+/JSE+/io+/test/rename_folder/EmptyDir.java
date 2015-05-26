package rename_folder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.System.out;

public class EmptyDir {
    public static void main(String[] args) throws IOException {
		file();
		files();
    }
    
    private static void files() throws IOException {
		out.println("===== Files#move =====");
        Path source = Files.createTempDirectory("");
        out.printf("SOURCE: %s%n", source);
        
        Path target = source.resolveSibling("renamed-files_" + source.getFileName());
        out.printf("TARGET: %s%n", target);
        
        Path result = Files.move(source, target);
        out.printf("RESULT: %s%n%n", result);		
	}
	
	private static void file() throws IOException {
		out.println("===== File#renameTo =====");
        File source = Files.createTempDirectory("").toFile();
        out.printf("SOURCE: %s%n", source);
        
        File target = new File(source.getParent(), "renamed-file_" + source.getName());
        out.printf("TARGET: %s%n", target);
        
        boolean result = source.renameTo(target);
        out.printf("RESULT: %s%n%n", result);		
	}
}