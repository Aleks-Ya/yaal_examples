import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File dir = new File(".");
        Desktop.getDesktop().open(dir);
    }
}