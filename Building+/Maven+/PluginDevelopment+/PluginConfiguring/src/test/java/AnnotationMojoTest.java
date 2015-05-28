import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AnnotationMojoTest {

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
    public void test() throws Exception {
        String path = AnnotationMojoTest.class.getResource("test_pom.xml").getFile().replaceAll("%2b","+");
        System.out.println(path);
        File pom = new File(path);

        assertTrue(pom.exists());
        AnnotationMojo mojo = (AnnotationMojo) rule.lookupMojo("annotation-mojo", pom);
        assertNotNull(mojo);
        mojo.execute();
    }
}