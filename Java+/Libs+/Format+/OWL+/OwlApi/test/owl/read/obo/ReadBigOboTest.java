package owl.read.obo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.obolibrary.obo2owl.OWLAPIObo2Owl;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Requires downloaded file: https://proconsortium.org/download/current/pro_reasoned.obo")
class ReadBigOboTest {
    private static final File file = new File("/home/aleks/Downloads/OBO/pro_reasoned.obo");

    @Test
    void loadOboWithoutConversionToOntology() throws IOException {
        var parser = new OBOFormatParser();
        var doc = parser.parse(file);
        var headerFrame = doc.getHeaderFrame();
        var termFrames = doc.getTermFrames();
        var typedefFrames = doc.getTypedefFrames();
        var instanceFrames = doc.getInstanceFrames();
        assertThat(headerFrame).isNotNull();
        assertThat(termFrames).isNotEmpty();
        assertThat(typedefFrames).isNotEmpty();
        assertThat(instanceFrames).isEmpty();
    }

    @Test
    void loadOntologyWithBridge() throws OWLOntologyCreationException, IOException {
        var manager = OWLManager.createOWLOntologyManager();
        var ontology = manager.createOntology();
        var parser = new OBOFormatParser();
        var doc = parser.parse(file);
        var bridge = new OWLAPIObo2Owl(ontology.getOWLOntologyManager());
        bridge.convert(doc, ontology);
        assertThat(ontology).isNotNull();
    }

    @Test
    void loadOntologyWithManager() throws OWLOntologyCreationException {
        var manager = OWLManager.createOWLOntologyManager();
        var ontology = manager.loadOntologyFromOntologyDocument(file);
        assertThat(ontology).isNotNull();
    }
}
