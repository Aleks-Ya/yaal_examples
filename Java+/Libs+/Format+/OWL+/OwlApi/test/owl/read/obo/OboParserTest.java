package owl.read.obo;

import org.junit.jupiter.api.Test;
import org.obolibrary.obo2owl.OWLAPIObo2Owl;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import owl.OwlFactory;

import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToFile;

class OboParserTest {
    @Test
    void parseOntology() throws OWLOntologyCreationException, IOException {
        var file = resourceToFile(getClass(), "ReadOntologyTest_act.obo");
        var manager = OWLManager.createOWLOntologyManager();
        var ontology = manager.createOntology();
        var parser = new OBOFormatParser();
        var doc = parser.parse(file);
        var bridge = new OWLAPIObo2Owl(ontology.getOWLOntologyManager());
        bridge.convert(doc, ontology);
        var expOntology = OwlFactory.readOntologyFromResource("owl/read/ReadOntologyTest_exp.rdf");
        assertThat(ontology).isEqualTo(expOntology);
    }

    @Test
    void parseDoc() throws IOException {
        var file = resourceToFile(getClass(), "ReadOntologyTest_act.obo");
        var parser = new OBOFormatParser();
        var doc = parser.parse(file);
        var headerFrame = doc.getHeaderFrame();
        var termFrames = doc.getTermFrames();
        var typedefFrames = doc.getTypedefFrames();
        var instanceFrames = doc.getInstanceFrames();
        assertThat(headerFrame).hasToString(
                "Frame(null format-version( 1.2) ontology( http://example.com/ontologies/person.owl) )");
        assertThat(termFrames).map(Frame::toString).containsOnly(
                "Frame(http://example.com/Student id( http://example.com/Student) is_a( http://example.com/Person) )");
        assertThat(typedefFrames).isEmpty();
        assertThat(instanceFrames).isEmpty();
    }

    @Test
    void parseString() throws IOException {
        var obo = """
                format-version: 1.2
                ontology: http://example.com/ontologies/person.obo
                
                [Term]
                id: http://example.com/Student
                property_value: admissionYear "2021" xsd:decimal
                """.stripIndent();
        var parser = new OBOFormatParser();
        var doc = parser.parse(new StringReader(obo));
        var headerFrame = doc.getHeaderFrame();
        var termFrames = doc.getTermFrames();
        var typedefFrames = doc.getTypedefFrames();
        var instanceFrames = doc.getInstanceFrames();
        assertThat(headerFrame).hasToString(
                "Frame(null format-version( 1.2) ontology( http://example.com/ontologies/person.obo) )");
        assertThat(termFrames).map(Frame::toString).containsOnly(
                "Frame(http://example.com/Student id( http://example.com/Student) property_value( admissionYear 2021 xsd:decimal) )");
        assertThat(typedefFrames).isEmpty();
        assertThat(instanceFrames).isEmpty();
    }
}
