import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MavenXpp3ReaderTest {

    @Test
    public void read() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(getClass().getResourceAsStream("test-pom.xml"));

        assertEquals("ru.yaal.examples.maven.plugin", model.getGroupId());
        assertEquals("BasicTestPluginConfig", model.getArtifactId());
        assertEquals("1.0", model.getVersion());

        List<String> modules = model.getModules();
        assertEquals(2, modules.size());
        assertEquals("loco-fun", modules.get(0));
        assertEquals("vaadin-binding", modules.get(1));
    }
}
