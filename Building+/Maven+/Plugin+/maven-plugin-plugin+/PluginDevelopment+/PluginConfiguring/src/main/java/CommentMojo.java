import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Указание параметров в комментарии.
 *
 * @goal comment-mojo
 */
public class CommentMojo extends AbstractMojo {
    /**
     * @parameter
     */
    private File file;
    /**
     * @parameter
     */
    private String[] strings;
    /**
     * @parameter
     */
    private Boolean bool;
    /**
     * @parameter
     */
    private Integer integer;
    /**
     * @parameter
     */
    private Double doub;
    /**
     * @parameter
     */
    private URL url;
    /**
     * @parameter
     */
    private List<String> lists;
    /**
     * @parameter
     */
    private Map<String, Integer> map;
    /**
     * @parameter
     */
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

        assert (file != null);
        assert (strings.length == 2);
        assert (bool);
        assert (integer == 77);
        assert (doub == 33.3);
        assert (url.toString().equals("http://robcostlow.com"));
        assert (lists.size() == 2);
        assert (map.size() == 2);
    }
}

