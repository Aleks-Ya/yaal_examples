package robot;

import org.junit.jupiter.api.Test;
import org.obolibrary.robot.IOHelper;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import util.FileUtil;
import util.ResourceUtil;

import java.io.IOException;

class OwlXmlToOboTest {
    @Test
    void convert() throws IOException {
        var inputOwlFile = ResourceUtil.resourceToFile("robot/ro-core.owl");
        var outputOboFile = FileUtil.createAbsentTempFile(".obo");
        System.out.println("Output file: " + outputOboFile.getAbsolutePath());

        var helper = new IOHelper();
        var ontology = helper.loadOntology(inputOwlFile);
        helper.saveOntology(ontology, new OBODocumentFormat(), outputOboFile);
    }
}
