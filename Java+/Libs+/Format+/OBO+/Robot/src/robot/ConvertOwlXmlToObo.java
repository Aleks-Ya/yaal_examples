package robot;

import org.obolibrary.robot.IOHelper;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import util.FileUtil;

import java.io.File;
import java.io.IOException;

class ConvertOwlXmlToObo {
    public static void main(String[] args) throws IOException {
        var inputOwlFile = new File("/home/aleks/Downloads/owl_ontologies/go-plus.owl");
        var outputOboFile = FileUtil.createAbsentTempFile(".obo");
        System.out.println("Output file: " + outputOboFile.getAbsolutePath());

        var helper = new IOHelper();
        var ontology = helper.loadOntology(inputOwlFile);
        helper.saveOntology(ontology, new OBODocumentFormat(), outputOboFile, false);
    }
}
