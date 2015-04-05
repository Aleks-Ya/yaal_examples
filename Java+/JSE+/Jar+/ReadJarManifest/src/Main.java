import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class Main {
    public static void main(String[] args) throws IOException {
        URLClassLoader cl = (URLClassLoader) Main.class.getClassLoader();
        URL url = cl.findResource("META-INF/MANIFEST.MF");
        Manifest manifest = new Manifest(url.openStream());
        Attributes attrs = manifest.getMainAttributes();
        String title = attrs.getValue("title");
        String version = attrs.getValue("version");
        System.out.println("title=" + title);
        System.out.println("version=" + version);
    }
}