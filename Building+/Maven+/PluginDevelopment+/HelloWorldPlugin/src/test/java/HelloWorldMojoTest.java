import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

/**
 * @author yablokov a.
 */
public class HelloWorldMojoTest extends AbstractMojoTestCase {
    protected void setUp() throws Exception {
        // required for mojo lookups to work
        super.setUp();
    }

    public void testMojoGoal() throws Exception {
        File testPom = new File(getBasedir(),
                "src/test/resources/basic-test-plugin-config.xml");
        assertTrue(testPom.exists());
        HelloWorldMavenPlugin mojo = (HelloWorldMavenPlugin) lookupMojo("hello", testPom);

        assertNotNull(mojo);
    }
}
