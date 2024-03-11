package robot;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

class ConvertOwlXmlToOboByClassLoader {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        var jarUrl = URI.create("file:///home/aleks/installed/robot_obo/robot.jar").toURL();
        try (var classLoader = new URLClassLoader(new URL[]{jarUrl})) {
            var mainClass = classLoader.loadClass("org.obolibrary.robot.CommandLineInterface");
            var mainMethod = mainClass.getMethod("main", String[].class);
            var args2 = new String[]{
                    "convert",
                    "--check", "false",
                    "--format", "OBO",
                    "--input", "/home/aleks/Downloads/owl_ontologies/go-plus.owl",
                    "--output", "/tmp/go-plus-4.obo"};
            mainMethod.invoke(null, (Object) args2);
        }
    }
}
