import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.WithoutMojo;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Перед запуском теста необходимо поместить тестируемый плагин в локальный репозитарий:
 * mvn -DskipTests clean install
 */
public class HelloWorldMojoTest {
    @Rule
    public MojoRule rule = new MojoRule() {
        @Override
        protected void before() throws Throwable {
        }

        @Override
        protected void after() {
        }
    };

    @Test
    public void testMojoGoal() throws Exception {
        String pomXmlPath = HelloWorldMojoTest.class.getResource(
                "basic-test-plugin-config.xml").getFile();
        pomXmlPath = pomXmlPath.replaceAll("%2b", "+");
        File testPom = new File(pomXmlPath);
        assertTrue("Not exists: " + testPom.getAbsolutePath(), testPom.exists());
        HelloWorldMavenPlugin mojo = (HelloWorldMavenPlugin) rule.lookupMojo("hello", testPom);

        assertNotNull(mojo);

        mojo.execute();
    }

    /**
     * Тестовый метод без MojoRule.
     */
    @WithoutMojo
    @Test
    public void testSomethingWhichDoesNotNeedTheMojoAndProbablyShouldBeExtractedIntoANewClassOfItsOwn() {
    }
}
