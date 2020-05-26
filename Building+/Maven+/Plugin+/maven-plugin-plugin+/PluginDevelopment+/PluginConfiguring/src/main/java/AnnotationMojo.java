import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Указание параметров с помощью аннотации @Parameter.
 */
@Mojo(name = "annotation-mojo")
public class AnnotationMojo extends AbstractMojo {

    @Parameter
    private File file;
    @Parameter(defaultValue = "*.myProperties")
    private String[] strings;
    @Parameter
    private Boolean bool;
    @Parameter
    private Integer integer;
    @Parameter
    private Double doub;
    @Parameter
    private URL url;
    @Parameter
    private List<String> lists;
    @Parameter
    private Map<String, Integer> map;
    @Parameter
    private Properties myProperties;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("File: " + file);
        getLog().info("Strings: " + Arrays.deepToString(strings));
        getLog().info("Boolean: " + bool);
        getLog().info("Integer: " + integer);
        getLog().info("Double: " + doub);
        getLog().info("URL: " + url);
        getLog().info("List: " + lists);
        getLog().info("Map: " + map);
        getLog().info("Properties: " + myProperties);

        assert (file != null);
        assert (strings.length == 2);
        assert (bool);
        assert (integer == 77);
        assert (doub == 33.3);
        assert (url.toString().equals("http://robcostlow.com"));
        assert (lists.size() == 2);
        assert (map.size() == 2);
        assert (myProperties.size() == 1);
    }
}

