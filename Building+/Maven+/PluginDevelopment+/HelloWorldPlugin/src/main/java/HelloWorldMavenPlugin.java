import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Maven-плагин Hello World.
 */
@Mojo(name = "hello")
public class HelloWorldMavenPlugin extends AbstractMojo {

    @Parameter(property = "hello.greeting", defaultValue = "Hello World!")
    private String greeting;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(greeting);
    }
}
