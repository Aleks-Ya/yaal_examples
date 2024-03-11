package owl.convert;

import org.junit.jupiter.api.Test;
import org.obolibrary.obo2owl.OWLAPIOwl2Obo;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.obolibrary.oboformat.writer.OBOFormatWriter;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import util.FileUtil;
import util.ResourceUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * core.owl and core.obo were downloaded from <a href="https://github.com/oborel/obo-relations">GitHub</a>.
 */
class ConvertOwlXmlToOboTest {
    private final File inputOwlFile = ResourceUtil.resourceToFile("owl/convert/core.owl");
    private final File outputOboFile = FileUtil.createAbsentTempFile(".obo");
    private final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

    @Test
    void convert() throws OWLOntologyCreationException, IOException, OWLOntologyStorageException {
        var ontology = manager.loadOntologyFromOntologyDocument(inputOwlFile);
        ontology.saveOntology(new OBODocumentFormat(), new FileOutputStream(outputOboFile));
        assertOutputObo();
    }

    @Test
    void convertNoValidation() throws OWLOntologyCreationException, IOException, OWLOntologyStorageException {
        var ontology = manager.loadOntologyFromOntologyDocument(inputOwlFile);
        var ontologyFormat = new OBODocumentFormat();
        ontologyFormat.setParameter(OBODocumentFormat.VALIDATION, Boolean.FALSE);
        ontology.saveOntology(ontologyFormat, new FileOutputStream(outputOboFile));
        assertOutputObo();
    }

    /**
     * Hide warnings in log: "[Test worker] ERROR org.obolibrary.obo2owl.OWLAPIOwl2Obo - MASKING ERROR «the axiom is not translated :..."
     */
    @Test
    void convertMuteMaskedErrors() throws OWLOntologyCreationException, IOException {
        var ontology = manager.loadOntologyFromOntologyDocument(inputOwlFile);
        var bridge = new OWLAPIOwl2Obo(manager);
        bridge.setMuteUntranslatableAxioms(true);
        var obo = bridge.convert(ontology);
        var writer = new OBOFormatWriter();
        writer.write(obo, outputOboFile);
        assertOutputObo();
    }

    private void assertOutputObo() throws IOException {
        var parser = new OBOFormatParser();
        var actOboDoc = parser.parse(outputOboFile);
        var oboFile = ResourceUtil.resourceToFile("owl/convert/core.obo");
        var expOboDoc = parser.parse(oboFile);
        assertThat(actOboDoc.getTermFrames().size()).isEqualTo(expOboDoc.getTermFrames().size());
        assertThat(actOboDoc.getTypedefFrames().size()).isEqualTo(expOboDoc.getTypedefFrames().size());
        assertThat(actOboDoc.getInstanceFrames().size()).isEqualTo(expOboDoc.getInstanceFrames().size());
    }
}
